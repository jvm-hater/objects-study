## 상속과 다형성

이제 앞선 질문에 답해 보자. Movie 클래스 어디에도 할인 정책이 금액 할인 정책인지, 비율 할인
정책인지를 판단하지 않는다. Movie 내부에 할인 정책을 결정하는 조건문이 없는데도 불구하고 
Movie는 선택할 수 있을까?

의존성을 살펴보면 알 수 있다.

의존성을 살펴보면 Movie는 DiscountPolicy만 의존하고 상속받은 Amount와 Percent는 상속받지
않았다.

```java
public class Movie {

    private DiscountPolicy discountPolicy;
    
    public Movie(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
(Movie 클래스 DiscountPolicy를 의존하고 있지만 그 외 구체화 된 클래스엔 의존하지 않는다.)

그렇다면 어떤식으로 이 문제를 자바에선 해결을 할까?

위 코드를 보면 생성자를 통해 인스턴스를 받고 있다. 이 때 우리는 인자로 Amount 또는 Percent
인스턴스를 전달하면 된다.

우리는 이를 통해 실행 시점의 의존성과 코드의 의존성이 다를 수 있고 이는 유연하고 쉽게
재사용할 수 있는 이점을 가져다 준다.

한 가지 간과해서는 안되는 사실은 이러한 설계는 이해하기 어렵다는 사실이다. 객체를 생성하고
연결해주는 부분을 차아야 하니 당연한 얘기다. 설계가 유연해질수록 코드를 이해하고
디버깅하기 어려워지는 사실을 기억하하자 무조건 읽기 쉬운 코드가 정답은 아니다. 

이것이 객체지향 설계가 어려운 이유이다.

### 차이에 의한 프로그래밍

상속을 이용하여 코드를 재사용하는 것을 대부분 알고 있을 것이다. 이를 이용하여 
구현은 공유하고 다른 자식 클래스를 쉽게 추가할 수 있다. AmountDiscountPolicy와
PercentDiscountPolicy의 경우 추상클래스인 DiscountPolicy에서 정의한 추상 메서드인
getDiscountAmount메서드를 오버라이딩해서 DiscountPolicy의 행동을 수정하는 것을
알 수 있다.

처럼 부모 클래스와 다른 부분만을 추가해서 새로운 클래스를 쉽고 빠르게 만드는 방법을
차이에 의한 프로그래밍이라 한다.

### 상속과 인터페이스

Movie에 calculateMovieFee 메서드를 살펴보자

Movie.java
```java
public class Movie {
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateAmount(screening));
    }
}
```

Movie가 DiscountPolicy의 인터페이스에 정의된 calculateDiscountAmount 메시지를 전송
하고 있다. 여기서 Movie는 어떤 인스턴스가 들어오던지 상관이 없다. 그냥 메시지를 수신할 수
있다는 사실이 중요하다. 결국 DiscountPolicy를 대신해서 Amount나 Percent 할인 전략
두 클래스가 협력할 수 있다.

결국 DiscountPolicy 타입임에도 AmountDiscountPolicy와 PercentDiscountPolicy가
인자로 들어올 수 있게된다. 이처럼 자식클래스가 부모 클래스를 대신하는 것을 업 캐스팅이라고
한다. 

### 다형성

메시지와 메서드는 다른 개념이다. Movie는 DiscountPolicy의 인스턴스에게 calculateDiscountAmount
메시지를 전송한다. 그럼 실행되는 것은 DiscountPolicy의 추상메서드를 오버라이딩한 메서드가
실행될 것이다. 동일한 메시지를 전송하지만 실제로 어떤 메서드가 실행될 것인지는 메시지를 수신
하는 객체의 클래스가 무엇이냐에 따라 달라지는데 이를 다형성이라고 한다.

다형성을 구현하는 방법은 매우 다양하지만 메시지에 응답하기 위해 실행될 메서드를 컴파일 시점이
아닌 실행 시점에 결정한다는 공통점이 있다. 다시 말해 메시지와 메서드를 실행 시점에 바인딩 하는
것이다. 이를 지연 바인딩 또는 동적 바인딩이라고 한다. 

객체지향이 컴파일 시점의 의존성과 실행 시점의 의존성을 분리하고, 하나의 메시지를 선택적
으로 서로 다른 메서드에 연결할 수 있는 이유가 바로 지연 바인딩이라는 매커니즘을 사용하기
때문이다.

### 인터페이스와 다형성

DiscountPolicy를 추상클래스로 구현함으로 자식 클래스들이 인터페이스와 내부 구현을 함께
상속받도록 만들었다. 그러나 종종 구현은 공유할 필요가 없고 인터페이스만 공유하고 싶을 때가
있다. 이때 자바는 인터페이스를 제공한다. 

할인 정책과 달리 할인 조건은 구현을 공유할 필요가 없었다. 때문에 DiscountCondition
인터페이스를 이용해 타입 계층을 구현했다. 이로써 인터페이스를 공유하며 다형적인 협력에
참여할 수 있다.

### 추상화의 힘

할인정책은 금액과 비율 할인 정책을 포괄하는 추상적인 개념이다. 할인조건 역시 마찬가지이다.
간단하게 개발한 프로그램에서도 DiscountPolicy는 Percent/AmountDiscountPolicy를 추상화
한 개념이라고 할 수 있다. 

객체지향에서 이러한 추상화는 두 가지 장점이 있다.

1. 추상화의 계층만 따로 떼어놓고 살펴보면 요구사항의 정책을 높은 수준에서 서술할 수 있다.
2. 추상화를 이용하여 더욱 유연한 설계가 가능해진다.

![untitle](file:///C:/Users/%EB%85%B8%EA%B2%BD%ED%83%9C/Downloads/Untitled%20Diagram.drawio.html)

우선 첫 번째 장점부터 살펴보자. 위 그림과 같은 설계 구조를 살펴보면 "영화 예매 요금은
최대 하나의 할인 정책과 다수의 할인 조건을 이용해 계산할 수 있다." 로 표현할 수 있다.
이것은 할인 정책과 할인 조건이라는 좀 더 추상적인 개념들을 이용해서 문장을 작성했기 때문
이다.





