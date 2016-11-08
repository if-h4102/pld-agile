package services.xml.exception;

public class ParserLowerBoundedNodesNumberException extends ParserNodesNumberException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int minNodesNumberExpected;

    public ParserLowerBoundedNodesNumberException(String message, int minNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super(message, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public ParserLowerBoundedNodesNumberException(Throwable cause, int minNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super(cause, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public ParserLowerBoundedNodesNumberException(String message, Throwable cause, int minNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(message, cause, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public int getMinNodesNumberExpected() {
        return minNodesNumberExpected;
    }
}
