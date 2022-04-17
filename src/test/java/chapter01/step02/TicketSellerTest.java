package chapter01.step02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TicketSellerTest {

    private long ticketOfficeAmount;
    private long ticketAmount;
    private TicketOffice ticketOffice;
    private TicketSeller ticketSeller;

    @BeforeEach
    void setUp() {
        ticketOfficeAmount = 10000L;
        ticketAmount = 500L;
        ticketOffice = new TicketOffice(ticketOfficeAmount, new Ticket(ticketAmount), new Ticket(ticketAmount));
        ticketSeller = new TicketSeller(ticketOffice);
    }

    @Test
    @DisplayName("초대장을 받고 티켓을 판매한다.")
    void sellTo_use_invitation() {
        long amount = 1000L;
        Bag bag = new Bag(new Invitation(), amount);
        Audience audience = new Audience(bag);

        ticketSeller.sellTo(audience);
        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount);
        assertThat(bag.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("돈을 받고 티켓을 판매한다.")
    void sellTo_use_amount() {
        long amount = 1000L;
        Bag bag = new Bag(amount);
        Audience audience = new Audience(bag);

        ticketSeller.sellTo(audience);
        assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeAmount + ticketAmount);
        assertThat(bag.getAmount()).isEqualTo(amount - ticketAmount);
    }
}