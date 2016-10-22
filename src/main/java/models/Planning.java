package models;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planning {
    final private SimpleListProperty<Route> routes = new SimpleListProperty<>();

    private int fullTime;

    public Planning(ObservableList<Route> routes) {
        this.routes.setValue(routes);
        this.fullTime = 0;
        for(Route r: this.routes) {
            fullTime += r.getDuration();
            if(r.getStartWaypoint() instanceof DeliveryAddress) {
                fullTime += ((DeliveryAddress) r.getStartWaypoint()).getDeliveryDuration();
            }
        }
    }

    public int getFullTime() {
        return this.fullTime;
    }

    public Planning(List<Route> routes) {
        this(FXCollections.observableArrayList(routes));  // Copy the values in `routes` to an ObservableList
    }

    public Planning addWayPoint(AbstractWayPoint point) {
        // TODO
        return this;
    }

    public Planning removeWayPoint(AbstractWayPoint point) {
        // TODO
        return this;
    }

    public SimpleListProperty<Route> getRoutes(){
    	return this.routes;
    }
}
