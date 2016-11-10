package components.waypointcard;

import components.events.SaveDeliveryAddress;
import components.timefield.TimeField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.DeliveryAddress;
import models.Intersection;

import java.io.IOException;

/**
 * This component is a wrapper for an editable delivery address, it is show in the Planning Details Pane at the right of the canvas
 * It appears when the user click the button + on the pane
 */
public class EditableDeliveryAddressCard extends WaypointCardBase<DeliveryAddress> {
    @FXML
    protected TimeField deliveryDurationField;
    @FXML
    protected TimeField startTimeField;
    @FXML
    protected TimeField endTimeField;
    @FXML
    protected CheckBox hasTimeConstraintsCheckBox;
    @FXML
    protected HBox cornerControls;
    @FXML
    protected AnchorPane timeConstraints;

    private final SimpleBooleanProperty hasTimeContraints = new SimpleBooleanProperty(this, "hasTimeContraints", false);
    private final SimpleIntegerProperty deliveryDuration = new SimpleIntegerProperty(this, "deliveryDuration", 0);
    private final SimpleIntegerProperty startTime = new SimpleIntegerProperty(this, "startTime", 0);
    private final SimpleIntegerProperty endTime = new SimpleIntegerProperty(this, "endTime", 0);
    private SimpleStringProperty coordinates = new SimpleStringProperty(this, "coordinates");

    /**Constructor of the Card.
     * Load the fxml file.
     * Bind the properties to the fxml objects associated.
     * Add a listener to the Waypoint they are showing.
     * 
     */
    public EditableDeliveryAddressCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/EditableDeliveryAddressCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.timeConstraints.managedProperty().bind(this.timeConstraints.visibleProperty());
        this.timeConstraints.visibleProperty().bind(this.hasTimeContraintsProperty());

        this.hasTimeConstraintsCheckBox.selectedProperty().bindBidirectional(this.hasTimeContraintsProperty());
        this.deliveryDurationField.timeProperty().bindBidirectional(this.deliveryDurationProperty());
        this.startTimeField.timeProperty().bindBidirectional(this.startTimeProperty());
        this.endTimeField.timeProperty().bindBidirectional(this.endTimeProperty());

        this.setName(this.computeName());
        updateCoordinates();

        waypointProperty().addListener((observable, oldValue, newValue) -> this.onWaypointChange(oldValue, newValue));
    }

    /** Create the accessible coordinates for the card
     * 
     * @return the coordinates property
     */
    public final SimpleStringProperty coordinatesProperty() {
        return coordinates;
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

    /**Create the accessible deliverDuration for the card.
     * 
     * @return the deliver duration property
     */
    public final SimpleIntegerProperty deliveryDurationProperty() {
        return this.deliveryDuration;
    }

    /**Get the delivery duration of the waypoint.
     * 
     * @return delivery duration
     */
    public final Integer getDeliveryDuration() {
        return this.deliveryDurationProperty().getValue();
    }

    /**Set the delivery duration of the waypoint
     * 
     * @param value - The new delivery duration
     */
    public final void setDeliveryDuration(Integer value) {
        this.deliveryDurationProperty().setValue(value);
    }

    /** Create the accessible startTime for the card.
     * 
     * @return startTimeProperty
     */
    public final SimpleIntegerProperty startTimeProperty() {
        return this.startTime;
    }

    /**Get the time when the delivery can start on the waypoint.
     * 
     * @return time start
     */
    public final int getStartTime() {
        return this.startTimeProperty().getValue();
    }

    /**Set the time when the delivery can start on the waypoint.
     * 
     * @param value - The starting time
     */
    public final void setStartTime(int value) {
        this.startTimeProperty().setValue(value);
    }

    /** Create the accessible endTime for the card.
     * 
     * @return endTimeProperty
     */
    public final SimpleIntegerProperty endTimeProperty() {
        return this.endTime;
    }

    /**Get the time when the delivery can end on the waypoint.
     * 
     * @return time end
     */
    public final int getEndTime() {
        return this.endTimeProperty().getValue();
    }

    /**Set the time when the delivery can end on the waypoint.
     * 
     * @param value - The ending time
     */
    public final void setEndTime(int value) {
        this.endTimeProperty().setValue(value);
    }

    /**
     * @return The observable property for the current content (most specific card for the current waypoint).
     */
    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
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
    }

    /** Return a new Delivery address to add to the deliveryrequest 
     * if it is well created (possible delivery duration, endTime < stratTime, coherence between duration, end and starttimr)
     * 
     * @return the new delivery address
     * @throws Exception
     */
    protected DeliveryAddress computeWaypoint() throws Exception {
        DeliveryAddress result;

        DeliveryAddress oldWaypoint = this.getWaypoint();
        Intersection intersection = oldWaypoint == null ? null : oldWaypoint.getIntersection();
        int deliveryDuration = this.getDeliveryDuration();

        if (deliveryDuration < 0) {
            throw new Exception("Delivery duration must be positive");
        }

        if (this.getHasTimeConstraints()) {
            int startTime = this.getStartTime();
            int endTime = this.getEndTime();
            if (endTime < startTime) {
                throw new Exception("End time must be greater than start time");
            } else if (startTime + deliveryDuration < endTime) {
                throw new Exception("The time constraints are too narrow: delivery duration does not fit!");
            }

            result = new DeliveryAddress(intersection, deliveryDuration, startTime, endTime);
        } else {
            result = new DeliveryAddress(intersection, deliveryDuration);
        }
        return result;
    }

    /**Fire an Event to notify a new delivery address has been created and set the delivery address as the current waypoint.
     * 
     * @param actionEvent - click on save
     */
    public void onSaveButtonAction(ActionEvent actionEvent) {
        DeliveryAddress current;
        try {
            current = this.computeWaypoint();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            return;
        }
        this.setWaypoint(current);
        this.fireEvent(new SaveDeliveryAddress(current));
    }
}
