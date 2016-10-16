package models;

import com.google.java.contract.Invariant;

import java.util.Map;

@Invariant("routes.length == routes[0].length")
public class DeliveryGraph {

    /**
     * The matrix representing the complete graph.
     */
    private Map<Integer, Map<Integer, Route>> routes;

    /**
     * Consruct a complete graph
     * @param routes
     */
    public DeliveryGraph(Map<Integer, Map<Integer, Route>> routes) {
        // TODO: compute matrix here ?
        this.routes = routes;
    }

    public int size() {
        return this.routes.size();
    }

    /**
     * TODO: description
     * @param startId
     * @param endId
     * @return
     */
    public Route getRoute(int startId, int endId) {
        // TODO: handle exceptions
        return routes.get(startId).get(endId);
    }
}
