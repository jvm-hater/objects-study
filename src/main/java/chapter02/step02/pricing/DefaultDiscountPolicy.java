package chapter02.step02.pricing;

import chapter02.step02.DiscountCondition;
import chapter02.step02.DiscountPolicy;
import chapter02.step02.Money;
import chapter02.step02.Screening;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition ... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }
        
        return Money.ZERO;
    }

    protected abstract Money getDiscountAmount(Screening screening);
}
