package services.xml.exception;

/**
 * This type of exception is thrown when two identical objects are in the xml file, whereas they should be unique. For example, two
 * identical intersection.
 */
public class ParserDuplicateObjectException extends ParserSemanticException {

    private static final long serialVersionUID = -1714806776195793116L;

    private Object duplicate;

    /**
     * Construct a new instance of this class.
     * @param message The message of the constructed exception.
     * @param duplicate The object which caused the exception.
     */
    public ParserDuplicateObjectException(String message, Object duplicate) {
        super(message);
        this.duplicate = duplicate;
    }

    /**
     * Construct a new instance of this class, using the message of the cause as message.
     * @param cause The cause of the exception.
     * @param duplicate The object which caused the exception.
     */
    public ParserDuplicateObjectException(Throwable cause, Object duplicate) {
        super(cause.getMessage(), cause);
        this.duplicate = duplicate;
    }

    /**
     * Construct a new instance of this class.
     * @param message The message of the constructed exception.
     * @param cause The cause of the exception.
     * @param duplicate The object which caused the exception.
     */
    public ParserDuplicateObjectException(String message, Throwable cause, Object duplicate) {
        super(message, cause);
        this.duplicate = duplicate;
    }

    /**
     * Returns the duplicate object.
     * @return The duplicate object.
     */
    public Object getDuplicate() {
        return duplicate;
    }
}
