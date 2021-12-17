package chapter02.step01.pricing;

import chapter02.step01.DiscountCondition;
import chapter02.step01.DiscountPolicy;
import chapter02.step01.Money;
import chapter02.step01.Screening;

import java.util.List;

public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(List<DiscountCondition> conditons, double percent) {
        super(conditons);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
