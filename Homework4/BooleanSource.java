/**
 * This class simulates a random event with a specified probability of
 * occurring, it has a method to check if the event occurs
 * based on the given probability.
 *
 * @author Emily Tsui
 */
public class BooleanSource {
    private double probability;

    /**
     * This is a Constructor used to create a new BooleanSource object.
     *
     * @param p The probability of the event occurring.
     */
    public BooleanSource(double p) {
        probability = p;
    }

    /**
     * Determines if the event occurs based on the given probability.
     *
     * @return
     * True if the event occurs (based on the probability), false otherwise.
     */
    public boolean occurs() {
        return (Math.random() < probability);
    }
}
