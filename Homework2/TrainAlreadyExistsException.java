/**
 * This class is used to create an exception that's thrown
 * if there is already a Train object in this Track with
 * the same train number as newTrain.
 *
 * @author Emily Tsui
 */
public class TrainAlreadyExistsException extends Exception {
    /**
     * Constructs a new TrainAlreadyExistsException
     * with a default detail message.
     */
    public TrainAlreadyExistsException() {
        super("Train with this train number already exists.");
    }

    /**
     * Constructs a new TrainAlreadyExistsException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public TrainAlreadyExistsException(String message) {
        super(message);
    }
}
