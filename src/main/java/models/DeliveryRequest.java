package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;

import java.util.Collection;

public class DeliveryRequest {
    final private SimpleObjectProperty<Warehouse> warehouse = new SimpleObjectProperty<>();
    final private SimpleSetProperty<DeliveryAddress> deliveryAddresses = new SimpleSetProperty<>();
    private int startPlanningTimestamp;

    public DeliveryRequest(Warehouse warehouse, Collection<DeliveryAddress> deliveryAddresses, int startPlanningTimestamp) {
        this.warehouse.setValue(warehouse);
        for (DeliveryAddress deliveryAddress : deliveryAddresses) {
            addDeliveryAddress(deliveryAddress);
        }
        this.startPlanningTimestamp = startPlanningTimestamp;
    }

    @Requires("!deliveryAddresses.contains(deliveryAddress)")
    @Ensures("deliveryAddresses.contains(deliveryAddress)")
    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean added = this.deliveryAddresses.add(deliveryAddress);
        assert added;
    }

    @Requires("deliveryAddresses.contains(deliveryAddress)")
    @Ensures("!deliveryAddresses.contains(deliveryAddress)")
    public void removeDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean removed = this.deliveryAddresses.remove(deliveryAddress);
        assert removed;
    }

    // TODO
    public Warehouse getWareHouse() {
        return null;
    }

    // TODO
    public Iterable<DeliveryAddress> getDeliveryAddresses() {
        return null;
    }
}
