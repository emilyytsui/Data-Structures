/**
 * This class is used to create an exception that's thrown if
 * the user tries to input an arrival and departure time that conflicts with
 * an already scheduled train,
 *
 * @author Emily Tsui
 */
public class TrainTimeConflictException extends Exception {
    /**
     * Constructs a new TrainTimeConflictException
     * with a default detail message.
     */
    public TrainTimeConflictException() {
        super("Train time conflicts with another train in the track.");
    }

    /**
     * Constructs a new TrainTimeConflictException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public TrainTimeConflictException(String message) {
        super(message);
    }
}
