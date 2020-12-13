# 모듈 디자인 패턴

- 객체지향 언어에서 모듈은 클래스와 똑같다. 클래스는 클래스를 캡슐화하기 좋고, 코드의 재사용성을 높여준다.

## IIFE

- 대개 모듈 디자인 패턴을 사용할 땐 클로저를 사용하여 외부에서 모듈의 멤버 변수, 함수등에 접근할 수 없게 한다. 아래는 즉시실행 함수IIFE(Immediately-Invoked-Function-Expressions)를 사용하여 구현하였다.

```js
let ModuleDesign = (function() {
  let grade = "A";
  let score = 96;

  const getGrade = function() {
    return grade;
  }

  const getScore = function() {
    return score;
  }

  return {
    getMyGrade: getGrade,
    getMyScore:getScore,
  }
})()

console.log(ModuleDesign.getMyScore()); //96
```

이 모듈은 내부적으로 두가지 변수를 가지고 있다. `grade`와 `score`변수는 외부에서 참조하지 못한다. `getGrade`와 `getScore`는 각 변수를 반환해준다.  
이렇게 설계할경우 모듈을 외부로부터 보호된 환경에서 작성할 수 있어 좀 더 안전하게(외부와 결합도를 낮춘상태에서) 모듈을 만들 수 있다. 클래스 개념에서 `getGrade`와 `getScore`는 **public** 이라고 볼수있으며, 내보내지 않은 변수는 **private**라고 볼 수 있다.

## ES6이후 디자인 패턴 구현 방법

- `export` 키워드를 사용해 모듈을 간단하게 내보낼 수 있게 바뀌어 파일 단위로 모듈을 관리하는 방식으로 많이 구현되었다. [(export 참고)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/export)
- `export`로 내보낸 모듈은 `import`키워드로 모듈을 불러와서 모듈이 내보낸 객체, 함수, 변수등 모든것을 사용할 수 있다.

```js
//fetch.js
const fetchUser = async id => {
  try {
    return await fetch(process.env.SERVER_URL, {id});
  } catch (error) {
    throw new Error(error)
  }
}

export default fetchUser;
```

```js
//user.js
import fetchUser "./fetch"

const user = fetchUser(id);
```

## 클래스

- ES6 이후엔 클래스 문법을 사용할 수 있기 때문에 클래스기능을 이용하면 동일하게 구현 가능하다.

```js
class Student {
  #grade = "A"
  #score = 96

  get grade() {
    return this.#grade;
  }

  get score() {
    return this.#score;
  }
}

const student = new Student();

student.grade // "A"
student.score // 96
student.#grade // SyntaxError: Private field '#grade' must be declared in an enclosing class
```

클래스에서 변수 앞에 `#`을 명시하면 해당 변수는 private한 변수로 지정된다. 외부에서 이 변수에 접근할 수 없으나, **getter**를 두어 외부에서 접근하게 만든다. 그리고 이전에 나왔던 `export`를 사용하여 클래스를 내보내면 ES6이전의 모듈 디자인 패턴과는 달리 아주 깔끔하게 코드를 작성할 수 있다.