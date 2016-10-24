package services.xml.exception;


public class ParserDuplicateObjectException extends ParserSemanticException {

    private static final long serialVersionUID = -1714806776195793116L;
    
    private Object duplicate;

    public ParserDuplicateObjectException(Object duplicate) {
        super();
        this.duplicate = duplicate;
    }
    
    public ParserDuplicateObjectException(Throwable e, Object duplicate) {
        super(e);
        this.duplicate = duplicate;
    }
    
    public Object getDuplicate() {
        return duplicate;
    }
}
