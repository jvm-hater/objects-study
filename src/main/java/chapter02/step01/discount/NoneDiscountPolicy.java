package chapter02.step01.discount;

import chapter02.step01.Money;
import chapter02.step01.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
