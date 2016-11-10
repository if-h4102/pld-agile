package components.waypointcard;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.DeliveryAddress;
import models.Intersection;

import java.io.IOException;

/**
 * This component is a wrapper for a delivery address, it is show in the Planning Details Pane at the right of the canvas
 */
public class DeliveryAddressCard extends WaypointCardBase<DeliveryAddress> {
    @FXML
    protected HBox cornerControls;
    @FXML
    protected AnchorPane timeConstraints;

    private final SimpleBooleanProperty hasTimeContraints = new SimpleBooleanProperty(this, "hasTimeContraints", false);
    private SimpleStringProperty coordinates;
    private SimpleStringProperty deliveryDuration;
    private SimpleStringProperty timeStart;
    private SimpleStringProperty timeEnd;

    /**Constructor of the delivery address card.
     * Load the associate fxml file.
     * Bind the properties corresponding to the fxml objects cornerControls and timeconstraints.
     * Add a listener to the change of the Waypoint the card contains.
     * 
     */
    public DeliveryAddressCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/DeliveryAddressCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.timeConstraints.managedProperty().bind(this.timeConstraints.visibleProperty());
        this.timeConstraints.visibleProperty().bind(this.hasTimeContraintsProperty());

        this.setName(this.computeName());
        updateCoordinates();

        waypointProperty().addListener((observable, oldValue, newValue) -> this.onWaypointChange(oldValue, newValue));
    }

    // Coordinates
    /** Create the accessible coordinates for the card
     * 
     * @return the coordinates property
     */
    public final SimpleStringProperty coordinatesProperty() {
        if (coordinates == null) {
            coordinates = new SimpleStringProperty(this, "coordinates");
        }
        return coordinates;
    }

    /**
     * Update the coordinates of the according to the waypoint the card contains.
     */
    public void updateCoordinates() {
        final DeliveryAddress waypoint = getWaypoint();
        if (waypoint == null) {
            return;
        }
        final Intersection intersection = waypoint.getIntersection();
        if (intersection == null) {
            return;
        }
        setCoordinates("(" + intersection.getX() + "; " + intersection.getY() + ")");
    }

    /**Return the name of the denomination waypoint.
     * @return "" if the card'waypoint is null, 
     * "DeliveryAddress" if the card'waypoint is not null 
     * or "DeliveryAddress + waypoint.id" if the intersection is not ull
     */
    protected String computeName() {
        DeliveryAddress waypoint = getWaypoint();
        if (waypoint == null) {
            return "";
        }
        Intersection intersection = waypoint.getIntersection();
        if (intersection == null) {
            return "DeliveryAddress";
        }
        return "DeliveryAddress #" + intersection.getId();
    }

    /**Create an accessible boolean to the time constraints. 
     * 
     * @return The time constraints property.
     */
    public final SimpleBooleanProperty hasTimeContraintsProperty() {
        return this.hasTimeContraints;
    }

    /**Enable to know if the Waypoint has timeConstraints or not
     * 
     * @return true if it has timeCostraints, false otherwise.
     */
    public final boolean getHasTimeConstraints() {
        return this.hasTimeContraintsProperty().getValue();
    }

    /**Set the timeConstraints true or false
     * 
     * @param value - True if it has timeCostraints, false otherwise.
     */
    public final void setHasTimeContraints(boolean value) {
        this.hasTimeContraintsProperty().setValue(value);
    }

    /**
     * @return The observable property for the current content (most specific card for the current waypoint).
     */
    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

    /**Get the coordinates of the card.
     * 
     * @return The card coordinates.
     */
    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }

    /**Set the coordinates of the card
     * 
     * @param value - The new coordinates.
     */
    public final void setCoordinates(String value) {
        coordinatesProperty().setValue(value);
    }

    /** Update the coordinates of the card according to the new delivery address.
     * 
     * @param oldValue
     * @param newValue - The new delivery address
     */
    protected void onWaypointChange(DeliveryAddress oldValue, DeliveryAddress newValue) {
        if (oldValue == newValue) {
            return;
        }

        this.updateCoordinates();
        this.setName(this.computeName());
        if (newValue == null) {
            return;
        }
        int startTime = newValue.getTimeStart();
        int endTime = newValue.getTimeEnd();
        if (startTime == 0 && endTime == 86400) {
            setHasTimeContraints(false);
        } else {
            setHasTimeContraints(true);
        }
    }
}
