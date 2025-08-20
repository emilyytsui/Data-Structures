/**
 * This class is used to create an exception that's thrown
 * if there is already an item with this item code in HashedGrocery.
 *
 * @author Emily Tsui
 */
public class ItemCodeAlreadyExistsException extends Exception {
    /**
     * Constructs a new ItemCodeAlreadyExistsException
     * a default detail message.
     */
    public ItemCodeAlreadyExistsException() {
        super("Item code");
    }

    /**
     * Constructs a new ItemCodeAlreadyExistsException
     * with the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public ItemCodeAlreadyExistsException(String message) {
        super(message);
    }
}
