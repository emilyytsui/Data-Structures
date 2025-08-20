/**
 * This class is used to create an exception that's thrown if
 * there is no selected Train.
 *
 * @author Emily Tsui
 */
public class NoSelectedTrainException extends Exception {
    /**
     * Constructs a new NoSelectedTrainException
     * with a default detail message.
     */
    public NoSelectedTrainException() {
        super("There is no selected Train.");
    }

    /**
     * Constructs a new NoSelectedTrainException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public NoSelectedTrainException(String message) {
        super(message);
    }
}
