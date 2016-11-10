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

    public final SimpleStringProperty coordinatesProperty() {
        return coordinates;
    }

    public final String getCoordinates() {
        return coordinatesProperty().getValue();
    }

    public final void setCoordinates(String value) {
        coordinatesProperty().setValue(value);
    }

    public final SimpleBooleanProperty hasTimeContraintsProperty() {
        return this.hasTimeContraints;
    }

    public final boolean getHasTimeConstraints() {
        return this.hasTimeContraintsProperty().getValue();
    }

    public final void setHasTimeContraints(boolean value) {
        this.hasTimeContraintsProperty().setValue(value);
    }

    public final SimpleIntegerProperty deliveryDurationProperty() {
        return this.deliveryDuration;
    }

    public final Integer getDeliveryDuration() {
        return this.deliveryDurationProperty().getValue();
    }

    public final void setDeliveryDuration(Integer value) {
        this.deliveryDurationProperty().setValue(value);
    }

    public final SimpleIntegerProperty startTimeProperty() {
        return this.startTime;
    }

    public final int getStartTime() {
        return this.startTimeProperty().getValue();
    }

    public final void setStartTime(int value) {
        this.startTimeProperty().setValue(value);
    }

    public final SimpleIntegerProperty endTimeProperty() {
        return this.endTime;
    }

    public final int getEndTime() {
        return this.endTimeProperty().getValue();
    }

    public final void setEndTime(int value) {
        this.endTimeProperty().setValue(value);
    }

    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

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

    protected void onWaypointChange(DeliveryAddress oldValue, DeliveryAddress newValue) {
        if (oldValue == newValue) {
            return;
        }
        this.updateCoordinates();
        this.setName(this.computeName());
    }

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
