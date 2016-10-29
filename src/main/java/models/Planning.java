package models;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planning {
    /**
     * The ORDERED list of routes constituting the current planning.
     */
    final private SimpleListProperty<Route> routes = new SimpleListProperty<>();

    /**
     * Construct a new Planning based on the given ordered list of routes,
     * transforming it to suit JavaFX needs.
     * @param routes an ordered list of routes.
     */
    public Planning(List<Route> routes) {
        this(FXCollections.observableArrayList(routes));  // Copy the values in `routes` to an ObservableList
    }

    /**
     * Construct a new Planning based on the given ordered list of routes.
     * @param routes an ordered list of routes.
     */
    public Planning(ObservableList<Route> routes) {
        this.routes.setValue(routes);
    }

    /**
     * Get the total amount of time that the delivery man will need
     * to complete all deliveries and loads.
     * @return the total amount of time needed to complete all deliveries and loads.
     */
    public int getFullTime() {
        int fullTime = 0;
        for(Route r: this.routes) {
            fullTime += r.getDuration();
            fullTime += r.getStartWaypoint().getDuration();
        }
        return fullTime;
    }

    /**
     * Get all the routes of the current planning,
     * wrapped for JavaFX.
     * @return the current ordered list of routes.
     */
    public SimpleListProperty<Route> getRoutes(){
        return this.routes;
    }

    /**
     * Add a way point to the current planning,
     * and update the current routes consequently.
     * @param point the way point to add to the current planning.
     * @param map the map with which the soon to be created new routes
     *            will be computed.
     * @return the updated current planning.
     */
    public Planning addWayPoint(AbstractWayPoint point, CityMap map) {
        // TODO
        return this;
    }

    /**
     * Remove a way point to the current planning,
     * and update the current routes consequently.
     * @param point the way point to remove from the current planning.
     * @param map the map with which the soon to be created new route
     *            will be computed.
     * @return the updated current planning.
     */
    public Planning removeWayPoint(AbstractWayPoint point, CityMap map) {
        // TODO
        return this;
    }
}
