package services.xml.exception;


public class ParserShouldBeIntegerValueException extends ParserSemanticException {
    private static final long serialVersionUID = -8356884840998191646L;
    
    public ParserShouldBeIntegerValueException() {
        super();
    }

    public ParserShouldBeIntegerValueException(Throwable e) {
        super(e);
    }
}
