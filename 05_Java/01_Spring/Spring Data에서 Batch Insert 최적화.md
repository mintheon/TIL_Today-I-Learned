Spring Data JPA가 안겨주는 편리함 뒤에는 가끔 성능 손실이 숨어있다. 이번에 알아볼 Batch Insert도 그런 예 중 하나다.

성능 손실 문제가 발생하는 이유와 2가지 해결 방법을 알아본다.

전체 코드는 https://github.com/HomoEfficio/micro-benchmark-spring-boot-batch-insert 여기에서 볼 수 있으며 아주 쉽게 직접 테스트해 볼 수도 있다.

---

### Batch Insert란?

아무것도 없다. 배치 Insert가 뭔지 몰라도 이미 쓰고 있었을 수도 있다.  
3건의 데이터를 insert 한다고 할 때,

```sql
INSERT INTO table1 (col1, col2) VALUES (val11, val12);
INSERT INTO table1 (col1, col2) VALUES (val21, val22);
INSERT INTO table1 (col1, col2) VALUES (val31, val32);
```

```sql
INSERT INTO table1 (col1, col2) VALUES
(val11, val12),
(val21, val22),
(val31, val32);
```

상단은 `개별 insert`, 하단은 `batch insert`이다.
DB 관점에서 보면 간단하나, Spring Data에서 저런 DML이 DB로 전달되게 하는건 그렇게 간단하지는 않다.

---

### Hibernate의 Batch Insert 제약 사항

[Hibernate 레퍼런스 문서 12.2.1. Batch inserts](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#batch-session-batch-insert) 에서 확인하면 **식별자 생성에 IDENTITY 방식을 사용하면 Hibernate가 JDBC 수준에서 batch insert를 비활성화** 한다고 나와있다.

> Hibernate disables insert batching at the JDBC level transparently if you use an identity identifier generator.

비활성화 하는 이유는 문서에는 없으나 `갓택 오버플로우`에 [Vlad Mihalcea가 올린 댓글](https://stackoverflow.com/a/27732138)에서 확인할 수 있다.

> The only drawback is that we can’t know the newly assigned value prior to executing the INSERT statement. This restriction is hindering the “transactional write behind” flushing strategy adopted by Hibernate. For this reason, Hibernates disables the JDBC batch support for entities using the IDENTITY generator.

**새로 할당할 Key 값을 미리 알 수 없는 `IDENTITY` 방식을 사용할 때, Batch Support를 지원하면 Hibernate가 채택한 flush 방식인 `Transactional Write Behind`와 충돌이 발생하기 때문에 `IDENTITY 방식에서는 Batch Insert를 비활성화` 한다**는 얘기다.
따라서 일상적으로 가장 널리 사용하는 `IDENTITY 방식을 사용하면 Batch Insert는 미동작`한다.

그렇다고 Batch Insert를 적용하기 위해 섣불리 SEQUENCE 방식이나 TABLE 방식을 잘못 사용하면 더 나쁜 결과를 불러올 수 있다.
**채번에 따른 부하가 상당히 큰 `SEQUENCE` 방식이나 `TABLE` 방식을 별다른 조치없이 사용하면 Batch Insert를 쓸 수 없는 `IDENTITY` 방식보다 더 느리다**

자세한 내용은 [JPA GenerationType별 INSERT 성능 비교](https://github.com/HomoEfficio/dev-tips/blob/master/JPA-GenerationType-%EB%B3%84-INSERT-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90.md) 를 참조하자.

문제 발생시 유추할 수 있는 해결 방법은 2가지가 있다.

1. `SEQUENCE`나 `TABLE` 방식을 사용하면서 채번 부하를 낮추는 방법
2. Spring Data JPA를 벗어나는 방법

---

### 채번 부하 절감

SEQUENCE나 TABLE 방식을 사용하면서 채번 부하를 낮추는 방법은 [이 곳](https://dev.to/smartyansh/best-possible-hibernate-configuration-for-batch-inserts-2a7a)을 참조하자.

간단히 `채번 자체를 Batch 방식으로 처리하여 채번 부하를 낮추는 방식`이다.

#### 일반적인 채번

SEQUENCE 방식은 일반적으로 다음과 같이 사용한다.

```java
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id
  ...
}
```

위 방식 사용 시 `Sequence를 지원하는 DB`에서는 `Sequence`를 이용하여 채번한다.  
아래는 `Sequence`를 지원하는 `H2 DB`를 사용하였을때 나오는 Hibernate 로그 일부다.

```
Hibernate: call next value for hibernate_sequence
Hibernate: call next value for hibernate_sequence
Hibernate: call next value for hibernate_sequence
```

`Sequence를 지원하지 않는 DB` 에서는 `Table`을 이용하여 채번한다.
아래는 `Sequence`를 지원하지 않는 `MySQL DB`의 로그 일부다. 번호를 하나 딸때마다 쿼리를 2개씩 날리므로 꽤 큰 부하가 발생된다.

```
...
SET autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 2 where next_val=1
commit
autocommit=1
autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 3 where next_val=2
commit
SET autocommit=1
SET autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 4 where next_val=3
commit
SET autocommit=1
...
```

#### Batch 채번

채번 자체를 Batch로 처리하면 아래와 같이 500개씩 한번에 채번해서 쿼리횟수륵 대폭 줄이고 성능을 높일 수 있다.

```
...
SET autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 501 where next_val=1
commit
SET autocommit=1
SET autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 1001 where next_val=501
commit
SET autocommit=1
SET autocommit=0
select next_val as id_val from hibernate_sequence for update
update hibernate_sequence set next_val= 1501 where next_val=1001
commit
SET autocommit=1
...
```

Batch 채번은 다음과 같이 다소 복잡한 Hibernate 전용 애너테이션을 지정해야 한다.

채번 배치 크기도 애너테이션 내에서 지정해야 하므로 배치 크기 설정을 yml파일로 외부화 할 수 없다는 단점이 있다.
또한 `채번 배치 크기는 엔티티 클래스에서 Hibernate 애너테이션으로 지정`해야 하고, `Batch Insert의 배치 크기는 yml 파일로 지정`하므로 두 값이 달라질 가능성이 있다는 것도 운영상 단점이다.

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemSequence {
  @Id
  @GenericGenerator(
    name = "SequenceGenerator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
      @Parameter(name = "sequence_name", value = "hibernate_sequence"),
      @Parameter(name = "optimizer", value = "pooled"),
      @Parameter(name = "inital_value", value = "1"),
      @Parameter(name = "increment_size", value ="500")
    }
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SequenceGenerator"
  )
  private Long id;
  private String name;
  private String description;
}
```

---

### Spring Data JDBC

Spring Data에는 JPA만 있는 것이 아니다. https://spring.io/projects/spring-data 를 보면 다양한 저장소를 지원하는 서브 프로젝트도 많이 있으며, 관계형 데이터 베이스에서는 JPA 대신 JDBC를 사용할 수도 있다.

#### JdbcTemplate.batchUpdate()

JdbcTemplate에는 Batch를 지원하는 `batchUpdate()` 메서드가 있다. 여러가지로 Overloading 되어 있어 편리한 메서드를 골라서 사용하면 되는데 여기서는 batch 크기를 지정할 수 있는 `BatchPreparedStatementSetter`를 사용하는 하기의 메서드를 사용해서 구현한다.

```java
batchUpdate(String sql, BatchPreparedStatementSetter pss)
```

#### 주요 구현 부분

`ItemJdbc`라는 객체를 `ITEM_JDBC` 테이블에 Batch Insert로 저장한다고 가정한다.

`batchSize` 변수를 통해 배치 크기를 지정하고, 전체 데이터를 배치 크기로 나눠 Batch Insert를 실행하고 자투리 데이터를 다시 Batch Insert 처리한다.

```java
@Repository
@RequiredArgsConstructor
public class ItemJdbcRepositoryImpl implements ItemJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${batchSize}")
    private int batchSize;

    @Override
    public void saveAll(List<ItemJdbc> items) {
        int batchCount = 0;
        List<ItemJdbc> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            subItems.add(items.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsert(batchSize, batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchSize, batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
    }

    private int batchInsert(int batchSize, int batchCount, List<ItemJdbc> subItems) {
        jdbcTemplate.batchUpdate("INSERT INTO ITEM_JDBC (`NAME`, `DESCRIPTION`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, subItems.get(i).getName());
                        ps.setString(2, subItems.get(i).getDescription());
                    }
                    @Override
                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
        subItems.clear();
        batchCount++;
        return batchCount;
    }
}
```

#### 실험 결과

아래와 같은 환경에서 테스트 한 결과 **Spring Data JDBC 방식이 가장 빠르다.**

- 테스트 환경

  - Java 11
  - Spring Boot 2.2.4
  - MySQL 5.7.18
  - Spring Data JPA 2.2.4
  - Hibernate Core 5.4.10.Final
  - Hibernate Commons Annotations 5.1.0.Final
  - Spring Data JDBC 1.1.4

- 성능 차이  
  (연관 관계 없이 단 하나의 엔티티만 저장하는 시나리오에서 배치 크기를 바꿔가면서 만건의 데이터를 저장)

  | 배치 크기 | JDBC(A) | Batch SEQUENCE(B) | IDENTITY(C) | (B)/(A) | (C)/(A)     |
  | --------- | ------- | ----------------- | ----------- | ------- | ----------- |
  | 10        | 0.885   | 3.072             | 5.087       | 3.47    | 5.748022599 |
  | 50        | 0.391   | 1.007             | 4.097       | 2.58    | 10.47826087 |
  | 100       | 0.356   | 0.808             | 5.218       | 2.27    | 14.65730337 |
  | 500       | 0.226   | 0.515             | 5.637       | 2.28    | 24.94247788 |
  | 1000      | 0.216   | 0.480             | 6.241       | 2.22    | 28.89351852 |
  | 5000      | 0.189   | 0.447             | 5.052       | 2.37    | 26.73015873 |

  배치 크기에 따라 다르지만 `Spring Data JDBC의 BatchUpdate()`를 사용하는 방식이 `Hibernate Batch Sequence` 방식보다 대략 2~3배 빠르고, Batch Insert가 사용되지 못하는 `Hibernate IDENTITY` 방식보다는 5 ~ 25배정도 빠르다.

MySQL에는 Sequence가 없으므로 SEQUENCE 방식을 지정했다고 하더라도 사실 상 TABLE 방식으로 동작했다는 것을 감안하면, Sequence가 지원되는 DB에서는 TABLE 방식보다 채번 부하가 더 적은 SEQUENCE 방식을 Batch 스타일로 사용하면 Spring Data JDBC 방식과 비슷한 성능을 보일 것 같다.

---

### 마무리

- 아주 많은 수의 데이터를 한 꺼번에 입력할 때는 Spring Data JPA를 잠시 뒤로하고 Spring Data JDBC의 batchUpdate()를 활용하는 것도 좋다.

  - Spring Data JDBC는 Spring Data JPA와 함께 혼용해서 사용할 수도 있고, @Transactional을 통해 트랜잭션이 관리될 수 있으므로, 현실적으로 가장 나은 방법이다.
  - Spring Data JPA를 사용해야만 한다면 IDENTITY 방식 말고 Batch SEQUENCE 방식을 사용하는 것이 좋다.

  - 그러나 이 방식은 애너테이션 지정이 필요 이상 복잡하고, 테이블 생성 시부터 적용하면 괜찮지만, 이미 ID 생성 방식이 IDENTITY인 기존 테이블을 SEQUENCE 방식으로 변경해야 하는 부담이 있고, batch 크기 지정 관련 운영 상의 단점이 있다.
