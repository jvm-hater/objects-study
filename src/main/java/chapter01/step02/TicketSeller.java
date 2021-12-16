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
