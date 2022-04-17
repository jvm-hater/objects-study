package chapter01.step03;

public class Bag {

    private Long amount;

    private Invitation invitation;

    private Ticket ticket;

    public Bag(long amount) {
        this(null, amount);
    }

    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    public long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        }
        setTicket(ticket);
        minusAmount(ticket.getFee());
        return ticket.getFee();
    }

    public Long getAmount() {
        return amount;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount(Long amount) {
        this.amount -= amount;
    }

    private void plusAmount(Long amount) {
        this.amount += amount;
    }
}
