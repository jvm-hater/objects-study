package chapter01.step01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Theater 테스트")
class TheaterTest {

    private long amount;
    private Theater theater;
    private TicketOffice ticketOffice;
    private TicketSeller ticketSeller;

    @BeforeEach
    void setUp() {
        amount = 10000L;
        ticketOffice = new TicketOffice(amount, new Ticket(500L), new Ticket(500L), new Ticket(500L));
        ticketSeller = new TicketSeller(ticketOffice);
        theater = new Theater(ticketSeller);
    }

    @Test
    @DisplayName("이벤트의 당첨된 관람객은 초대장을 티켓으로 교환하여 입장한다.")
    void enter_event_win() {
        long ticketOfficeAmount = ticketOffice.getAmount();

        long amount = 5000L;
        Bag bag = new Bag(new Invitation(), amount);
        Audience audience = new Audience(bag);
        theater.enter(audience);

        assertThat(bag.hasInvitation()).isTrue();
        assertThat(bag.getAmount()).isEqualTo(amount);
        assertThat(bag.hasTicket()).isTrue();

        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount);
    }

    @Test
    @DisplayName("이벤트의 낙첨된 관람객은 티켓을 돈으로 구매하여 입장한다.")
    void enter_event_lose() {
        long ticketOfficeAmount = ticketOffice.getAmount();

        long amount = 5000L;
        Bag bag = new Bag(amount);
        Audience audience = new Audience(bag);
        theater.enter(audience);

        assertThat(bag.hasInvitation()).isFalse();
        assertThat(bag.getAmount()).isLessThan(amount);
        assertThat(bag.hasTicket()).isTrue();

        assertThat(ticketOffice.getAmount()).isGreaterThan(ticketOfficeAmount);
    }
}