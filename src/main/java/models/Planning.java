package models;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Planning {
    final private SimpleListProperty<Route> routes = new SimpleListProperty<>();

    public Planning(ObservableList<Route> routes) {
        this.routes.setValue(routes);
    }

    public Planning(List<Route> routes) {
        this(FXCollections.observableArrayList(routes));  // Copy the values in `routes` to an ObservableList
    }
    
    public SimpleListProperty<Route> getRoutes(){
    	return this.routes;
    }
}
