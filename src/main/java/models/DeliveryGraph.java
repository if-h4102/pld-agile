package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DeliveryGraph {

    /**
     * The matrix representing the complete graph.
     */
    private Map<AbstractWayPoint, Map<AbstractWayPoint, Route>> routes;

    /**
<<<<<<< HEAD
     * Consruct a complete graph
     *
=======
     * Construct a complete graph
>>>>>>> tsp
     * @param routes
     */
    public DeliveryGraph(Map<AbstractWayPoint, Map<AbstractWayPoint, Route>> routes) {
        // TODO: compute matrix here ?
        this.routes = routes;
    }

    /**
     * Get the number of nodes in the current graph.
     * @return the total number of nodes.
     */
    public int size() {
        return this.routes.size();
    }

    /**
     * Get all IDs of the graph's nodes.
     * @return an array filled with the IDs of each graph's node.
     */
    public ArrayList<AbstractWayPoint> getNodes() {
        ArrayList<AbstractWayPoint> nodes = new ArrayList();
        this.routes.entrySet().forEach((entry) -> {
            nodes.add(entry.getKey());
        });
        return nodes;
    }

    /**
     *
     * @return the map of the delivery duration for each node.
     */
    public Map<AbstractWayPoint, Integer> getDeliveryDurations() {
        Map<AbstractWayPoint, Integer> deliveryDurations = new HashMap();
        this.routes.entrySet().forEach((entry) -> {
            int duration = 0;
            if(entry.getKey() instanceof DeliveryAddress) {
                duration = ((DeliveryAddress) entry.getKey()).getDeliveryDuration();
            }
            deliveryDurations.put(entry.getKey(), duration);
        });
        return deliveryDurations;
    }

    /**
     *
     * @return an iterator on the graph.
     */
    public Iterator<Map.Entry<AbstractWayPoint, Map<AbstractWayPoint, Route>>> iterator() {
        return this.routes.entrySet().iterator();
    }

    /**
     * TODO: description
     * @param start
     * @param end
     * @return
     */
    public Route getRoute(AbstractWayPoint start, AbstractWayPoint end) {
        // TODO: handle exceptions
        return routes.get(start).get(end);
    }
}
