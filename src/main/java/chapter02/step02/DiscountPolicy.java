package chapter02.step02;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
