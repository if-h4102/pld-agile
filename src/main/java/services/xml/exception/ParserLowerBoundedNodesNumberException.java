package services.xml.exception;

/**
 * This type of exception is thrown when the number of nodes of one type is wrong and the expected number is lower bounded. For example if a
 * city map contained no intersections, whereas it should contain at least 2.
 */
public class ParserLowerBoundedNodesNumberException extends ParserNodesNumberException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int minNodesNumberExpected;

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the constructed exception.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes.
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserLowerBoundedNodesNumberException(String message, int minNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super(message, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Construct a new instance of this class, using the message of this cause as message.
     * 
     * @param cause
     *            The cause of the exception.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes.
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserLowerBoundedNodesNumberException(Throwable cause, int minNodesNumberExpected, int actualNodesNumber, String nodesName) {
        super(cause, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the constructed exception.
     * @param cause
     *            The cause of the exception.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes.
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserLowerBoundedNodesNumberException(String message, Throwable cause, int minNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(message, cause, actualNodesNumber, nodesName);
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Returns the minimum number of nodes expected.
     * 
     * @return The minimum number of nodes expected.
     */
    public int getMinNodesNumberExpected() {
        return minNodesNumberExpected;
    }
}
