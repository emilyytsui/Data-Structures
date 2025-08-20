/**
 * This class is used to create an exception that's thrown if
 * there is no more room in a GeneralLedger to record additional transactions.
 *
 * @author Emily Tsui
 */
public class FullGeneralLedgerException extends Exception {
    /**
     * Constructs a new FullGeneralLedgerException
     * with a default detail message.
     */
    public FullGeneralLedgerException() {
        super("General ledger is full: can't record additional transactions.");
    }

    /**
     * Constructs a new FullGeneralLedgerException with
     * the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public FullGeneralLedgerException(String message) {
        super(message);
    }
}
