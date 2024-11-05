package pedernal.github.dicemode.utilities;

@FunctionalInterface
public interface TriPredicate <A, B, C> {
    public boolean test(A a, B b, C c);
}
