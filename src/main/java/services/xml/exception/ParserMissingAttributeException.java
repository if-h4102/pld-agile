package services.xml.exception;

public class ParserMissingAttributeException extends ParserSemanticException {

    private static final long serialVersionUID = 7045499953529509920L;

    public ParserMissingAttributeException(String message) {
        super(message);
    }

    public ParserMissingAttributeException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParserMissingAttributeException(String message, Throwable cause) {
        super(message, cause);
    }
}
