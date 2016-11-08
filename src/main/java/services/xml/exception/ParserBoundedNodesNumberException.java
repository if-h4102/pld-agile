package services.xml.exception;

public class ParserBoundedNodesNumberException extends ParserNodesNumberException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int maxNodesNumberExpected;
    private int minNodesNumberExpected;

    public ParserBoundedNodesNumberException(String message, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(message, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public ParserBoundedNodesNumberException(Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(cause, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public ParserBoundedNodesNumberException(String message, Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected,
            int actualNodesNumber, String nodesName) {
        super(message, cause, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    public int getMaxNodesNumberExpected() {
        return maxNodesNumberExpected;
    }

    public int getMinNodesNumberExpected() {
        return minNodesNumberExpected;
    }
}
