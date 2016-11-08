package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DeliveryGraph {

    /**
     * The matrix representing the complete graph.
     */
    private final CityMap cityMap;
    private Map<AbstractWaypoint, Map<AbstractWaypoint, Route>> routes;

    /**
     * Construct a complete graph
     *
     * @param cityMap The map of the city containing the intersections of the way-points of this delivery graph.
     * @param routes  A matrix representing the complete graph
     */
    public DeliveryGraph(CityMap cityMap, Map<AbstractWaypoint, Map<AbstractWaypoint, Route>> routes) {
        this.cityMap = cityMap;
        // TODO: compute matrix here ?
        this.routes = routes;
    }

    /**
     * Get the number of nodes in the current graph.
     *
     * @return the total number of nodes.
     */
    public int size() {
        return this.routes.size();
    }

    /**
     * Get all IDs of the graph's nodes.
     *
     * @return an array filled with the IDs of each graph's node.
     */
    public ArrayList<AbstractWaypoint> getNodes() {
        ArrayList<AbstractWaypoint> nodes = new ArrayList<AbstractWaypoint>();
        this.routes.entrySet().forEach((entry) -> {
            nodes.add(entry.getKey());
        });
        return nodes;
    }

    /**
     * @return the map of the delivery duration for each node.
     */
    public Map<AbstractWaypoint, Integer> getDeliveryDurations() {
        Map<AbstractWaypoint, Integer> deliveryDurations = new HashMap<AbstractWaypoint, Integer>();
        this.routes.entrySet().forEach((entry) -> {
            deliveryDurations.put(entry.getKey(), entry.getKey().getDuration());
        });
        return deliveryDurations;
    }

    /**
     * @return an iterator on the graph.
     */
    public Iterator<Map.Entry<AbstractWaypoint, Map<AbstractWaypoint, Route>>> iterator() {
        return this.routes.entrySet().iterator();
    }

    /**
     * TODO: description
     *
     * @param start
     * @param end
     * @return
     */
    public Route getRoute(AbstractWaypoint start, AbstractWaypoint end) {
        try {
            return routes.get(start).get(end);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @return Map of the city containing the intersections of the way-points of this delivery graph.
     */
    public CityMap getCityMap() {
        return this.cityMap;
    }
}
