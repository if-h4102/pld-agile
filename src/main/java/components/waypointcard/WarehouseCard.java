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

/**
 * The WarehouseCard component is able to display data about a warehouse in a
 * small rectanlge (card).
 */
public class WarehouseCard extends WaypointCardBase<Warehouse> {
    /**
     * References to the corner controls, for transclusion of buttons (extension point).
     */
    @FXML
    protected HBox cornerControls;

    /**
     * The string representation of the coordinates of the warehouse.
     */
    private SimpleStringProperty coordinates;

    /** Constructor of the class WarehouseCard.
     * Load the associate fxml file.
     * Add a listener to the coordinates of the event received.
     */
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

    /**
     * @return The observable property for the string representation of the coordinates.
     */
    public final SimpleStringProperty coordinatesProperty() {
        if (coordinates == null) {
            coordinates = new SimpleStringProperty(this, "coordinates");
        }
        return coordinates;
    }

    /* Update the coordinates of the card according to the warehouse position.
     *
     */
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

    /**
     * Computes the title of the card: it returns the constant "Warehouse" if the
     * warehouse is set (empty string otherwise).
     * @return The title of the card.
     */
    @Override
    protected String computeName() {
        Warehouse waypoint = getWaypoint();
        if (waypoint == null) {
            return "";
        }
        return "Warehouse";
    }

    /**
     * @return The observable list of corner controls: elements injected in the top
     * corner by parent components.
     */
    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

    /** Get the coordinates of the card.
     *
     * @return the coordinates of the Card
     */
    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }

    /** Set the coordinates to the new value.
     *
     * @param value - The new coordinates
     */
    public final void setCoordinates(String value) {
        coordinatesProperty().setValue(value);
    }
}
