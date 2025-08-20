/**
 * This class is used to create an exception that's thrown
 * if there is no last undone Equation.
 *
 * @author Emily Tsui
 */
public class NoLastUndoneEquationException extends Exception {
    /**
     * Constructs a new NoLastUndoneEquationException
     * with a default detail message.
     */
    public NoLastUndoneEquationException() {
        super("There is no last undone Equation");
    }

    /**
     * Constructs a new NoLastUndoneEquationException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public NoLastUndoneEquationException(String message) {
        super(message);
    }
}
