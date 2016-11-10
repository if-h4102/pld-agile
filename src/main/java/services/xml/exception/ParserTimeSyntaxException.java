package services.xml.exception;

/**
 * This type of exception is thrown when a time in the xml file has not the correct syntax. For example, the start planning time is "8:00",
 * whereas it should be at the syntax "hh:mm:ss".
 */
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
