package chapter01.step02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Theater 테스트")
class TheaterTest {

    private long ticketOfficeAmount;
    private long ticketAmount;
    private TicketOffice ticketOffice;
    private TicketSeller ticketSeller;
    private Theater theater;

    @BeforeEach
    void setUp() {
        ticketOfficeAmount = 10000L;
        ticketAmount = 500L;
        ticketOffice = new TicketOffice(ticketOfficeAmount, new Ticket(ticketAmount), new Ticket(ticketAmount));
        ticketSeller = new TicketSeller(ticketOffice);
        theater = new Theater(ticketSeller);
    }

    @Test
    @DisplayName("초대장이 있는 사람을 소극장에 입장시킨다.")
    void enter_with_invitation() {
        long amount = 1000L;
        Bag bag = new Bag(new Invitation(), amount);
        Audience audience = new Audience(bag);

        theater.enter(audience);
        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount);
        assertThat(bag.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("초대장이 없는 사람을 소극장에 입장시킨다.")
    void enter_with_not_invitation() {
        long amount = 1000L;
        Bag bag = new Bag(amount);
        Audience audience = new Audience(bag);

        theater.enter(audience);
        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount + ticketAmount);
        assertThat(bag.getAmount()).isEqualTo(amount - ticketAmount);
    }

}