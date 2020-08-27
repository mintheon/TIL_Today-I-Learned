## Currying(커링)

- 커링, 커리함수(curried function)
- **함수 내부에서 변수 선언없이 바로 함수를 리턴**
- **JS에서는 클로저의 원리로 구현**되지만, 이런 패턴의 코드를 커링이라고 구분지어 부름

```js
const curring = (arg1) => {
  return (arg2) => {
    return arg1 + arg2;
  };
};

// 깔꼼버전
const curring = (arg) => (arg2) => arg1 + arg2;
```

#### 언제 쓰는건가?

함수가 인자를 여러번에 걸쳐 받아 실행해야 하는 상황.

#### 왜 쓰는건가?

1. 함수의 **재사용성**을 높일 수 있다.
2. **라이브러리**와 같이 타인이 작성한 함수를 수정하지 않고 사용할 때 유용하다.

- 커리함수가 처음 받는 인자 : **설정** or **옵션** 등에 해당하는 값
- 나중에 받는 인자 : **적용대상, 값** 등에 해당

### 배열 메서드 커링으로 구현

`map()`, `filter()`, `reduce()`, `sort()`, `reverse()`, `slice()`를 커링으로 구현하자.

- 원본 배열을 변경하지 않고 새로운 배열을 리턴하도록 구현
- `map()`, `filter()`, `reduce()`, `sort()`는 `CB`인자를 받도록
- `reverse()` : 인자 없음
- `slice()` : 2개의 숫자 값을 인자로 받음.

```js
const mapArray = (func) => (arr) => {
  return arr.map(func);
};

const filterArray = (func) => (arr) => {
  return arr.filter(func);
};

const reduceArray = (func, init) => (arr) => {
  return arr.reduce(func, init);
};

const sortArray = (func) => (arr) => {
  const newArr = [...arr];
  return newArr.sort(func);
};

const reverseArray = (func) => (arr) => {
  const newArr = [...arr];
  return newArr.reverse(func);
};

const sliceArray = (start, end) => (arr) => {
  return arr.slice(start, end);
};

//사용법
mapArray((el, idx) => `${idx + 1}. ${el}`)([1, 2, 3, 4, 5]);
```

[출처](https://velog.io/@hwang-eunji/Javascript-Curring)
