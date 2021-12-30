package chapter02.step02.pricing;

import chapter02.step02.DiscountPolicy;
import chapter02.step02.Money;
import chapter02.step02.Screening;

public class NoneDiscountPolicy implements DiscountPolicy {

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
