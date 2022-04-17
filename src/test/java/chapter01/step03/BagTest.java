package chapter01.step03;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bag 테스트")
class BagTest {

    @Test
    @DisplayName("초대장이 있으면 돈을 내지 않고 쿠폰을 획득한다.")
    void hold_with_invitation() {
        Bag bag = new Bag(new Invitation(), 1000L);
        assertThat(bag.hold(new Ticket(500L))).isZero();
        assertThat(bag.hasTicket()).isTrue();
    }

    @Test
    @DisplayName("초대장이 없으면 돈을 지불하고 쿠폰을 획득한다.")
    void hold_with_amount() {
        long amount = 1000L;
        Bag bag = new Bag(amount);

        long ticketAmount = 500L;
        assertThat(bag.hold(new Ticket(ticketAmount))).isEqualTo(amount - ticketAmount);
        assertThat(bag.hasTicket()).isTrue();
    }

}