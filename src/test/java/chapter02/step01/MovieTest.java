package chapter02.step01;

import chapter02.step01.discount.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Movie 테스트")
class MovieTest {

    private final Money FIXED_DISCOUNT_AMOUNT = Money.wons(800);
    private final double DISCOUNT_PERCENT = 0.1;

    private final LocalDateTime MONDAY = LocalDate.of(2022, Month.APRIL, 25).atStartOfDay();
    private final LocalDateTime TUESDAY = MONDAY.plusDays(1);
    private final LocalDateTime THURSDAY = MONDAY.plusDays(3);
    private final LocalDateTime SUNDAY = MONDAY.plusDays(6);
    private final LocalDateTime SUNDAY_AFTERNOON = SUNDAY.withHour(13).withMinute(30);

    private final DiscountPolicy AVATAR_DISCOUNT_POLICY = new AmountDiscountPolicy(
            FIXED_DISCOUNT_AMOUNT,
            new SequenceCondition(1),
            new SequenceCondition(10),
            new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
    );

    private final DiscountPolicy TITANIC_DISCOUNT_POLICY = new PercentDiscountPolicy(
            DISCOUNT_PERCENT,
            new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
            new SequenceCondition(2),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))
    );

    private final DiscountPolicy STAR_WARS_DISCOUNT_POLICY = new NoneDiscountPolicy();

    Movie getAvatar() {
        return new Movie("아바타", Duration.ofMinutes(120), Money.wons(10000), AVATAR_DISCOUNT_POLICY);
    }

    Movie getTitanic() {
        return new Movie("타이타닉", Duration.ofMinutes(180), Money.wons(11000), TITANIC_DISCOUNT_POLICY);
    }

    Movie getStarWars() {
        return new Movie("스타워즈", Duration.ofMinutes(210), Money.wons(10000), STAR_WARS_DISCOUNT_POLICY);
    }

    abstract class TestCalculateMovieFee {

        abstract Movie getMovie();

        Money getBasicFee() {
            return getMovie().getFee();
        }

        Money subject(Screening screening) {
            return getMovie().calculateMovieFee(screening);
        }
    }

    @Nested
    @DisplayName("calculateMovieFee 메서드는")
    class DescribeCalculateMovieFee {

        @Nested
        @DisplayName("주어진 영화가 '아바타'일 때 (할인 조건: 상영 시작 시간, 상영 순번 / 할인 금액 : 고정 금액)")
        class ContextWithAvatar extends TestCalculateMovieFee {

            @Override
            Movie getMovie() {
                return getAvatar();
            }

            @Nested
            @DisplayName("상영 시작 시간이 할인 조건에 맞는다면")
            class ContextWithValidPeriod {

                private final List<LocalDateTime> VALID_PERIODS = List.of(
                        MONDAY.withHour(10).withMinute(0),
                        MONDAY.withHour(11).withMinute(59),
                        MONDAY.withHour(10).withMinute(1),
                        MONDAY.withHour(11).withMinute(58),
                        THURSDAY.withHour(10).withMinute(0),
                        THURSDAY.withHour(20).withMinute(59),
                        THURSDAY.withHour(10).withMinute(1),
                        THURSDAY.withHour(11).withMinute(58)
                );

                List<Screening> getScreens() {
                    return VALID_PERIODS.stream()
                            .map(period -> new Screening(getMovie(), 0, period))
                            .collect(Collectors.toList());
                }

                @Test
                @DisplayName("고정 할인 금액만큼 할인된 금액을 반환한다.")
                void return_discounted_fee() {
                    for (Screening screening : getScreens()) {
                        Money money = subject(screening);
                        assertThat(money).isEqualTo(getBasicFee().minus(FIXED_DISCOUNT_AMOUNT));
                    }
                }
            }

            @Nested
            @DisplayName("상영 시작 시간이 할인 조건에 맞지 않는다면")
            class ClassWithInvalidPeriod {

                private final List<LocalDateTime> INVALID_PERIODS = List.of(
                        MONDAY.withHour(9).withMinute(59),
                        MONDAY.withHour(12).withMinute(0),
                        THURSDAY.withHour(9).withMinute(59),
                        THURSDAY.withHour(21).withMinute(0),
                        TUESDAY.withHour(10).withMinute(0),
                        TUESDAY.withHour(10).withMinute(1),
                        TUESDAY.withHour(10).withMinute(30)
                );

                List<Screening> getScreens() {
                    return INVALID_PERIODS.stream()
                            .map(period -> new Screening(getMovie(), -1, period))
                            .collect(Collectors.toList());
                }

                @Test
                @DisplayName("할인되지 않은 금액을 반환한다.")
                void return_not_discounted_fee() {
                    for (Screening screening : getScreens()) {
                        Money money = subject(screening);
                        assertThat(money).isEqualTo(getBasicFee());
                    }
                }
            }

            @Nested
            @DisplayName("상영 순번이 할인 조건에 맞는다면")
            class ContextWithValidSequence {

                private final List<Integer> SEQUENCES = List.of(1, 10);

                List<Screening> getScreens() {
                    return SEQUENCES.stream()
                            .map(sequence -> new Screening(getMovie(), sequence, SUNDAY_AFTERNOON))
                            .collect(Collectors.toList());
                }

                @Test
                @DisplayName("고정 할인 금액만큼 할인된 금액을 반환한다.")
                void return_discounted_fee() {
                    for (Screening screening : getScreens()) {
                        Money money = subject(screening);
                        assertThat(money).isEqualTo(getBasicFee().minus(FIXED_DISCOUNT_AMOUNT));
                    }
                }
            }

            @Nested
            @DisplayName("상영 순번이 할인 조건에 맞지 않는다면")
            class ContextWithInvalidSequence {

                private final List<Integer> SEQUENCES = List.of(2, 9);

                List<Screening> getScreens() {
                    return SEQUENCES.stream()
                            .map(sequence -> new Screening(getMovie(), sequence, SUNDAY_AFTERNOON))
                            .collect(Collectors.toList());
                }

                @Test
                @DisplayName("할인되지 않은 금액을 반환한다.")
                void return_not_discounted_fee() {
                    for (Screening screening : getScreens()) {
                        Money money = subject(screening);
                        assertThat(money).isEqualTo(getBasicFee());
                    }
                }
            }
        }
    }
}
