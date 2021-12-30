package chapter02.step02.pricing;

import chapter02.step02.DiscountCondition;
import chapter02.step02.DiscountPolicy;
import chapter02.step02.Money;
import chapter02.step02.Screening;

public class AmountDiscountPolicy extends DefaultDiscountPolicy {
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
