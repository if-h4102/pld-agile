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
    public final SimpleStringProperty coordinatesProperty() {
        if (coordinates == null) {
            coordinates = new SimpleStringProperty(this, "coordinates");
        }
        return coordinates;
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

    public final SimpleBooleanProperty hasTimeContraintsProperty() {
        return this.hasTimeContraints;
    }

    public final boolean getHasTimeConstraints() {
        return this.hasTimeContraintsProperty().getValue();
    }

    public final void setHasTimeContraints(boolean value) {
        this.hasTimeContraintsProperty().setValue(value);
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
        int endTime = newValue.getTimeStart();
        if (startTime == 0 && endTime == 86400) {
            setHasTimeContraints(false);
        } else {
            setHasTimeContraints(true);
        }
    }
}
