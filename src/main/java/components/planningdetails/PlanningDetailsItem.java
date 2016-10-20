package components.planningdetails;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.CityMap;
import models.Route;

import java.io.IOException;

class PlanningDetailsItem extends AnchorPane {
    private static final Route DEFAULT_ROUTE = null;
    @FXML
    protected Label content;

    private SimpleObjectProperty<Route> route;

    PlanningDetailsItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetailsItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        routeProperty().addListener(event -> content.setText(
            "Duration: " + getRoute().getDuration() + ", Street Sections: " + getRoute().getStreetSections().size()
            + ", Start: (" + getRoute().getStartWaypoint().getIntersection().getX()
            + ", " + getRoute().getStartWaypoint().getIntersection().getY()
            + "), End: (" + getRoute().getEndWaypoint().getIntersection().getX()
            + ", " + getRoute().getEndWaypoint().getIntersection().getY()
            + ")"
        ));
    }

    public final SimpleObjectProperty<Route> routeProperty() {
        if (route == null) {
            route = new SimpleObjectProperty<>(this, "route", DEFAULT_ROUTE);
        }
        return route;
    }

    public final void setRoute(Route value) {
        routeProperty().setValue(value);
    }

    public final Route getRoute() {
        return route == null ? DEFAULT_ROUTE : routeProperty().getValue();
    }
}
