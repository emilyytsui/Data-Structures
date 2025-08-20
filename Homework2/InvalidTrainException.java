/**
 * This class is used to create an exception that's thrown
 * if the train arrivalTime is invalid.
 *
 * @author Emily Tsui
 */
public class InvalidTrainException extends Exception {
    /**
     * Constructs a new InvalidTrainException
     * with a default detail message.
     */
    public InvalidTrainException() {
        super("Train arrival time is invalid.");
    }

    /**
     * Constructs a new InvalidTrainException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public InvalidTrainException(String message) {
        super(message);
    }
}
