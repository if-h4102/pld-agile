package services.xml.exception;

import components.exceptionwindow.ExceptionWindow;

public abstract class ParserException extends Exception {

    private static final long serialVersionUID = 1893611693296088818L;

    public ParserException(String message) {
        super(message);
        ExceptionWindow exceptionWindow = new ExceptionWindow(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
        ExceptionWindow exceptionWindow = new ExceptionWindow(message);
    }
    
    public ParserException(Throwable cause) {
        super(cause.getMessage(), cause);
        ExceptionWindow exceptionWindow = new ExceptionWindow(cause.getMessage());
    }

}
