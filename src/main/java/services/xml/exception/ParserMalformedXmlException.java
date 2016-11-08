package services.xml.exception;

public class ParserMalformedXmlException extends ParserSyntaxException {

    private static final long serialVersionUID = -3390574735801619072L;

    public ParserMalformedXmlException(String message) {
        super(message);
    }

    public ParserMalformedXmlException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParserMalformedXmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
