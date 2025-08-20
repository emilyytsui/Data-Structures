/**
 * This class is used to create an exception that's thrown
 * if the current node does not have any empty child nodes.
 *
 * @author Emily Tsui
 */
public class FullSceneException extends Exception {
    /**
     * Constructs a new FullSceneException
     * with a default detail message.
     */
    public FullSceneException() {
        super("Current node doesn't have any empty child nodes.");
    }

    /**
     * Constructs a new FullSceneException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public FullSceneException(String message) {
        super(message);
    }
}
