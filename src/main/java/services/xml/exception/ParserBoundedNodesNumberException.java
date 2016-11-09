package services.xml.exception;

/**
 * This type of exception is thrown when the number of nodes of one type is wrong and the expected number is bounded. For example, with
 * duplicate warehouse.
 */
public class ParserBoundedNodesNumberException extends ParserNodesNumberException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int maxNodesNumberExpected;
    private int minNodesNumberExpected;

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the exception constructed.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param maxNodesNumberExpected
     *            The maximum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserBoundedNodesNumberException(String message, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(message, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Construct a new instance of this class, using the message of the cause as message.
     * 
     * @param cause
     *            The exception which has cause this exception.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param maxNodesNumberExpected
     *            The maximum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserBoundedNodesNumberException(Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected, int actualNodesNumber,
            String nodesName) {
        super(cause, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the exception constructed.
     * @param cause
     *            The exception which has cause this exception.
     * @param minNodesNumberExpected
     *            The minimum number of nodes expected.
     * @param maxNodesNumberExpected
     *            The maximum number of nodes expected.
     * @param actualNodesNumber
     *            The actual number of nodes
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserBoundedNodesNumberException(String message, Throwable cause, int minNodesNumberExpected, int maxNodesNumberExpected,
            int actualNodesNumber, String nodesName) {
        super(message, cause, actualNodesNumber, nodesName);
        this.maxNodesNumberExpected = maxNodesNumberExpected;
        this.minNodesNumberExpected = minNodesNumberExpected;
    }

    /**
     * Returns the maximum number of nodes expected.
     * @return The maximum number of nodes expected.
     */
    public int getMaxNodesNumberExpected() {
        return maxNodesNumberExpected;
    }

    /**
     * Returns the minimum number of nodes expected.
     * @return The minimum number of nodes expected.
     */
    public int getMinNodesNumberExpected() {
        return minNodesNumberExpected;
    }
}
