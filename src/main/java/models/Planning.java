package models;

import com.google.java.contract.Requires;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import java.util.stream.Collectors;

public class Planning {

    /**
     * The SORTED list of routes constituting the current planning.
     */
    final private SimpleListProperty<Route> routes = new SimpleListProperty<>();

    private Map<AbstractWayPoint, Integer> wayPointWaitingTime;
    private int fullTime;

    /**
     * Construct a new Planning based on the given sorted list of routes, transforming it to suit JavaFX needs.
     *
     * @param routes
     *            an sorted list of routes.
     */
    public Planning(List<Route> routes, Map<AbstractWayPoint, Integer> waitingTimes, int fullTime) {
        this(FXCollections.observableArrayList(routes), waitingTimes, fullTime); // Copy the values in `routes` to an ObservableList
    }

    /**
     * Construct a new Planning based on the given sorted list of routes.
     *
     * @param routes
     *            an sorted list of routes.
     */
    public Planning(ObservableList<Route> routes, Map<AbstractWayPoint, Integer> waitingTimes, int fullTime) {
        this.routes.setValue(routes);
        this.wayPointWaitingTime = waitingTimes; // TODO: clone ?
        this.fullTime = fullTime;
    }

    /**
     * Get the total amount of time that the delivery man will need to complete all deliveries and loads.
     *
     * @return the total amount of time needed to complete all deliveries and loads.
     */
    // The full time is now a parameter of planning, so the penalty are in it
    public int getFullTime() {
//        int fullTime = 0;
//        for (Route r : this.routes) {
//            fullTime += r.getDuration();
//            fullTime += r.getStartWaypoint().getDuration();
//        }
//        for (int waitingTime : wayPointWaitingTime.values()) {
//            fullTime += waitingTime;
//        }
        return fullTime;
    }

    /**
     * Get the time that the delivery man must wait at the given way point
     * 
     * @param wayPoint
     *            The wait point where the delivery man will wait
     * @return The waiting time of the delivery man
     */
    public int getWaitingTimeAtWayPoint(AbstractWayPoint wayPoint) {
        if (wayPointWaitingTime.containsKey(wayPoint)) // Avoid the nullPointerException if the wayPoint is not in the map
            return wayPointWaitingTime.get(wayPoint);
        return 0;
    }

    /**
     * Get all the routes of the current planning, wrapped for JavaFX.
     *
     * @return the current sorted list of routes.
     */
    public SimpleListProperty<Route> getRoutes() {
        return this.routes;
    }

    /**
     * Add a way point to the current planning, and update the current routes consequently.
     * 
     * @param point
     *            the way point to add to the current planning.
     * @param map
     *            the map with which the soon to be created new routes will be computed.
     * @return the updated current planning.
     */
    public Planning addWayPoint(AbstractWayPoint point, CityMap map) {
        // Compute the best position to introduce the given way point
        int bestTime = Integer.MAX_VALUE;
        int bestPosition = 0;
        Route[] newRoutes = new Route[2];
        int index = 0;
        for (Route r : this.routes) {
            int time = map.shortestPath(r.getStartWaypoint(), Collections.singletonList(point)).get(0).getDuration()
                    + map.shortestPath(point, Collections.singletonList(r.getEndWaypoint())).get(0).getDuration();
            // TODO: style guide ?
            if (time < bestTime) {
                bestTime = time;
                bestPosition = index;
                newRoutes[0] = map.shortestPath(r.getStartWaypoint(), Collections.singletonList(point)).get(0);
                newRoutes[1] = map.shortestPath(point, Collections.singletonList(r.getEndWaypoint())).get(0);
            }
        }
        // Remove the now unnecessary route
        this.routes.remove(bestPosition);
        // Introduce the way point
        this.routes.add(bestPosition, newRoutes[1]);
        this.routes.add(bestPosition, newRoutes[0]);

        return this;

        // TODO: refactor this with the following method
    }

    /**
     * Add a way point to the current planning after the given way point, and update the current routes consequently.
     * 
     * @param point
     *            the way point to add to the current planning.
     * @param afterPoint
     *            the way point after which the new way point must be added.
     * @param map
     *            the map with which the soon to be created new routes will be computed.
     * @return the updated current planning.
     */
    public Planning addWayPoint(AbstractWayPoint point, AbstractWayPoint afterPoint, CityMap map) {
        // Look for the position of the given afterPoint
        int position = 0;
        for (int i = 0; i < this.routes.size(); i++) {
            Route route = this.routes.get(i);
            if (route.getStartWaypoint().equals(afterPoint)) {
                position = i;
                break;
            }
        }
        // Split the affected route
        this.routes.remove(position);
        this.routes.add(position, map.shortestPath(this.routes.get(position).getStartWaypoint(), Collections.singletonList(point)).get(0));
        this.routes.add(position, map.shortestPath(point, Collections.singletonList(this.routes.get(position).getEndWaypoint())).get(0));
        return this;
    }

    /**
     * Remove a way point to the current planning, and update the current routes consequently. Please not that the removed way point can't
     * be the first warehouse.
     *
     * @param point
     *            the way point to remove from the current planning.
     * @param map
     *            the map with which the soon to be created new route will be computed.
     * @return the updated current planning.
     */
    @Requires("!(point.equals(this.getRoutes().iterator().next()))")
    public Planning removeWayPoint(AbstractWayPoint point, CityMap map) {
        // NOTE: the following algorithm works only if the current list is already sorted
        // Let's look for the position of the given way point
        AbstractWayPoint start = null;
        AbstractWayPoint end = null;
        int index = 0;
        int[] routesToRemove = new int[2];
        for (Route r : this.routes) {
            if (start != null) {
                // We just found the way point we were looking for in the previous iteration
                // As the routes list must be sorted, we know have the other point we want
                end = r.getEndWaypoint();
                routesToRemove[1] = index;
                // We have everything, no need to stay here anymore
                break;
            }
            if (r.getEndWaypoint().equals(point)) {
                // Ok, we just found the route ending with the given point
                // The next one is therefore starting with it, as the routes list must be sorted
                start = r.getStartWaypoint();
                routesToRemove[0] = index;
            }
            index++;
        }
        // Now we can compute the shortest path between the two previously found way point
        Route newRoute = map.shortestPath(start, Collections.singletonList(end)).get(0);
        // TODO: handle errors
        // Now we can remove the two old routes using the way point we want to remove
        this.routes.remove(routesToRemove[0]);
        this.routes.remove(routesToRemove[1]);
        // Finally we can add the previously created route
        this.routes.add(routesToRemove[0], newRoute);
        return this;
    }
}
