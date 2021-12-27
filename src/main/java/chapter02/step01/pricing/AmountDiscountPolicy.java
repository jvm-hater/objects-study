package chapter02.step01.pricing;

import chapter02.step01.DiscountCondition;
import chapter02.step01.DiscountPolicy;
import chapter02.step01.Money;
import chapter02.step01.Screening;

import java.util.List;

public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
