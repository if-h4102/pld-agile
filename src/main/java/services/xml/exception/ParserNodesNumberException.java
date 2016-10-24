package services.xml.exception;

public class ParserNodesNumberException extends ParserSyntaxException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int minNodesNumberExpected;
    private int maxNodesNumberExpected;
    private int actualNodesNumber;
    private String nodesName;

    public ParserNodesNumberException(int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super();
        this.minNodesNumberExpected = minNodesNumberExpected;
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    public ParserNodesNumberException(Throwable e, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(e);
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
