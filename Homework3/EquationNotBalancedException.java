/**
 * This class is used to create an exception that's thrown
 * if the equation is not balanced (properly matched parentheses) or if the
 * equation can't be evaluated.
 *
 * @author Emily Tsui
 */
public class EquationNotBalancedException extends Exception {
    /**
     * Constructs a new EquationNotBalancedException
     * with a default detail message.
     */
    public EquationNotBalancedException() {
        super("Equation is not balanced.");
    }

    /**
     * Constructs a new EquationNotBalancedException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public EquationNotBalancedException(String message) {
        super(message);
    }
}
