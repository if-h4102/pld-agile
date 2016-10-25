package services.xml.exception;

public class ParserException extends Exception {

    private static final long serialVersionUID = 1893611693296088818L;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ParserException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
