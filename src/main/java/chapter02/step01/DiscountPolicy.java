package chapter02.step01;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscountPolicy {
    private List<DiscountCondition> conditons = new ArrayList<>();

    public DiscountPolicy(List<DiscountCondition> conditons) {
        this.conditons = conditons;
    }

    public Money calcuateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditons) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}
