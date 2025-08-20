/**
 * This class is used to create an exception that's thrown
 * if the expected node doesn't exist
 *
 * @author Emily Tsui
 */
public class NoSuchNodeException extends Exception {
    /**
     * Constructs a new NoSuchNodeException
     * with a default detail message.
     */
    public NoSuchNodeException() {
        super("The node does not exist.");
    }

    /**
     * Constructs a new NoSuchNodeException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public NoSuchNodeException(String message) {
        super(message);
    }
}
