### Lombok 설명

#### Lombok이란?

> Lombok은 Getter, Setter, ToString과 같은 메서드들을 간편하게 사용할 수 있게해주는 어노테이션기반 JAVA의 라이브러리입니다. 물론 IDE를 사용해 Getter, Setter 등의 메서드들을 손쉽게 생성할 수 있지만 멤버변수가 증가하면 증가할 수 록 코드의 양이 많아지고 복잡해지게 됩니다. Lombok은 이러한 것을 어노테이션을 이용해 개발자가 실제 구현한 코드에는 보이지 않지만 컴파일이 될 때 해당 메서드들이 자동으로 생성이 되는 과정을 통해 돌아가게 됩니다.

#### Lombok 사용하기

- 의존성 추가

  - Spring Boot를 사용할 시 프로젝트 생성할 때 추가해도 되지만 그렇지 않다면 아래의 의존성을 추가해주어야한다.

  ```xml
  compileOnly 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok'
  ```

  - `IntelliJ`를 사용할 경우 다음과 같이 설정도 해주어야 합니다.  
    `Preferences - Build, Excution, Deployment - Compiler - Annotation Processors`에서 `Enable Annotation Processing` 항목을 활성화시킨다.

- **@Getter**와 **@Setter**

  - Lombok을 사용하지 않은 Person 클래스

    ```java
    public class Person {

        private Long id;
        private String name;
        private int age;
        private String hobby;

          public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getHobby() {
            return hobby;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }
      }
    ```

  - Lombok을 사용한 Person 클래스

    ```
    @Getter
    @Setter
    public class Person {

        private Long id;
        private String name;
        private int age;
        private String hobby;

    }
    ```

​ 위의 예가 살짝 극단적이기는 하지만 멤버변수가 단지 4개일 뿐인데 Lombok을 사용하지 않은 코드와 사용한 코드의 라인 수 가 확연이 차이나는 것을 확인할 수 있다. 하지만 무분별한 Getter와 Setter는 캡슐화를 위반할 수 있으므로 좋지 않다.

```java
public class Person {

         @Getter
    private Long id;

      @Getter
      @Setter
    private String name;

    private int age;

    private String hobby;

}
```

​ 위의 코드에서는 `id`와 `name`은 외부에서 Get할 수 있지만 `age`, `hobby`의 경우는 get할 수 없다. 또한 `name`만이 외부에서 Set을 할 수 있다. 이처럼 멤버변수마다 따로 지정을 할 수 있어 무분별한 Getter, Setter의 설정으로 캡슐화를 위반해 결합도 를 높일 수 있는 위험성을 낮출 수 있다.

- **@ToString 사용**

  - 객체를 원하는 형태의 String으로 나타내기 위하여 `toString`이라는 메서드를 오버라이드하여 사용한다.

    ```java
    public class Person {

        private Long id;
        private String name;
        private int age;
        private String hobby;

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", hobby='" + hobby + '\'' +
                    '}';
        }

    }
    ```

  - 여기에 멤버변수가 빈번히 추가된다거나 멤버변수가 많아서 코드의 양이 늘어난다거나 할 수 있는데 이 또한 Getter나 Setter와 같이 Lombok이 어노테이션으로 지정만하면 자동으로 만들어준다.

    ```java
    @ToString
    public class Person {

        private Long id;
        private String name;
        private int age;
        private String hobby;

    }
    ```

  - 만약 `toString`을 사용하는데 `age`라는 값은 제외하고 보여주고 싶을 수 있는데 그럴때는 아래와 같이 사용할 수도 있다.

    ```java
    @ToString(exclude = "age")
    public class Person {

        private Long id;
        private String name;
        private int age;
        private String hobby;

    }
    ```

    또는

    ```java
      @ToString
      public class Person {

          private Long id;

          private String name;

            @ToString.Exclude
            private int age;

          private String hobby;

      }
    ```

- 생성자 생성하기

  - @NoArgsConstructor

  > Default 생성자를 추가해주는 어노테이션이다.

  - @AllArgsConstructor

  > 모든 멤버변수를 인자로 가지는 생성자를 추가해주는 어노테이션이다.

  - @RequiredArgsConstructor

  > @NonNull 어노테이션으로 지정해 놓은 멤버변수를 인자로 가지는 생성자를 추가해주는 어노테이션이다.

  ```java
  @NoArgsConstructor
  @AllArgsConstructor
  @RequiredArgsConstructor
  public class Person {

          @NonNull
      private Long id;

      @NonNull
      private String name;

      private int age;

      private String hobby;

  }
  ```

  위의 코드에서는 세 가지의 생성자가 존재하는 것과 같은 코드라고 볼 수 있다.

  ```java
  public Person() {

  }

  public Person(Long id, String name) {
  this.id = id;
  this.name = name;
  }

  public Person(Long id, String name, int age, String hobby) {
  this.id = id;
  this.name = name;
  this.age = age;
  this.hobby = hobby;
  }
  ```

- **@EqualsAndHashCode 사용하기**

  > 객체의 같음을 판단해주는 equals와 해시코드를 만들어주는 hashCode 함수를 오버라이드 하지않아도 Lombok이 자동으로 추가를 해준다. 따라서 비교해야할 멤버변수가 늘어난다거나해도 코드를 따로 변경하지않고 어노테이션만 있다면 번거롭지 않게 코딩을 할 수 있다.

  ```java
  public class Person {

      private Long id;

      private String name;

      private int age;

      private String hobby;

        @Override
      public boolean equals(Object object){
          if(object == null) {
              return false;
          }

          Person person = (Person) object;

          if(!person.getName().equals(this.name)){
              return false;
          }

          if(person.getAge() != this.age) {
              return false;
          }

          return true;
      }

        @Override
      public int hashCode() {
          return (name + age).hashCode();
      }

  }
  ```

  기존에 위의 코드처럼 `equals()`와 `hashCode()`를 오버라이드하여 사용하여야했다. 그러나 지금 위의 코드는 hobby의 같음 여부와 해시코드를 생성하는 곳에서 사용하지 않는다. 따라서 내용이 같은 객체 두 개를 생성하여 비교해보면 다르다는 결과를 얻을 수 있을 것이다. 이 문제를 해결하기 위해서는 두 메서드에 모두 hobby라는 값을 추가해주어야한다.

  ```java
  @EqualsAndHashCode
  public class Person {

      private Long id;

      private String name;

      private int age;

      private String hobby;
  }
  ```

  그러나 @EqualsAndHashCode 어노테이션을 사용하면 위의 코드와 같이 깔끔하게 해결을 할 수 있다.

- **@Data**

  ```java

  * @see Getter
  * @see Setter
  * @see RequiredArgsConstructor
  * @see ToString
  * @see EqualsAndHashCode
  * @see lombok.Value
  */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Data {
    // 생략
    }

  ```

  위의 코드는 @Data 어노테이션이 구현되어있는 코드이다. 위에서 볼 수 있듯이 @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode가 포함되어있는 것을 확인할 수 있다. 쉽게 말해 이 5개 어노테이션을 묶어서 파는 기획상품?이라고 표현할 수도 있을 것 같다.

  하지만 @Data를 사용하면 무분별한 Setter의 남용으로 객체의 안전성을 보장받기 힘들고 @ToString으로 인한 양방향 연관관계시 순환 참조 문제와 같이 문제가 발생할 수 있어 지양하는 것이 좋다고 합니다.

  [참고] : https://cheese10yun.github.io/lombok/

- **@Builder**
  > 이 어노테이션 또한 Builder를 자동으로 만들어주는 어노테이션입니다.

이렇게 Lombok에 대하여 알아보았습니다. Lombok은 코드의 다이어트와 가독성 및 유지보수에 많은 도움이 될 수 있습니다. 하지만 편리한만큼 잘 못 사용하여 독이 될 수도 있다고 합니다. 따라서 객체를 생성하거나 메서드를 제공할 때 캡슐화나 응집도, 결합도 측면을 조금만 더 깊게 생각해보고 Lombok을 상황에 맞게 사용하는 것이 중요하다고 생각을 합니다.

출처 : https://hooongs.tistory.com/244

---

#### Test에서 `Lombok` 사용하는 방법

테스트 코드에서 Lobmok을 사용하려고 했더니 패키지를 찾지 못한다는 에러로 골머리를 썩었다.

여차저차 알아보니 테스트환경에서 lombok을 사용하려면 test 의존성 scope를 추가해주어야 한다. 테스트 코드를 이때까지 사용하지 않아서 벌어진 참극이다.

**build.gradle**
```
dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.14'
    annotationProcessor 'org.projectlombok:lombok:1.18.14'
    
    testCompileOnly 'org.projectlombok:lombok:1.18.14' // 테스트 의존성 추가
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.14' // 테스트 의존성 추가
}
```