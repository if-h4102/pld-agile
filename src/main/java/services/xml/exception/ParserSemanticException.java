package services.xml.exception;

public abstract class ParserSemanticException extends ParserException {

    private static final long serialVersionUID = 7238471110650199606L;

    public ParserSemanticException(String message) {
        super(message);
    }

    public ParserSemanticException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
    
    public ParserSemanticException(String message, Throwable cause) {
        super(message, cause);
    }
}
