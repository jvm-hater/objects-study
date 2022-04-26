package chapter02.step01.discount;

import chapter02.step01.Screening;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DiscountCondition 테스트")
class DiscountConditionTest {

    @Test
    @DisplayName("상영 순번이 같으면 순번 할인 조건에 만족한다.")
    void isSatisfiedBy_sequence() {
        Screening screening = new Screening(null, 3, null);
        DiscountCondition discountCondition = new SequenceCondition(3);
        assertThat(discountCondition.isSatisfiedBy(screening)).isTrue();

        discountCondition = new SequenceCondition(4);
        assertThat(discountCondition.isSatisfiedBy(screening)).isFalse();
    }

    @Test
    @DisplayName("상영 기간이 포함되면 기간 할인 조건에 만족한다.")
    void isSatisfiedBy_period() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 4, 25, 12, 42);
        Screening screening = new Screening(null, 0, localDateTime);
        DiscountCondition discountCondition = new PeriodCondition(DayOfWeek.MONDAY, LocalTime.MIN, LocalTime.MAX);
        assertThat(discountCondition.isSatisfiedBy(screening)).isTrue();

        discountCondition = new PeriodCondition(DayOfWeek.SATURDAY, LocalTime.MIN, LocalTime.MAX);
        assertThat(discountCondition.isSatisfiedBy(screening)).isFalse();
    }
}