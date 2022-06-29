package chapter05_movie;

public class SequenceCondition implements DiscountCondition{

    private int sequence;

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return sequence == screening.getSequence();
    }
}
