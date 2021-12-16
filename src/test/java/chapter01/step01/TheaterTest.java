package chapter01.step01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TheaterTest {

    final Long givenFee = 1000L;
    final Ticket givenTicket = new Ticket(givenFee);

    @Nested
    @DisplayName("buy 메소드는")
    class DescribeBuyMethod {

        @Nested
        @DisplayName("초대장이 있는 경우")
        class ContextWithInvitation {
            final Invitaion givenInvite = new Invitaion();
            final Bag givenBag = new Bag(100000L, givenInvite);

            Audience hasTicketAudience() {
                return new Audience(givenBag);
            }

            @Test
            @DisplayName("관람객의 가방에 티켓을 추가하고, 0을 리턴한다.")
            void addHasTicket() {
                final Audience audience = hasTicketAudience();


            }
        }
    }
}