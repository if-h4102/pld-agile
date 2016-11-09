package services.xml.exception;

/**
 * This type of exception is thrown when an integer value is out of its bounds. For example, a length which is negative.
 */
public class ParserIntegerValueException extends ParserSemanticException {

    private static final long serialVersionUID = -8356884840998191646L;

    private int value;

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the exception constructed.
     * @param value
     *            The value of the integer which caused the exception.
     */
    public ParserIntegerValueException(String message, int value) {
        super(message);
        this.value = value;
    }

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the exception constructed.
     * @param cause
     *            The exception which caused the exception constructed.
     * @param value
     *            The value of the integer which caused the exception.
     */
    public ParserIntegerValueException(String message, Throwable cause, int value) {
        super(message, cause);
        this.value = value;
    }

    /**
     * Construct a new instance of this class, using the message of the cause
     * 
     * @param cause
     *            The exception which caused the exception constructed.
     * @param value
     *            The value of the integer which caused the exception.
     */
    public ParserIntegerValueException(Throwable cause, int value) {
        super(cause.getMessage(), cause);
        this.value = value;
    }

    /**
     * Returns the value which caused the exception.
     * 
     * @return The value which caused the exception.
     */
    public int getValue() {
        return value;
    }

}
