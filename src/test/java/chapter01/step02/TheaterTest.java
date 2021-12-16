package chapter01.step02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("극장 입장 테스트")
class TheaterTest {

    final Long givenTicketFee = 1000L;
    final Ticket givenTicket = new Ticket(givenTicketFee);

    @Nested
    @DisplayName("buy 메소드는")
    class buyTicket {

        @Nested
        @DisplayName("초대장이 있는 경우")
        class HasInvitation {
            final Invitaion givenInvite = new Invitaion();
            final Bag givenBag = new Bag(100000L, givenInvite);

            Audience InviteAudience() {
                return new Audience(givenBag);
            }

            @Test
            @DisplayName("초대장이 없을 경우 무료 입장")
            void freeEnter() {
                final Audience audience = InviteAudience();
                final Long paid = audience.buy(givenTicket);

                assertTrue(givenBag.hasTicket(), "(사이드 이펙트) 관람객은 티켓을 갖고있다.");
                assertEquals(paid, Long.valueOf(0L), "티켓 비용은 0원이다.");
            }
        }

        @Nested
        @DisplayName("초대장이 없는 경우")
        class NoInvitation {
            final Bag givenBag() {
                return new Bag(100000L);
            }

            Audience givenNoInvite() {
                return new Audience(givenBag());
            }

            @Test
            @DisplayName("관람객의 가방에 티켓 추가, 티켓 값 지불")
            void buyTicket() {
                final Bag bag = givenBag();
                final Long paid = givenNoInvite().buy(givenTicket);

                assertFalse(bag.hasTicket(), "(사이드 이펙트) 관람객은 티켓을 갖고있다.");
                assertEquals(paid, givenTicketFee, "관람객이 티켓 값을 지불했다.");
            }
        }
    }
}