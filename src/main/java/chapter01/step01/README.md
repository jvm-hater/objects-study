#티켓 판매 애플리케이션

## 요구사항 분석

소극장의 홍보도 겸해서 작은 이벤트를 기획하기로 했다. 
이벤트 내용은 추첨을 통해 선정된 관람객에게 공연을 무료로 관람할 수 있는 
초대장을 발송하는 것이다.

이벤트는 성공했고 공연날 관람객을 맞이해야하는데 염두해야 할 점이 있다.


이벤트에 당첨된 관람객과 그렇지 못한 관람객은 다른 방식으로 입장시켜야 한다는 것이다.
이벤트에 당첨된 관람객은 초대장을 티켓으로 교환한 후에 입장할 수 있다. 이벤트에 당첨되지 않은 
관람객은 티켓을 구매해야만 입장이 가능하다. 그렇기에 입장하기전에 이벤트 당첨여부를
확인해야한다. 


### 1. 이벤트 당첨자에게 초대장을 보내보자!!

Invitation.java

```java
package chapter01.step01;

import java.time.LocalDateTime;

public class Invitaion {

    private LocalDateTime when;
}
```

Ticket.java

```java
package chapter01.step01;

public class Ticket {
    private Long fee;

    public Long getFee() {
        return fee;
    }
}
```

Bag.java

```java
package chapter01.step01;

public class Bag {
    private Long amount;
    private Invitaion invitaion;
    private Ticket ticket;

    public Bag(long amount) {
        this(amount, null); // 초대장 없는 경우 (당첨 X)
    }

    public Bag(Long amount, Invitaion invitaion) {
        this.amount = amount;
        this.invitaion = invitaion; // 초대장을 갖고 있는 경우 (당첨 O)
    }

    public boolean hasInvitation() {
        return invitaion != null;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

Audience.java

```java
package chapter01.step01;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
```

TicketOffice.java

```java
package chapter01.step01;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, List<Ticket> tickets) {
        this.amount = amount;
        this.tickets = tickets;
    }

    public Ticket getTicket() {
        return tickets.remove(0);
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

TicketSeller.java

```java
package chapter01.step01;

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
}
```

자 이제 모든 준비가 끝났다. 이 클래스들을 조합하여 소극장에 입장 시키는 로직만
완성하면 끝이다.

Theater.java

```java
package chapter01.step01;

public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```

자 구현을 마쳤다. 프로그램은 동작하고 로직도 간단하다. 하지만 여기엔 문제점들이 숨어있다.
우리는 이것을 찾아 고쳐나가보도록 하자.
