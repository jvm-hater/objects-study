package chapter01.step03;

public class Audience {
    private Bag bag;

    /**
     * 가방에 대한 의존성 역시 없애 버렸다.
     * 이제 관람객은 가방에 대해 알지 못한다. 의존도 하지 않는다.
     *
     * @param ticket
     * @return ticketFee
     */
    public Long buy(Ticket ticket) {
        return bag.hold();
    }
}
