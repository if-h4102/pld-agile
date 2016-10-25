package services.xml.exception;


public class ParserShouldBeIntegerValueException extends ParserSemanticException {
    private static final long serialVersionUID = -8356884840998191646L;
    
    public ParserShouldBeIntegerValueException(String message) {
        super(message);
    }

    public ParserShouldBeIntegerValueException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
    
    public ParserShouldBeIntegerValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
