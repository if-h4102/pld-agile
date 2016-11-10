package services.xml.exception;

/**
 * This type of exception is thrown when an attribute is missing in the xml file. For example, a street section without length.
 */
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
