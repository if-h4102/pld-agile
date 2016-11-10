package services.xml.exception;

/**
 * This type of exception is thrown when the time constraints in a delivery address is invalid. For example, the time delivery end is before
 * the time delivery start.
 */
public class ParserTimeConstraintsException extends ParserSemanticException {

    private static final long serialVersionUID = -2004089292418467668L;

    public ParserTimeConstraintsException(String message) {
        super(message);
    }

    public ParserTimeConstraintsException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParserTimeConstraintsException(String message, Throwable cause) {
        super(message, cause);
    }
}
