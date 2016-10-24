package services.xml.exception;

public class ParserIntegerValueException extends ParserSemanticException {

    private static final long serialVersionUID = -8356884840998191646L;
    
    private int value;

    public ParserIntegerValueException(int value) {
        super();
        this.value = value;
    }

    public ParserIntegerValueException(Throwable e, int value) {
        super(e);
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

}
