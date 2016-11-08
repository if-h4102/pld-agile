package services.xml.exception;

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
