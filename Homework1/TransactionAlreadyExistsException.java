/**
 * This class is used to create an exception that's thrown
 * if there is already a transaction in this GeneralLedger
 * which is equivalent to newTransaction.
 *
 * @author Emily Tsui
 */
public class TransactionAlreadyExistsException extends Exception {
    /**
     * Constructs a new TransactionAlreadyExistsException
     * a default detail message.
     */
    public TransactionAlreadyExistsException() {
        super("Transaction already exists in ledger.");
    }

    /**
     * Constructs a new TransactionAlreadyExistsException
     * with the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public TransactionAlreadyExistsException(String message) {
        super(message);
    }
}
