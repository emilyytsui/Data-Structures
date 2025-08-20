/**
 * This class is used to create an exception that's thrown
 * if the position is not within the valid range.
 *
 * @author Emily Tsui
 */
public class InvalidLedgerPositionException extends Exception {
    /**
     * Constructs a new InvalidLedgerPositionException
     * with a default detail message.
     */
    public InvalidLedgerPositionException(){
        super("Position is not valid: must be greater than equal to 1 " +
                "and less than equal to items currently in list");
    }

    /**
     * Constructs a new InvalidLedgerPositionException
     * with the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public InvalidLedgerPositionException(String message) {
        super(message);
    }
}
