package chapter05_movie;

import java.time.Duration;
import java.util.List;

public class PercentDiscountMovie extends Movie{

    private double discountPercent;

    public PercentDiscountMovie(String title, Duration runningTime, Money fee,
        List<DiscountCondition> discountConditions, double discountPercent) {
        super(title, runningTime, fee, discountConditions);
        this.discountPercent = discountPercent;
    }

    @Override
    protected Money calculateDiscountAmount() {
        return getFee().times(discountPercent);
    }
}
