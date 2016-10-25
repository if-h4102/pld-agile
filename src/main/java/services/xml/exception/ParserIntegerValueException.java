package services.xml.exception;

public class ParserIntegerValueException extends ParserSemanticException {

    private static final long serialVersionUID = -8356884840998191646L;
    
    private int value;

    public ParserIntegerValueException(String message, int value) {
        super(message);
        this.value = value;
    }

    public ParserIntegerValueException(String message, Throwable cause, int value) {
        super(message, cause);
        this.value = value;
    }
    
    public ParserIntegerValueException(Throwable cause, int value) {
        super(cause.getMessage(), cause);
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

}
