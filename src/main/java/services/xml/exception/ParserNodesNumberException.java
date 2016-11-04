package services.xml.exception;

public abstract class ParserNodesNumberException extends ParserSyntaxException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int actualNodesNumber;
    private String nodesName;

    public ParserNodesNumberException(String message, int actualNodesNumber, String nodesName) {
        super(message);
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public ParserNodesNumberException(Throwable cause, int actualNodesNumber, String nodesName) {
        super(cause.getMessage(), cause);
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public ParserNodesNumberException(String message, Throwable cause, int actualNodesNumber, String nodesName) {
        super(message, cause);
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public int getActualNodesNumber() {
        return actualNodesNumber;
    }

    public String getNodesName() {
        return nodesName;
    }
}
