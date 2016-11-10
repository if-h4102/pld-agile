package services.xml.exception;

/**
 * This class described a generic exception thrown during the parsing of xml files.
 */
public abstract class ParserException extends Exception {

    private static final long serialVersionUID = 1893611693296088818L;

    /**
     * Construct a new instance of this class.
     * @param message The message of the constructed exception.
     */
    public ParserException(String message) {
        super(message);
    }

    /**
     * Construct a new instance of this class.
     * @param message The message of the constructed exception.
     * @param cause The cause of the constructed exception.
     */
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Construct a new instance of this class, with the message of the cause as message.
     * @param cause The cause of the constructed exception.
     */
    public ParserException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
