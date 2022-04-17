package chapter01.step01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TicketOffice 테스트")
class TicketOfficeTest {

    private long amount;
    private TicketOffice ticketOffice;

    @BeforeEach
    void setUp() {
        ticketOffice = new TicketOffice(amount, new Ticket(500L), new Ticket(500L), new Ticket(500L));
    }

    @Test
    @DisplayName("티켓 하나를 가져온다.")
    void getTicket() {
        amount = 10000L;
        Ticket ticket = ticketOffice.getTicket();
        assertThat(ticket.getFee()).isEqualTo(500L);
    }

    @Test
    @DisplayName("판매 금액을 조절한다.")
    void plusAndMinus() {
        ticketOffice.plusAmount(500L);
        assertThat(ticketOffice.getAmount()).isEqualTo(amount + 500L);

        ticketOffice.minusAmount(500L);
        assertThat(ticketOffice.getAmount()).isEqualTo(amount);
    }
}