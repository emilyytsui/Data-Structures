/**
 * This class is used to create an exception that's thrown
 * if there is already a Track object in this Station with
 * the same track number as newTrack.
 *
 * @author Emily Tsui
 */
public class TrackAlreadyExistsException extends Exception {
    /**
     * Constructs a new TrackAlreadyExistsException
     * with a default detail message.
     */
    public TrackAlreadyExistsException() {
        super("Track with this track number already exists.");
    }

    /**
     * Constructs a new TrackAlreadyExistsException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public TrackAlreadyExistsException(String message) {
        super(message);
    }
}
