package components.planningdetails;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.AbstractWayPoint;
import models.Intersection;
import models.Route;

import java.io.IOException;

public class PlanningDetailsItem<E extends Route> extends AnchorPane {
    @FXML
    protected Label content;

    private SimpleObjectProperty<E> item;
    private SimpleIntegerProperty index;
    private SimpleStringProperty waypointName;
    private SimpleStringProperty coordinates;

    public PlanningDetailsItem() {
        updateWaypointName();
        updateCoordinates();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetailsItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        indexProperty().addListener(event -> updateWaypointName());
        itemProperty().addListener(event -> updateCoordinates());
    }

    // Item
    public final SimpleObjectProperty<E> itemProperty() {
        if (item == null) {
            item = new SimpleObjectProperty<>(this, "item", null);
        }
        return item;
    }

    public final void setItem(E value) {
        itemProperty().setValue(value);
    }

    public final E getItem() {
        return item == null ? null : itemProperty().getValue();
    }

    // Index
    public final SimpleIntegerProperty indexProperty() {
        if (index == null) {
            index = new SimpleIntegerProperty(this, "index");
        }
        return index;
    }

    public final void setIndex(int value) {
        indexProperty().setValue(value);
    }

    public final int getIndex() {
        return indexProperty().getValue();
    }

    // WaypointName
    public final SimpleStringProperty waypointNameProperty() {
        if (waypointName == null) {
            waypointName = new SimpleStringProperty(this, "waypointName");
        }
        return waypointName;
    }

    public final void setWaypointName(String value) {
        waypointNameProperty().setValue(value);
    }

    public final String getWaypointName() {
        return waypointNameProperty().getValue();
    }

    // Coordinates
    public final SimpleStringProperty coordinatesProperty() {
        if (coordinates == null) {
            coordinates = new SimpleStringProperty(this, "coordinates");
        }
        return coordinates;
    }

    public final void setCoordinates(String value) {
        coordinatesProperty().setValue(value);
    }

    public void updateCoordinates() {
        final Route item = getItem();
        if (item == null) {
            return;
        }
        final AbstractWayPoint waypoint = item.getStartWaypoint();
        if (waypoint == null) {
            return;
        }
        final Intersection intersection = waypoint.getIntersection();
        if (intersection == null) {
            return;
        }
        setCoordinates("(" + intersection.getX() + "; " + intersection.getY() + ")");
    }

    public void updateWaypointName() {
        setWaypointName("Waypoint #" + getIndex());
    }

    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }

    public void onRemoveButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove waypoint ...");
    }

    public void onEditButtonAction(ActionEvent actionEvent) {
        System.out.println("Edit waypoint ...");
    }
}
