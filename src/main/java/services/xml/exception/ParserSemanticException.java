package services.xml.exception;

/**
 * This type of exception is thrown when a semantic error happened while parsing a xml file. For example, when the length of a street
 * section is negative.
 */
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
