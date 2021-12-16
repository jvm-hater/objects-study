package chapter01.step03;

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }


    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience); // 이로써 이제 어떤 티켓 판매소인지는 중요하지 않아졌다.
    }
}
