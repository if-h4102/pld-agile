package components.waypointcard;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Intersection;
import models.Warehouse;

import java.io.IOException;

public class WarehouseCard extends WaypointCardBase<Warehouse> {
    @FXML
    public HBox cornerControls;
    @FXML
    protected AnchorPane timeConstraints;

    private SimpleObjectProperty<Warehouse> waypoint;
    private SimpleStringProperty waypointName;
    private SimpleStringProperty coordinates;
    private SimpleStringProperty deliveryDuration;
    private SimpleStringProperty timeStart;
    private SimpleStringProperty timeEnd;
    private SimpleBooleanProperty readOnly;

    public WarehouseCard() {
        updateWaypointName();
        updateCoordinates();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/WarehouseCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        waypointProperty().addListener(event -> {
            updateCoordinates();
            updateWaypointName();
        });
    }

    // Waypoint
    public final SimpleObjectProperty<Warehouse> waypointProperty() {
        if (waypoint == null) {
            waypoint = new SimpleObjectProperty<>(this, "waypoint", null);
        }
        return waypoint;
    }

    public final void setWaypoint(Warehouse value) {
        waypointProperty().setValue(value);
    }

    public final Warehouse getWaypoint() {
        return waypoint == null ? null : waypointProperty().getValue();
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
        final Warehouse waypoint = getWaypoint();
        if (waypoint == null) {
            return;
        }
        final Intersection intersection = waypoint.getIntersection();
        if (intersection == null) {
            return;
        }
        setCoordinates("(" + intersection.getX() + "; " + intersection.getY() + ")");
    }

    // Editable
    public final SimpleBooleanProperty readOnlyProperty() {
        if (readOnly == null) {
            readOnly = new SimpleBooleanProperty(this, "readOnly", false);
        }
        return readOnly;
    }

    public final void setReadOnly(boolean value) {
        readOnlyProperty().setValue(value);
    }

    public final boolean getReadOnly() {
        return readOnly == null ? false : readOnlyProperty().getValue();
    }

    public void updateWaypointName() {
        String name;
        Warehouse waypoint = getWaypoint();
        if (waypoint == null) {
            name = "";
        } else {
            name = "Warehouse";
        }
        setWaypointName(name);
    }

    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }
}
