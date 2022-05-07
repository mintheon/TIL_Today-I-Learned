# 어떤 상황?

Spring Boot에서 JPA를 사용해서 DB에서 Entity를 받아오며 [일반 코드 문자열로 되어있는 부분을 Enum으로 바꾸는 `@Converter`](https://techblog.woowahan.com/2600/) 를 통해 데이터를 받아오는 중 발생한 에러였다.

# 원인

해당 필드 타입이 `Enum` 타입이 아닌 `String` 타입으로 되어있지 않아 생긴 에러였다. 서로 호환이 안되는 타입이라 Reflection을 통해 값을 넣을 수 없어서 발생한 에러라고 볼 수 있다.

다른 사례를 찾아보니 DB에서 not null 지정이 되어있지 않은 컬럼에 `Primitive Type` 을 사용할 경우 발생되는 에러라고 한다. (null이 들어갈 가능성이 있는데 primitive는 null이 들어가지 못하니까)

# 해결

해당 Entity 클래스에서 `String` 타입을 `Enum` 타입으로 변경하였다.
