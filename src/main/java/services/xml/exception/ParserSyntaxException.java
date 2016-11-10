package services.xml.exception;

/**
 * This type of exception is thrown when a syntax error happened while parsing a xml file. For example, two warehouses in a delivery
 * request.
 */
public abstract class ParserSyntaxException extends ParserException {

    private static final long serialVersionUID = -8031780060238395069L;

    public ParserSyntaxException(String message) {
        super(message);
    }

    public ParserSyntaxException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParserSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
