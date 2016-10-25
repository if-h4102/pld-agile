package services.xml.exception;

public class ParserNodesNumberException extends ParserSyntaxException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int minNodesNumberExpected;
    private int maxNodesNumberExpected;
    private int actualNodesNumber;
    private String nodesName;

    public ParserNodesNumberException(String message, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super(message);
        this.minNodesNumberExpected = minNodesNumberExpected;
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public ParserNodesNumberException(Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(cause.getMessage(), cause);
        this.minNodesNumberExpected = minNodesNumberExpected;
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }
    
    public ParserNodesNumberException(String message, Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(message, cause);
        this.minNodesNumberExpected = minNodesNumberExpected;
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public int getMinNodesNumberExpected() {
        return minNodesNumberExpected;
    }

    public int getMaxNodesNumberExpected() {
        return maxNodesNumberExpected;
    }

    public int getActualNodesNumber() {
        return actualNodesNumber;
    }

    public String getNodesName() {
        return nodesName;
    }
}
