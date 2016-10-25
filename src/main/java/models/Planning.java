package models;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Planning {
    final private SimpleListProperty<Route> routes = new SimpleListProperty<>();

    private int fullTime;

    public Planning(ObservableList<Route> routes) {
        this.routes.setValue(routes);
        this.fullTime = 0;
        for(Route r: this.routes) {
            fullTime += r.getDuration();
            if(r.getStartWaypoint() instanceof DeliveryAddress) {
                fullTime += ((DeliveryAddress) r.getStartWaypoint()).getDuration();
            }
        }
    }

    public int getFullTime() {
        return this.fullTime;
    }

    public Planning(List<Route> routes) {
        this(FXCollections.observableArrayList(routes));  // Copy the values in `routes` to an ObservableList
    }

    public SimpleListProperty<Route> getRoutes(){
    	return this.routes;
    }
}
