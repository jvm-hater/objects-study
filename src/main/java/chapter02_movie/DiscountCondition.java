package chapter02_movie;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
