package services.xml.exception;

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
