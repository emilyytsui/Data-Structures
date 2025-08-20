/**
 * This class is used to create an exception that's thrown
 * if the position is out of range or invalid.
 *
 * @author Emily Tsui
 */
public class InvalidPositionException extends Exception {
    /**
     * Constructs a new InvalidPositionException
     * with a default detail message.
     */
    public InvalidPositionException() {
        super("Position is not valid.");
    }

    /**
     * Constructs a new InvalidPositionException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public InvalidPositionException(String message) {
        super(message);
    }
}
