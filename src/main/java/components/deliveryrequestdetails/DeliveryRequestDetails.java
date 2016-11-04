package components.deliveryrequestdetails;

import components.intersectioncard.IntersectionCard;
import components.waypointcard.WaypointCard;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.*;

import java.io.IOException;

public class DeliveryRequestDetails extends ScrollPane {
    @FXML
    protected VBox vBox;
    private SimpleObjectProperty<DeliveryRequest> deliveryRequest;

    public DeliveryRequestDetails() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/deliveryrequestdetails/DeliveryRequestDetails.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        observeDeliveryRequest();
    }

    public void observeDeliveryRequest() {
        deliveryRequestProperty().addListener(event -> refreshAll());
    }

    private void refreshAll() {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();
        itemNodes.clear();
        final DeliveryRequest deliveryRequest = getDeliveryRequest();
        if (deliveryRequest == null) {
            return;
        }

        final WaypointCard<Warehouse> warehouseNode = new WaypointCard<>();
        warehouseNode.setWaypoint(deliveryRequest.getWarehouse());
        itemNodes.add(warehouseNode);

        for (DeliveryAddress da: deliveryRequest.getDeliveryAddresses()) {
            final WaypointCard<DeliveryAddress> node = new WaypointCard<>();
            node.setWaypoint(da);
            itemNodes.add(node);
        }
    }

    public final SimpleObjectProperty<DeliveryRequest> deliveryRequestProperty() {
        if (deliveryRequest == null) {
            deliveryRequest = new SimpleObjectProperty<>(this, "deliveryRequest", null);
        }
        return deliveryRequest;
    }

    public final void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        deliveryRequestProperty().setValue(deliveryRequest);
    }

    public final DeliveryRequest getDeliveryRequest() {
        return deliveryRequestProperty().getValue();
    }
}
