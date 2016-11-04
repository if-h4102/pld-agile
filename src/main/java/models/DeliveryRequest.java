package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class DeliveryRequest {

    private Warehouse warehouse;
    private Set<DeliveryAddress> deliveryAddresses;
    private int startPlanningTimestamp;

    public DeliveryRequest(Warehouse warehouse, Collection<DeliveryAddress> deliveryAddresses, int startPlanningTimestamp) {
        this.warehouse = warehouse; // TODO clone to avoid a later modification?
        this.deliveryAddresses = new TreeSet<DeliveryAddress>();
        for (DeliveryAddress deliveryAddress : deliveryAddresses) {
            addDeliveryAddress(deliveryAddress);
        }
        this.startPlanningTimestamp = startPlanningTimestamp;
    }

    @Requires("!deliveryAddresses.contains(deliveryAddress)")
    @Ensures("deliveryAddresses.contains(deliveryAddress)")
    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean added = deliveryAddresses.add(deliveryAddress);
        assert added;
    }

    @Requires("deliveryAddresses.contains(deliveryAddress)")
    @Ensures("!deliveryAddresses.contains(deliveryAddress)")
    public void removeDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean removed = deliveryAddresses.remove(deliveryAddress);
        assert removed;
    }

    public Warehouse getWareHouse() {
        return warehouse;
    }

    public Iterable<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryRequest))
            return false;

        DeliveryRequest other = (DeliveryRequest) obj;

        if (this.startPlanningTimestamp != other.startPlanningTimestamp)
            return false;

        if (!this.warehouse.equals(other.warehouse))
            return false;

        if (this.deliveryAddresses.size() != other.deliveryAddresses.size())
            return false;
        for (DeliveryAddress deliveryAddress : this.deliveryAddresses) {
            if (!other.deliveryAddresses.contains(deliveryAddress))
                return false;
        }

        return true;
    }
}
