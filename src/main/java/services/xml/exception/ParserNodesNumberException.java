package services.xml.exception;

/**
 * This type of exception is thrown when the number of nodes of one type is invalid. For example, no delivery address in a delivery request.
 */
public abstract class ParserNodesNumberException extends ParserSyntaxException {

    private static final long serialVersionUID = 1376406090684519267L;

    private int actualNodesNumber;
    private String nodesName;

    /**
     * Construct a new instance of this class.
     * 
     * @param message
     *            The message of the constructed exception.
     * @param actualNodesNumber
     *            The actual number of nodes.
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
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

    /**
     * @param message
     *            The message of the constructed exception.
     * @param cause
     *            The cause of the exception.
     * @param actualNodesNumber
     *            The actual number of nodes.
     * @param nodesName
     *            The name of the nodes in the xml document.
     */
    public ParserNodesNumberException(String message, Throwable cause, int actualNodesNumber, String nodesName) {
        super(message, cause);
        this.actualNodesNumber = actualNodesNumber;
        this.nodesName = nodesName;
    }

    /**
     * Returns the actual number of nodes.
     * @return The actual number of nodes.
     */
    public int getActualNodesNumber() {
        return actualNodesNumber;
    }

    /**
     * Returns the name of the nodes in the xml file.
     * @return The name of the nodes in the xml file.
     */
    public String getNodesName() {
        return nodesName;
    }
}
