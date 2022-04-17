package chapter01.step03;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TicketOffice 테스트")
class TicketOfficeTest {

    private long ticketOfficeAmount;
    private long ticketAmount;
    private TicketOffice ticketOffice;

    @BeforeEach
    void setUp() {
        ticketOffice = new TicketOffice(ticketOfficeAmount, new Ticket(ticketAmount), new Ticket(ticketAmount));
    }

    @Test
    @DisplayName("초대장이 관람객에게는 돈을 받지 않고 티켓을 준다.")
    void sellTicketTo_with_invitation() {
        long amount = 1000L;
        Bag bag = new Bag(new Invitation(), amount);
        Audience audience = new Audience(bag);
        ticketOffice.sellTicketTo(audience);

        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount);
        assertThat(bag.getAmount()).isEqualTo(amount);
        assertThat(bag.hasTicket()).isTrue();
    }

    @Test
    @DisplayName("초대장이 없는 관람객에게는 돈을 받고 티켓을 준다.")
    void sellTicketTo_with_amount() {
        long amount = 1000L;
        Bag bag = new Bag(amount);
        Audience audience = new Audience(bag);
        ticketOffice.sellTicketTo(audience);

        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount + ticketAmount);
        assertThat(bag.getAmount()).isEqualTo(amount - ticketAmount);
        assertThat(bag.hasTicket()).isTrue();
    }
}