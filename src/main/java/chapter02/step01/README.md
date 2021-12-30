# 요구사항 살펴보기

이번 장에서는 온라인 영화 예매 시스템이다. 사용자는 영화 예매 시스템을 이용해 쉽고 빠르게
보고 싶은 영화를 예매할 수 있다.

먼저 용어 정리를 하자면, 여기서 "영화"는 영화가 갖고 있는 기본정보를 포함한다. 예를 들면,
상영시간, 가격 정보와 같은 것이 이에 해당된다.
"상영"은 실제로 관객들이 영화를 관람하는 행동을 표현한다. 

사용자는 "상영"을 위해 돈을 지불하고 여기서 특정한 예매자는 할인을 받을 수 있다. 할인액을
결정하는 두 가지 규칙이 존재하는데, 하나의 할인 조건이라고 부르고 다른 하나는 할인 정책
이라고 부른다.

"할인 조건"은 가격의 할인 여부를 결정하며 "순서 조건"과 "기간 조건"의 두 종류로 나눌 수 있다.

먼저 "순서 조건"은 상영 순번을 이용해 할인 여부를 결정하는 규칙이다. 
ex) 순번이 10번일 경우 매일 10번째로 상영되는 영화를 예매한 사용자들에게 할인 혜택을 제공한다.

두 번째로, "기간 조건"은 영화 상영 시작 시간을 이용해 할인 여부를 결정한다. 기간 조건은
요일, 시작 시간, 종료 시간의 세 부분으로 구성되며 영화 시작 시간이 해당 기간 안에 포함될
경우 요금을 할인한다.

그리고 금액 할인 정책과 비율 할인 정책 할인 정책 없음 세 가지 할인 정책이 존재한다.

### 협력, 객체 클래스

클래스 기반의 객체지향 언어에 익숙한 사람이라면 가장 먼저 어떤 클래스(class)가 필요한지
고민할 것이다. 하지만 이것이 본질이 아니다. 그렇다면 무엇이 본질일까?

1. 어떤 객체들이 필요한지를 고민하라. 클래스는 공통적인 상태와 행동을 공유하는 객체를 
   추상화 한 것이다.

2. 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체의 일원으로 보자


독립적으로 존재가 아닌 행동을 할 수 있는 것이다. 독립적인 행동을 하는 객체가 협력하여
하나의 프로그램이 되야한다.


### 도메인 구조를 따르는 프로그램 구조

도메인이라는 용어도 살펴봐야한다. 영화 예매 시스템의 목적은 영화를 좀 더 쉽고 빠르게 예매
하려는 사용자의 문제를 해결하는 것이다. 이처럼 문제를 해결하기 위해 사용자가 프로그램을 사용하는
분야를 도메인이라고 부른다.

도메인으로 각 클래스를 나눠보자

1. Movie - 영화 기본정보
2. Screening - 영화 상영
3. DiscountPolicy - 할인 정책
4. AmountDiscountPolicy - 금액 할인 정책
5. PercentDiscountPolicy - 비율 할인 정책
6. DiscountCondition - 할인 조건
7. SequenceCondition - 순번 조건
8. PeriodCondition - 기간 조건

### 클래스 구현하기

Screening클래스는 사용자들이 예매하는 대상인 '상영'을 구현한다.

Screening.java
```java
public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;
    private DiscountPolicy discountPolicy;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened, DiscountPolicy discountPolicy) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
        this.discountPolicy = discountPolicy;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Money getMovieFee() {
        return movie.getFee();
    }
}
```

여기서 주목할 점은 변수는 private 메서드는 public이라는 점이다. 클래스를 구현/사용할 때
가장 중요한 것은 클래스의 경계를 구분짓는 것이다. 

반드시 외부에서는 객체의 속성에 직접 접근할 수 없도록 막고, public 메서드를 통해서만
변경할 수 있게 해야한다.

### 자율적인 객체

1. 객체는 상태와 행동을 함께 가지는 복합적인 존재이다. 
2. 객체가 스스로 판단하고 행동하는 자율적인 존재이다.

이 두 사실을 파악하고 시작해보자.

많은 사람들은 객체를 상태와 행동을 함께 포함하는 식별 가능한 단위로 정의한다. 하지만 
객체지향 프로그래밍에서는 객체의 단위 안에 데이터와 기능을 한번에 묶어 문제 영역의
아이디어를 적절하게 표현할 수 있다. 이를 우리는 캡슐화라고 부른다.

그래서 자바에서는 private protected public등 접근 제어 키워드를 제공하고 
이를 통해 접근 제어를 한다.

접근을 통제하는 이유는 객체를 자율적 존재로 만들기 위해서이다. 객체지향의 핵심은 
스스로 상태를 관리하고, 판단하고, 행동하는 자율적인 객체들의 공동체를 구성하는 것이다.
객체가 자율적인 존재가 되기 위해서 외부의 간섭을 최소화 해야한다.

캡슐화와 접근 제어는 객체를 둘로 나눈다.

1. 외부에서 접근 가능한 부분인 public interface
2. 오직 내부에서만 접근 가능한 부분인 implements

여기서 말하는 내부는 인터페이스를 구현한 구현체들을 말하는 것이다. 예를 들어 우리는
자동차는 알지만 K3나 카니발에대해는 알지 못한다. 그렇다고 우리가 그걸 알아야만 탈 수
있는 것도 아니다. 각자 자율적으로 존재하고 우리는 차만 알면 되는 것이다. 이런 원리를
이용해 객체지향에는 인터페이스를 이용한 DIP원칙을 지킨다.

### 협력하는 객체들의 공동체

예매를 위한 reserve라는 예매 메서드를 만들고 이때 calculateFee를 통해 계산하여
Reservation이라는 생성자에 넣어 반환한다.

Screening.java
```java
public class Screening {
   public Reservation reserve(Customer customer, int audienceCount) {
      return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
   }

   private Money calculateFee(int audienceCount) {
      return movie.calculateMovieFee(this).times(audienceCount);
   }
}
```

그리고 금액과 관련된 다양한 계산을 구현하기 위한 Money 클래스를 만든다.

Money.java
```java
public class Money {

    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money amount) {
        return new Money(this.amount.add(amount.amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money times(double percent) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }
}
```

이 때 금액을 구현하기 위해 Long 타입을 사용했던 것을 기억하자. Money는 Long과 다르게
저장하는 값이 금액과 관련되어 있다는 의미를 전달할 수 없다.

객체 지향의 장점은 객체를 이용해 도메인의 의미를 풍부하게 표한할 수 있다는 것이다.

Reservation 클래스는 고객, 상영 정보, 예매 요금, 인원 수를 속성으로 포함한다.

Reservation.java
```java
public class Reservation {

    private Customer customer;
    private Screening screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
```
위 클래스들을 분석하면 Screening, Movie, Reservation 인스턴스들은 서로의 메서드를
호출 하며 상호 작용 하는 것을 알 수 있다.

### 할인요금 구하기

Movie.java
```java
public class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calcuateDiscountAmount(screening));
    }
}
```

calculateMovieFee 메서드는 discountPolicy에 calculateDiscountAmount 메시지를 
전송해 할인 요금을 반환 받고 반환된 할인 요금을 차감한다.

하지만 이 메서드 안에는 어떤 할인정책을 사용할 것인지 결정하는 코드가 어디에도 존재하지
않는다. 단지 discountPolicy에 메시지를 전송할 뿐이다.

여기에는 상속과 다형성이라는 개념이 숨어있고 두 개념은 추상화를 기반으로 하고있다. 

그렇다면 코드를 살펴보자.

DiscountPolicy.java
```java
public abstract class DiscountPolicy {
    private List<DiscountCondition> conditons = new ArrayList<>();

    public DiscountPolicy(List<DiscountCondition> conditons) {
        this.conditons = conditons;
    }

    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditons) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}
```

할인을 만족하는 DiscountCondition이 하나라도 존재한다면 추상메서드인 getDiscountAmount
를 호출하여 할인 요금을 계산하고 존재하지 않는다면 0원을 반환한다.

이 때 추상 메서드를 자식 클래스에서 오버라이딩하여 구현된 메서드가 실행 되는데
이처럼 부모 클래스에 기본적인 알고리즘을 구현하고 중간에 필요한 처리를 클래스에게 
위임하는 디자인 패턴을 TEMPLETE METHOD 패턴이라고 부른다.

DiscountCondition.java
```java
public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
```

처음에 두 가지에 할인 조건이 존재 했다.

SequenceCondition.java
```java
public class SequenceCondition implements DiscountCondition {

    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
``` 
필드에 존재하는 sequence와 상영 순번이 일치할 경우 할인이 가능한 것으로 판단 되어
true를 반환해주고 아닐 경우 false를 반환한다.

PeriodCondition.java
```java
public class PeriodCondition implements DiscountCondition {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
```

특정 기간 안에 포함 되는지 여부를 판단해서 할인 여부를 결정한다. 
startTime과 endTime 사이에 있고 상영 요일과 같을 때 true를 반환한다.

이제 할인 정책을 구현해보자.

AmountDiscountPolicy.java
```java
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
```
DiscountPolicy를 상속 받아 기본적으로 할인 금액이 정해져 있어 getDiscountAmount를
오버라이딩하여 해당 금액만큼 할인이 들어간다.

PercentDiscountPolicy.java
```java
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
```

필드에 percent를 통해 일정 비율을 정하여 getDiscountAmount 메서드를 통하여
비율 만큼 할인 금액에서 차감 한다.

하지만 여기서 우리는 여러개의 할인 정책을 DiscountPolicy가 받을 수 있도록 설계했지만
Movie는 단 한개의 인스턴스만 받을 수 있다.

```java
class MovieTest {
   Movie givenAbatar() {
      return new Movie(
              "아바타",
              Duration.ofMinutes(120),
              Money.wons(10000),
              new AmountDiscountPolicy(Money.wons(800)),
              abatarCondition
      );
   }
}
```
다음 코드를 보면 아바타에 대한 할인 정책과 조건을 두 개 의 순서 조건과 두 개의 기간 조건
을 이용해 할인 여부를 판단하는 것을 알 수 있다. 이 때 우리는 어떻게 해야할까?

