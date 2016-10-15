package models;

import java.util.Collection;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;

public class DeliveryRequest {
    final private SimpleObjectProperty<Warehouse> warehouse = new SimpleObjectProperty<>();
    final private SimpleSetProperty<DeliveryAddress> deliveryAddresses = new SimpleSetProperty<>();

    public DeliveryRequest (Warehouse warehouse, Collection<DeliveryAddress> deliveryAddresses) {
    	this.warehouse.setValue(warehouse);
    	for (DeliveryAddress deliveryAddress : deliveryAddresses) {
    		addDeliveryAddress(deliveryAddress);
    	}
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
