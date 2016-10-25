package services.xml.exception;


public class ParserDuplicateObjectException extends ParserSemanticException {

    private static final long serialVersionUID = -1714806776195793116L;
    
    private Object duplicate;

    public ParserDuplicateObjectException(String message, Object duplicate) {
        super(message);
        this.duplicate = duplicate;
    }
    
    public ParserDuplicateObjectException(Throwable cause, Object duplicate) {
        super(cause.getMessage(), cause);
        this.duplicate = duplicate;
    }
    
    public ParserDuplicateObjectException(String message, Throwable cause, Object duplicate) {
        super(message, cause);
        this.duplicate = duplicate;
    }
    
    public Object getDuplicate() {
        return duplicate;
    }
}
