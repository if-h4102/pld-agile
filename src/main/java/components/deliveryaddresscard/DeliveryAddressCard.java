package components.deliveryaddresscard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.AbstractWayPoint;
import models.DeliveryAddress;

import java.io.IOException;

public class DeliveryAddressCard<E extends DeliveryAddress> extends AnchorPane {
    @FXML
    protected Label content;

    private SimpleObjectProperty<E> deliveryAddress;

    public DeliveryAddressCard() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/deliveryaddresscard/DeliveryAddressCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Item
    public final SimpleObjectProperty<E> deliveryAddressProperty() {
        if (deliveryAddress == null) {
            deliveryAddress = new SimpleObjectProperty<>(this, "deliveryAddress", null);
        }
        return deliveryAddress;
    }

    public final void setDeliveryAddress(E value) {
        deliveryAddressProperty().setValue(value);
    }

    public final E getDeliveryAddress() {
        return deliveryAddress == null ? null : deliveryAddressProperty().getValue();
    }
/*
    public void onRemoveButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove intersection ...");
    }

    public void onEditButtonAction(ActionEvent actionEvent) {
        System.out.println("Edit intersection ...");
    }
    */
}
