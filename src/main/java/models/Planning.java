package models;

import java.util.ArrayList;
import java.util.List;

public class Planning {

    private List<Route> routes;

    public Planning(List<Route> routes) {
        this.routes = new ArrayList<Route>(routes);
    }
}
