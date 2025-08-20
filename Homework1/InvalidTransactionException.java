/**
 * This class is used to create an exception that's thrown
 * if the transaction amount is 0 or if the date is invalid.
 *
 * @author Emily Tsui
 */
public class InvalidTransactionException extends Exception {
    /**
     * Constructs a new InvalidTransactionException
     * with a default detail message.
     */
    public InvalidTransactionException() {
        super("Transaction is invalid: " +
                "amount can't be 0 and date must be valid.");
    }

    /**
     * Constructs a new InvalidTransactionException
     * with the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
