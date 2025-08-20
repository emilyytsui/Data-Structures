/**
 * This class is used to create an exception that's thrown
 * if the no patient with name does not exist, so it can't be removed from
 * the recipients or donors list.
 *
 * @author Emily Tsui
 */
public class PatientDoesNotExistException extends Exception {
    /**
     * Constructs a new PatientDoesNotExistException
     * a default detail message.
     */
    public PatientDoesNotExistException() {
        super("Patient does not exist.");
    }

    /**
     * Constructs a new PatientDoesNotExistException
     * with the specified detail message.
     *
     * @param message
     * The detail message that describes the cause of the exception.
     */
    public PatientDoesNotExistException(String message) {
        super(message);
    }
}
