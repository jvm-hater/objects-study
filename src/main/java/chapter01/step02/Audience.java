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
