package chapter02.step02;

import java.time.LocalDateTime;

public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;
    private DiscountPolicy discountPolicy;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened, DiscountPolicy discountPolicy) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
        this.discountPolicy = discountPolicy;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Money getMovieFee() {
        return movie.getFee();
    }

    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}