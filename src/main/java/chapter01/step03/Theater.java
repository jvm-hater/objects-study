package chapter01.step03;

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
