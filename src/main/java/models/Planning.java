package models;

import com.google.java.contract.Requires;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    /**
     * The circular list of waypoints + metadata of the current planning. Contains the warehouse twice.
     */
    final private ReadOnlyListWrapper<PlanningWaypoint> planningWaypoints = new ReadOnlyListWrapper<>(this, "planningWaypoints");

    /**
     * The map of waiting times for all waypoints.
     */
    final private Map<AbstractWaypoint, Integer> waypointWaitingTime;

    /**
     * The total amount of time need to complete all deliveries.
     */
    private int fullTime;

    /**
     * Construct a new Planning based on the given sorted list of waypoints.
     * @param waypoints a sorted list of waypoints.
     */
    public Planning(CityMap cityMap, Collection<AbstractWaypoint> waypoints, Map<AbstractWaypoint, Integer> waitingTimes, int fullTime) {
        this.cityMap.setValue(cityMap);
        this.waypoints.setValue(FXCollections.observableArrayList(waypoints));
        this.waypointWaitingTime = waitingTimes;
        this.fullTime = fullTime;
        this.updateRoutes();
        this.updatePlanningWaypoints();
    }

    /**
     * Get the city map property of the current planning.
     * @return the city map property (javafx).
     */
    public ReadOnlyObjectProperty<CityMap> cityMapProperty() {
        return this.cityMap.getReadOnlyProperty();
    }

    /**
     * Get the city map associated to the current planning.
     * @return the city map associated to the curent planning.
     */
    public CityMap getCityMap() {
        return this.cityMap.getValue();
    }

    /**
     * Get the total amount of time that the delivery man will need to complete all deliveries and loads.
     * @return the total amount of time needed to complete all deliveries and loads.
     */
    public int getFullTime() {
        return this.fullTime;
    }


    /**
     * Get the time that the delivery man must wait at the given waypoint
     * @param waypoint The waypoint where the delivery man will wait.
     * @return The waiting time of the delivery man
     */
    public int getWaitingTimeAtWaypoint(AbstractWaypoint waypoint) {
        if (this.waypointWaitingTime.containsKey(waypoint)) {
            // Avoid the nullPointerException if the waypoint is not in the map
            return this.waypointWaitingTime.get(waypoint);
        }
        return 0;
    }

    /**
     * Get all the waypoints of the current planning, wrapped for JavaFX.
     * @return the current sorted list of routes.
     */
    public ReadOnlyListProperty<AbstractWaypoint> waypointsProperty() {
        return this.waypoints.getReadOnlyProperty();
    }

    /**
     * Get all the waypoints of the current planning.
     * @return the current sorted list of routes.
     */
    public ObservableList<AbstractWaypoint> getWaypoints() {
        return this.waypointsProperty().getValue();
    }

    /**
     * Get all the routes of the current planning, wrapped for JavaFX.
     * @return the current sorted list of routes.
     */
    public ReadOnlyListProperty<Route> routesProperty() {
        return this.routes.getReadOnlyProperty();
    }

    /**
     * Get all the waypoints with metadata of the current planning
     * @return the current sorted list of routes.
     */
    public ReadOnlyListProperty<PlanningWaypoint> planningWaypointsProperty() {
        return this.planningWaypoints.getReadOnlyProperty();
    }

    /**
     * Get the observable list of waypoints used by the planning.
     * @return an observable list of waypoints used by the planning.
     */
    public ObservableList<PlanningWaypoint> getPlanningWaypoints() {
        return this.planningWaypointsProperty().getValue();
    }

    /**
     * Get all the routes of the current planning.
     * @return the current sorted list of routes.
     */
    public ObservableList<Route> getRoutes() {
        return this.routesProperty().getValue();
    }

    /**
     * Add a waypoint to the current planning at the best position possible,
     * and update the current routes consequently.
     * @param point the waypoint to add to the current planning.
     * @return the updated current planning.
     */
    @Deprecated
    public Planning addWaypoint(AbstractWaypoint point) {
        // Compute the best position to introduce the given waypoint
        int bestTime = Integer.MAX_VALUE;
        int bestPosition = 0;
        Route[] newRoutes = new Route[2];
        int index = 0;
        for (Route r : this.routes) {
            int time = this.getCityMap().shortestPath(r.getStartWaypoint(), Collections.singletonList(point)).get(0).getDuration()
                    + this.getCityMap().shortestPath(point, Collections.singletonList(r.getEndWaypoint())).get(0).getDuration();
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
    }

    /**
     * Add a waypoint to the current planning after the given waypoint, and update the current routes consequently.
     * @param waypoint The waypoint to add to the current planning.
     * @param index The index where to add the waypoint. The current waypoint at this index and the following waypoints will be moved by one.
     */
    @Requires({ "!this.waypoints.contains(waypoint)" })
    public void addWaypoint(AbstractWaypoint waypoint, int index) {
        this.waypoints.add(index, waypoint);
        this.updateRoutes();
        this.updatePlanningWaypoints();
    }

    /**
     * Add a waypoint to the current planning after the given waypoint, and update the current routes consequently.
     * @param waypoint the waypoint to add to the current planning.
     * @param afterWaypoint the waypoint after which the new waypoint must be added.
     */
    @Deprecated
    @Requires({ "!this.waypoints.contains(waypoint)", "this.waypoints.contains(afterWaypoint)" })
    public void addWaypoint(AbstractWaypoint waypoint, AbstractWaypoint afterWaypoint) {
        int index = this.waypoints.indexOf(afterWaypoint) + 1;
        this.addWaypoint(waypoint, index);
        this.updateRoutes();
        this.updatePlanningWaypoints();
    }

    /**
     * Remove a waypoint to the current planning, and update the current routes consequently.
     * Please not that the removed waypoint can't be the first warehouse.
     * @param waypoint The waypoint to remove from the current planning.
     */
    @Requires({ "this.waypoints.contains(waypoint)", "!waypoint.equals(this.waypoints.get(0))" })
    public void removeWaypoint(AbstractWaypoint waypoint) {
        int index = this.waypoints.indexOf(waypoint);
        this.waypoints.remove(index);
        this.routes.remove(index);
        this.routes.remove(index - 1);
        this.routes.add(index - 1, this.getCityMap().shortestPath(this.getWaypoint(index - 1), this.getWaypoint(index)));
        this.waypoints.remove(waypoint);
        this.updatePlanningWaypoints();
        // this.updateRoutes();
    }

    /**
     * Get the waypoint of the planning at the given index.
     * The waypoints list is circular, so giving an index greater than the size of the list
     * will still return a waypoint.
     * @param index the index at which the wanted waypoint is supposed to be.
     * @return the waypoint at the given index.
     */
    public AbstractWaypoint getWaypoint(int index) {
        return waypoints.get(index % this.waypoints.size());
    }

    /**
     * Get the warehouse with which the delivery man will start delivering things.
     * @return the warehouse associated to the planning.
     */
    public Warehouse getWarehouse() {
        return (Warehouse) this.getWaypoint(0);
    }

    /**
     * Compute all routes used by the current planning.
     * @return the list of all routes in the planning.
     */
    protected List<Route> computeRoutes() {
        List<Route> result = new LinkedList<>();
        for (int i = 0; i < this.waypoints.size(); i++) {
            AbstractWaypoint startWaypoint = this.getWaypoint(i);
            AbstractWaypoint endWaypoint = this.getWaypoint(i + 1);
            result.add(getCityMap().shortestPath(startWaypoint, endWaypoint));
        }
        return result;
    }

    /**
     * Update the list of all routes used by the planning.
     */
    protected void updateRoutes() {
        this.routes.setValue(FXCollections.observableArrayList(computeRoutes()));
    }

    /**
     * Transform all waypoints used by the planning into PlanningWayPoint objects.
     * @return the list off all used waypoints in the shape of PlanningWayPoints.
     */
    protected List<PlanningWaypoint> computePlanningWaypoints() {
        List<PlanningWaypoint> result = new ArrayList<>();

        Warehouse warehouse = this.getWarehouse();
        int time = warehouse.getTimeStart();

        result.add(new PlanningWaypoint(time, time, warehouse, time, time, true));

        for (Route route: this.routes) {
            AbstractWaypoint target = route.getEndWaypoint();
            int startTime = time;
            time += route.getDuration();

            if (time + target.getDuration() > target.getTimeEnd()) {
                result.add(new PlanningWaypoint(startTime, time, target, time, time, false));
            } else {
                int startWaitingTime = time;
                if (time < target.getTimeStart()) {
                    time = target.getTimeStart();
                }
                int startUnloadingTime = time;
                time += target.getDuration();
                int endUnloadingTime = time;
                result.add(new PlanningWaypoint(startTime, startWaitingTime, target, startUnloadingTime, endUnloadingTime, true));
            }
        }
        return result;
    }

    /**
     * Update all waypoints used by the planning.
     */
    protected void updatePlanningWaypoints() {
        this.updatePlanningWaypoints(0);
    }

    /**
     * Update all waypoints used by the planning, starting with the waypoint at the given index.
     * @param startIndex the index of the first waypoint to update.
     */
    protected void updatePlanningWaypoints(int startIndex) {
        this.planningWaypoints.setValue(FXCollections.observableArrayList(this.computePlanningWaypoints()));
    }

}
