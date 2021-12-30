package chapter02.step02;

import chapter02.step02.pricing.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

class MovieTest {

    private final Money DiscountAmount = Money.wons(800);
    private final double DiscountPercent = 0.1;

    Movie givenAbatar() {
        return new Movie(
                "아바타",
                Duration.ofMinutes(120),
                Money.wons(10000),
                abatarCondition
        );
    }

    private final DiscountPolicy abatarCondition = new AmountDiscountPolicy(
            DiscountAmount,
            new SequenceCondition(1),
            new SequenceCondition(10),
            new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
    );

    Movie givenTitanic() {
        return new Movie(
                "타이타닉",
                Duration.ofMinutes(180),
                Money.wons(11000),
                titanicCondition
        );
    }

    private final DiscountPolicy titanicCondition = new PercentDiscountPolicy(
            0.1,
            new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
            new SequenceCondition(2),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))
    );

    Movie givenStarWars() {
        return new Movie(
                "스타워즈",
                Duration.ofMinutes(210),
                Money.wons(10000),
                starWarsCondition
        );
    }

    private final DiscountPolicy starWarsCondition = new NoneDiscountPolicy();
}
