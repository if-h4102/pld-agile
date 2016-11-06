package components.waypointcard;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import models.Intersection;
import models.Warehouse;

import java.io.IOException;

public class WarehouseCard extends WaypointCardBase<Warehouse> {
    @FXML
    protected HBox cornerControls;

    private SimpleStringProperty coordinates;
    private SimpleStringProperty startTime;

    public WarehouseCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/WarehouseCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setName(this.computeName());
        updateCoordinates();

        waypointProperty().addListener(event -> {
            updateCoordinates();
            this.setName(this.computeName());
        });
    }

    // Coordinates
    public final SimpleStringProperty coordinatesProperty() {
        if (coordinates == null) {
            coordinates = new SimpleStringProperty(this, "coordinates");
        }
        return coordinates;
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

    protected String computeName() {
        Warehouse waypoint = getWaypoint();
        if (waypoint == null) {
            return "";
        }
        return "Warehouse";
    }

    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }

    public final void setCoordinates(String value) {
        coordinatesProperty().setValue(value);
    }
}
