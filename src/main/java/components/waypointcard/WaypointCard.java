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
import models.AbstractWaypoint;
import models.DeliveryAddress;
import models.Intersection;
import models.Warehouse;

import java.io.IOException;

/**
 * This component is a wrapper for a DeliveryAddressCard or WarehouseCard.
 * It chooses the best concrete card according to the type of the `waypoint` property.
 *
 * @param <WP> Concrete implementation of an AbstractWaypoint
 */
public class WaypointCard<WP extends AbstractWaypoint> extends AnchorPane {
    @FXML
    public HBox cornerControls;
    @FXML
    protected AnchorPane concreteCard;
    private SimpleObjectProperty<WP> waypoint;
    private SimpleBooleanProperty readOnly;

    public WaypointCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/WaypointCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.waypointProperty().addListener(event -> {
            try {
                this.updateConcreteCard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Waypoint
    public final SimpleObjectProperty<WP> waypointProperty() {
        if (waypoint == null) {
            waypoint = new SimpleObjectProperty<>(this, "waypoint", null);
        }
        return waypoint;
    }

    public final void setWaypoint(WP value) {
        waypointProperty().setValue(value);
    }

    public final WP getWaypoint() {
        return waypoint == null ? null : waypointProperty().getValue();
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

    // CornerControls
    public ObservableList<Node> getCornerControls() {
        return cornerControls.getChildren();
    }

    protected void updateConcreteCard() throws Exception {
        AbstractWaypoint waypoint = getWaypoint();
        if (waypoint == null) {
            concreteCard.getChildren().clear();
            return;
        }
        // TODO: check if value changed before clearing and replacing
        concreteCard.getChildren().clear();

        Node concreteCardContent;

        if (waypoint instanceof Warehouse) {
            Warehouse warehouse = (Warehouse) waypoint;
            WarehouseCard node = new WarehouseCard();
            node.setWaypoint(warehouse);
            concreteCardContent = node;
        } else if (waypoint instanceof DeliveryAddress) {
            DeliveryAddress deliveryAddress = (DeliveryAddress) waypoint;
            DeliveryAddressCard node = new DeliveryAddressCard();
            node.setWaypoint(deliveryAddress);
            concreteCardContent = node;
        } else {
            // TODO: real exception
            throw new Exception("Unknown concrete waypoint");
        }

        concreteCard.getChildren().add(concreteCardContent);
        AnchorPane.setLeftAnchor(concreteCardContent, 0.0);
        AnchorPane.setRightAnchor(concreteCardContent, 0.0);
    }
}
