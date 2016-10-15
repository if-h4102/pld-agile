package models;

import com.google.java.contract.Invariant;

@Invariant("routes.length == routes[0].length")
public class DeliveryGraph {

    /**
     * The matrix representing the complete graph.
     */

    private Route[][] routes;

    /**
     * Consruct a complete graph
     * @param routes
     */
    public DeliveryGraph(Route[][] routes) {
        // TODO: compute matrix here ?
        this.routes = routes;
    }

    public int size() {
        return this.routes.length;
    }

    /**
     * TODO: description
     * @param startId
     * @param endId
     * @return
     */
    public Route getRoute(int startId, int endId) {
        // TODO: handle exceptions
        return routes[startId][endId];
    }
}
