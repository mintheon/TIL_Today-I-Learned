# 자바의 EOF 처리법

알고리즘 문제를 풀다가 입력 개수를 제한하지 않는 문제를 풀게 된 적이 있다. 대부분 N개의 입력을 받는다고 명시하나 해당 문제는 그런게 적혀있지 않아 이상했지만 그냥 풀었다.

역시나.. 일부러 노린 문제였다. `출력초과`라는 에러가 났다.

[해당 문제(A+B-4)](https://www.acmicpc.net/problem/10951)

최근에 코딩테스트를 진행하면서 비슷한 유형의 문제가 나와 정리하게 되었다.

입력 클래스에 대한 정보는 [이곳](https://mygumi.tistory.com/78)을 참조.

---

### Scanner 클래스

```java
Scanner sc = new Scanner(System.in);

while(sc.hasNextLine()) {
   sc.nextLine();
}

while(sc.hasNextInt()) {
   sc.nextInt();
}
```

### BufferedReader 클래스

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String input = "";

while((input = br.readLine()) != null) {

}
```

`Scanner`클래스 경우에는 타입에 따른 입력으로 편의를 제공하지만, `BufferedReader`보다 속도가 느리다.

또한 `StringTokenizer`로 `EOF`를 처리하려고 생각할 수 있지만, 해당 구문은 문자열을 입력하는것이 아닌, 입력받은 문자열을 가공하는 역할을 하기 때문에 `EOF` 처리한다고 보기는 어렵다.