## 무엇이 문제일까?

로버트 C. 마틴은 <클린 소프트웨어>에서 모듈이 가져야 하는 세 가지 기능에 대해 설명 한다.

1. 목적은 실행 중에 제대로 동작해야한다.


3. 목적은 변경을 위해 존재하는 것이다. 대부분의 모듈은 생명주기 동안 변경되기 때문에
   간단한 작업만으로 변경이 가능해야한다.


3. 코드를 읽는 사람과 의사소통 하는 것이다. 모듈은 특별한 훈련없이도 개발자가 쉽게
   읽고 이해할 수 있어야 한다.

이 세 가지를 중점으로 코드를 고쳐나가 보자

### 예상을 빗나가는 코드

1. 관람객 입장에서 보면 관람객이 아닌 갑자기 제 3자인 소극장에서 가방을 열어본다.
   이는 말이 안되는 설계이다.


2. 판매원 입장이라면 소극장이 허락도 없이 보관 중인 티켓과 현금에 마음대로 접근할
   수 있다. 마찬가지로 판매원의 역할인데 소극장이 마음대로 할 수 있는 것이다.


이러한 코드들은 리펙토링을 거쳐 고쳐줘야한다.

### 변경에 취약한 코드

변경에 취약한 코드만큼 개발자를 두려움에 떨게하는 코드가 없을 것이다. 각 코드들은
서로 강하게 의존을 하고 있다. 그렇기 때문에 예를 들어 신용카드가 생긴다면? 관람객이
가방을 들고다니지 않은다면?

모든 코드를 뒤엎는 끔찍한 결과를 낳을 수 있다. 우리는 이러한 객체 간 의존성을
줄일 필요가 있다.

### 설계 다시하기

우리는 여기서 관람객과 판매원을 자율적인 존재로 만드는 작업을 할 것이다.

그렇다면 변경해보자

Theater.java
```java
package chapter01.step02;

public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

   /**
    * 이제 Theater는 입장만 도울 뿐 그 외에 어떤 역할도 갖지 않는다.
    *
    * @param audience
    */
    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
```

TicketSeller.java
```java
package chapter01.step02;

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /**
     * @param audience
     *
     * Theater에서 Office에 접근하지 못하게 하자
     * 현재 sellTo 메소드를 만들면서 이제는 판매원만 티켓 오피스에 접근할 수 있게 되었다.
     */
    public void sellTo(Audience audience) {
        // seller 클라이언트는 이제 메소드로 접근하고 bag에 대한건 알지 못한다.
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
```

Audience.java
```java
package chapter01.step02;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    /**
     * 관람객은 이제 더이상 제 3자 개입 없이 스스로 가방 안을 확인한다.
     * 이로써 Audience와 TicketSeller의 캡슐화를 마쳤다.
     *
     * @param ticket
     * @return ticketFee
     */
    public Long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        }
        else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}
```

보다 싶이 의존성이 확떨어진 것을 볼 수 있다. 이제 극장은 관람객의 가방이나 티켓 판매소
를 모른다. 판매소가 변경이 되든 더 이상 극장에서는 신경을 쓸 필요가 없다!

마찬가지로 판매원 입장에서는 손님의 가방을 알 수가 없다. 이제 지갑을 들고오든 무엇을
들고 오든 판매원은 입장 티켓만 받고 처리할 뿐이다.

### 캡슐화와 응집도

핵심은 객체 내부의 상태를 캡슐화하고 객체 간에 오직 메시지를 통해서만 상호작용 하도록
만드는 것이다.

객체간에 의존성이 줄어들면 서로 내부에 대해는 알지 못하고 그저 메시지를 이해하고 응답
만 할 수 있는 것이다. 이러한 방식으로 인해 변경에 대해 빠른 대처가 가능해졌다.

### 책임의 이동

절자척 프로그래밍과 객체지향 프로그래밍의 근본적인 차이는 책임의 이동이다.

절차적 프로그래밍 방식은 모든 흐름이 Theater에 의해 제어된다. 이는 모든 책임이 
Theater한테 집중돼 있는 것을 뜻한다.

하지만 객체지향은 이와 달리 자신 스스로만 책임을 지면 그만인 것이다. 이번 코드로 알
수 있을 것이다. 

Theater에 몰려있던 책임을 Audience와 TicketSeller에게 책임을 분배했다. 극장은 
들여보내는 것에 대한 책임, 관람객은 이벤트 당첨 티켓을 꺼내는 책임, 판매원은 티켓을
확인하고 티켓을 판매하는 책임으로 각각 분리가 된 것이다.

이로써 우리는 변경에 대응할 수 있는 설계를 하였다.

### 정리

설계를 어렵게 만드는 것은 의존성이라는 것을 기억하자. 해결방법은 불필요한 의존성을
제거함으로 객체 간 결합도를 낮추는 것이다. 

이는 세부사항을 감추는 캡슐화하여 가능하게 할 수 있고, 객체의 자율성을 높이고 응집도
는 낮추는 효과를 갖고온다.

