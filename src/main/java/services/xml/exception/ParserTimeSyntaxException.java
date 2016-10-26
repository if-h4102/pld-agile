package services.xml.exception;

public class ParserTimeSyntaxException extends ParserSyntaxException {

    private static final long serialVersionUID = -8809869877938422103L;

    public ParserTimeSyntaxException(String message) {
        super(message);
    }

    public ParserTimeSyntaxException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParserTimeSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

}
