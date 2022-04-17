package chapter01.step02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AudienceTest {

    @Test
    @DisplayName("초대장이 있으면 초대장으로 티켓으로 구입한다.")
    void buy_use_invitation() {
        long amount = 1000L;
        Bag bag = new Bag(new Invitation(), amount);
        Audience audience = new Audience(bag);

        long ticketAmount = 500L;
        assertThat(audience.buy(new Ticket(ticketAmount))).isZero();
        assertThat(bag.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("초대장이 있으면 돈으로 티켓을 구입한다.")
    void buy_use_amount() {
        long amount = 1000L;
        Bag bag = new Bag(amount);
        Audience audience = new Audience(bag);

        long ticketAmount = 500L;
        assertThat(audience.buy(new Ticket(ticketAmount))).isEqualTo(ticketAmount);
        assertThat(bag.getAmount()).isEqualTo(amount - ticketAmount);
    }

}