package models;

import com.google.java.contract.Requires;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

import java.util.*;

public class Planning {
    /**
     * The city-map containing the intersections of the waypoints used by the planning.
     */
    final private ReadOnlyObjectWrapper<CityMap> cityMap = new ReadOnlyObjectWrapper<>(this, "cityMap");

    /**
     * The circular list of waypoints of the current planning.
     */
    final private ReadOnlyListWrapper<AbstractWaypoint> waypoints = new ReadOnlyListWrapper<>(this, "waypoints");

    /**
     * The circular list of routes of the current planning.
     */
    final private ReadOnlyListWrapper<Route> routes = new ReadOnlyListWrapper<>(this, "routes");

    final private Map<AbstractWaypoint, Integer> waypointWaitingTime;

    private int fullTime;

    /**
     * Construct a new Planning based on the given sorted list of waypoints.
     *
     * @param waypoints a sorted list of waypoints.
     */
    public Planning(CityMap cityMap, Collection<AbstractWaypoint> waypoints, Map<AbstractWaypoint, Integer> waitingTimes, int fullTime) {
        this.cityMap.setValue(cityMap);
        this.waypoints.setValue(FXCollections.observableArrayList(waypoints));
        this.waypointWaitingTime = waitingTimes; // TODO: clone ?
        this.fullTime = fullTime;
        this.updateRoutes();
    }

    public ReadOnlyObjectProperty<CityMap> cityMapProperty() {
        return this.cityMap.getReadOnlyProperty();
    }

    public CityMap getCityMap() {
        return this.cityMap.getValue();
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
//        for (int waitingTime : waypointWaitingTime.values()) {
//            fullTime += waitingTime;
//        }
        return fullTime;
    }

    /**
     * Get the time that the delivery man must wait at the given waypoint
     *
     * @param waypoint The wait point where the delivery man will wait
     * @return The waiting time of the delivery man
     */
    public int getWaitingTimeAtWaypoint(AbstractWaypoint waypoint) {
        if (waypointWaitingTime.containsKey(waypoint)) // Avoid the nullPointerException if the waypoint is not in the map
            return waypointWaitingTime.get(waypoint);
        return 0;
    }

    /**
     * Get all the waypoints of the current planning, wrapped for JavaFX.
     *
     * @return the current sorted list of routes.
     */
    public ReadOnlyListProperty<AbstractWaypoint> waypointsProperty() {
        return this.waypoints.getReadOnlyProperty();
    }

    /**
     * Get all the waypoints of the current planning.
     *
     * @return the current sorted list of routes.
     */
    public ObservableList<AbstractWaypoint> getWaypoints() {
        return this.waypointsProperty().getValue();
    }

    /**
     * Get all the routes of the current planning, wrapped for JavaFX.
     *
     * @return the current sorted list of routes.
     */
    public ReadOnlyListProperty<Route> routesProperty() {
        return this.routes.getReadOnlyProperty();
    }

    /**
     * Get all the routes of the current planning.
     *
     * @return the current sorted list of routes.
     */
    public ObservableList<Route> getRoutes() {
        return this.routesProperty().getValue();
    }

    /**
     * Add a waypoint to the current planning,
     * and update the current routes consequently.
     *
     * @param point the waypoint to add to the current planning.
     * @return the updated current planning.
     */
    public Planning addWaypoint(AbstractWaypoint point) {
        // Compute the best position to introduce the given waypoint
        int bestTime = Integer.MAX_VALUE;
        int bestPosition = 0;
        Route[] newRoutes = new Route[2];
        int index = 0;
        for (Route r : this.routes) {
            int time = this.getCityMap().shortestPath(r.getStartWaypoint(), Collections.singletonList(point)).get(0).getDuration()
                + this.getCityMap().shortestPath(point, Collections.singletonList(r.getEndWaypoint())).get(0).getDuration();
            // TODO: style guide ?
            if (time < bestTime) {
                bestTime = time;
                bestPosition = index;
                newRoutes[0] = this.getCityMap().shortestPath(r.getStartWaypoint(), Collections.singletonList(point)).get(0);
                newRoutes[1] = this.getCityMap().shortestPath(point, Collections.singletonList(r.getEndWaypoint())).get(0);
            }
        }
        // Remove the now unnecessary route
        this.routes.remove(bestPosition);
        // Introduce the waypoint
        this.routes.add(bestPosition, newRoutes[1]);
        this.routes.add(bestPosition, newRoutes[0]);

        return this;

        // TODO: refactor this with the following method
    }

    /**
     * Add a waypoint to the current planning after the given waypoint,
     * and update the current routes consequently.
     *
     * @param waypoint The waypoint to add to the current planning.
     * @param index    The index where to add the waypoint. The current waypoint
     *                 at this index and the following waypoints will be moved
     *                 by one.
     * @return the updated current planning.
     */
    @Requires({"!this.waypoints.contains(waypoint)"})
    public void addWaypoint(AbstractWaypoint waypoint, int index) {
        this.waypoints.add(index, waypoint);
        this.updateRoutes();
    }

    /**
     * Add a waypoint to the current planning after the given waypoint,
     * and update the current routes consequently.
     *
     * @param waypoint      the waypoint to add to the current planning.
     * @param afterWaypoint the waypoint after which the new waypoint must be added.
     * @return the updated current planning.
     */
    @Requires({"!this.waypoints.contains(waypoint)", "this.waypoints.contains(afterWaypoint)"})
    public void addWaypoint(AbstractWaypoint waypoint, AbstractWaypoint afterWaypoint) {
        int index = this.waypoints.indexOf(afterWaypoint) + 1;
        this.addWaypoint(waypoint, index);
        this.updateRoutes();
    }

    /**
     * Remove a waypoint to the current planning, and update the current routes consequently. Please not that the removed waypoint can't
     * be the first warehouse.
     *
     * @param waypoint The waypoint to remove from the current planning.
     */
    @Requires({"this.waypoints.contains(waypoint)", "!waypoint.equals(this.waypoints.get(0))"})
    public void removeWaypoint(AbstractWaypoint waypoint) {
        int index = this.waypoints.indexOf(waypoint);
        this.waypoints.remove(index);
        this.routes.remove(index);
        this.routes.remove(index - 1);
        this.routes.add(index - 1, this.getCityMap().shortestPath(this.getWaypoint(index-1), this.getWaypoint(index)));
        this.waypoints.remove(waypoint);
        // this.updateRoutes();
    }

    public AbstractWaypoint getWaypoint(int index) {
        int size = this.waypoints.size();  // TODO: require(size > 0)
        return this.waypoints.get(index % size);
    }

    protected List<Route> computeRoutes() {
        List<Route> result = new LinkedList<>();
        for (int i = 0; i < this.waypoints.size(); i++) {
            AbstractWaypoint startWaypoint = this.getWaypoint(i);
            AbstractWaypoint endWaypoint = this.getWaypoint(i + 1);
            result.add(this.getCityMap().shortestPath(startWaypoint, endWaypoint));
        }
        return result;
    }

    protected void updateRoutes() {
        this.routes.setValue(FXCollections.observableArrayList(this.computeRoutes()));
    }

    public Warehouse getWarehouse(){
        return (Warehouse) waypoints.get(0);
    }
}
