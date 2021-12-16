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
        this.amount = amount;
    }
}
