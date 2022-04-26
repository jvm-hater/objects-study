package chapter02.step01.discount;

import chapter02.step01.Money;
import chapter02.step01.Movie;
import chapter02.step01.Screening;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DiscountPolicy 테스트")
class DiscountPolicyTest {

    @Test
    @DisplayName("비율 할인 정책은 할인 조건을 만족한다면, 영화 예매 가격에 대해 일정 퍼센트의 할인 요금을 반환한다.")
    void calculateDiscount_percentDiscountPolicy() {
        DiscountPolicy discountPolicy = new PercentDiscountPolicy(0.1, new SequenceCondition(3));
        Movie movie = new Movie("movie", Duration.ofHours(1L), new Money(BigDecimal.valueOf(3000L)), discountPolicy);

        Money money = discountPolicy.calculateDiscountAmount(new Screening(movie, 3, null));
        assertThat(movie.getFee().times(0.1)).isEqualTo(money);
    }

    @Test
    @DisplayName("금액 할인 정책은 할인 조건을 만족한다면, 영화 예매 가격에 대해 일정 금액의 할인 요금을 반환한다.")
    void calculateDiscount_amountDiscountPolicy() {
        DiscountPolicy discountPolicy = new AmountDiscountPolicy(new Money(BigDecimal.valueOf(500L)), new PeriodCondition(DayOfWeek.MONDAY, LocalTime.MIN, LocalTime.MAX));
        Movie movie = new Movie("movie", Duration.ofHours(1L), new Money(BigDecimal.valueOf(3000L)), discountPolicy);

        Money money = discountPolicy.calculateDiscountAmount(new Screening(movie, 0, LocalDateTime.of(2022, 4, 25, 12, 42)));
        assertThat(new Money(BigDecimal.valueOf(500L))).isEqualTo(money);
    }

    @Test
    @DisplayName("None 할인 정책은 영화 예매 가격에 0원의 요금을 반환한다.")
    void calculateDiscount_noneDiscountPolicy() {
        DiscountPolicy discountPolicy = new NoneDiscountPolicy();
        assertThat(discountPolicy.calculateDiscountAmount(null)).isEqualTo(Money.ZERO);
    }

    @Test
    @DisplayName("비율 혹은 금액 할인 정책은 할인 조건을 만족하지 않으면, 0원의 요금을 반환한디.")
    void calculateDiscount_not_satisfy_discountCondition() {
        DiscountPolicy discountPolicy = new PercentDiscountPolicy(0.1, new SequenceCondition(3));
        Movie movie = new Movie("movie", Duration.ofHours(1L), new Money(BigDecimal.valueOf(3000L)), discountPolicy);

        Money money = discountPolicy.calculateDiscountAmount(new Screening(movie, 1, null));
        assertThat(money).isEqualTo(Money.ZERO);

        discountPolicy = new AmountDiscountPolicy(new Money(BigDecimal.valueOf(500L)), new PeriodCondition(DayOfWeek.MONDAY, LocalTime.MAX, LocalTime.MAX));
        movie = new Movie("movie", Duration.ofHours(1L), new Money(BigDecimal.valueOf(3000L)), discountPolicy);

        money = discountPolicy.calculateDiscountAmount(new Screening(movie, 0, LocalDateTime.of(2022, 4, 25, 0, 0)));
        assertThat(money).isEqualTo(Money.ZERO);
    }
}