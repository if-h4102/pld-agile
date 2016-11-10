package services.xml.exception;

/**
 * This type of exception is thrown when an attribute in the xml file should be an integer, but is another value. For example, if the 
 * address of a delivery address is "4ds".
 */
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
