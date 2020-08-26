Function 함수적 인터페이스는 매개값과 리턴값이 있는 `applyXXX()` 메소드를 가지고 있다.

이 메소드는 매개값을 리턴값으로 매핑(타입 변환)하는 역할을 한다.

---

| 인터페이스명            | 추상메소드                       | 설명                     |
| ----------------------- | -------------------------------- | ------------------------ |
| Function<T, R>          | R apply(T t)                     | 객체 T를 R로 매핑        |
| BiFunction<T, U, R>     | R apply(T t, U u)                | 객체 T,U를 객체 R로 매핑 |
| DoubleFunction<R>       | R apply(double value)            | double을 객체 R로 매핑   |
| IntFunction<R>          | R apply(int value)               | int를 객체 R로 매핑      |
| IntToDoubleFunction     | double applyAsDouble(int value)  | int를 double로 매핑      |
| IntToLongFunction       | long applyAsLong(int value)      | int를 long으로 매핑      |
| LongToDoubleFunction    | double applyAsDouble(long value) | long을 double로 매핑     |
| LongToIntFunction       | int applyAsInt(long value)       | long을 double로 매핑     |
| ToDoubleBiFunction<T,U> | double applyAsDouble(T t, U u)   | 객체 T,U를 double로 매핑 |

너무 많기때문에 그 이상은 [자바 Docs를](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) 참고하자.

---

```java
//Student.java
public class Student {
  private String name;
  private int mathScore;
  private int englishScore;

  public Student(String name, int mathScore, int englishScore) {
    this.name = name;
    this.mathScore = mathScore;
    this.englishScore = englishScore;
  }

  public String getName() {
    return name;
  }

  public String getMathScore() {
    return mathScore;
  }

  public String getEnglishScore() {
    return englishScore;
  }
}
```

```java
//FunctionExample.java
public class FunctionExample {
  private static List<Student> list = Arrays.asList(
    new Student("민", 100, 100),
    new Student("바보민", 0, 0)
  );

  public static void printString(Function<Student, String> function) {
    for(Student student : list) {
      System.out.print(function.apply(student) + " ");
    }
    System.out.println();
  }

  public static void printInt(ToIntFunction<Student> function) {
    for(Student student : list) {
      System.out.println(function.apply(student) + " ");
    }
    System.out.println();
  }

  public static void getAverage(ToDoubleFunction<Student> function) {
    int sum = 0;

    for(Student student : list) {
      sum += function.applyAsDouble(student);
    }

    return sum / list.size();
  }

  public static void main(String[] args) {
    System.out.println("-- 학생 이름 --");
    printString(t -> t.getName());

    System.out.println("-- 영어 점수 --");
    printInt(t -> t.getEnglishScore());

    System.out.println("-- 수학 점수 --");
    printInt(t -> t.getMathScore());

    double enlishAvg = getAverage(t -> t.getEnglishScore());
    double mathAvg = getAverage(t -> t.getMathScore());

    System.out.println("영어 평균 점수 : " + englishAvg);
    System.out.println("수학 평균 점수 : " + mathAvg);
  }
}
```

#### 결과값

```
-- 학생 이름 --
민 바보민
-- 영어 점수 --
100 0
-- 수학 점수 --
100 0
영어 폄균 점수 : 50.0
수학 평균 점수 : 50.0
```
