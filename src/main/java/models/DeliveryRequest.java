package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * A DeliveryRequest contains all the data to compute a planning matching the needs of a user.
 * It contains the warehouse with the departure time and a set of addresses to deliver.
 */
public class DeliveryRequest {
    /**
     * The warehouse is the point where the delivery should start and end.
     */
    private Warehouse warehouse;

    /**
     * The set of addresses where we should deliver goods.
     */
    private Set<DeliveryAddress> deliveryAddresses;

    /**
     * The time at which the delivery starts from the warehouse.
     */
    private int startPlanningTimestamp;

    /**
     * The map of the city containing the intersections of the way-points of this request.
     */
    private final CityMap cityMap;

    public DeliveryRequest(CityMap cityMap, Warehouse warehouse, Collection<DeliveryAddress> deliveryAddresses, int startPlanningTimestamp) {
        this.cityMap = cityMap;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Iterable<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    /**
     *
     * @return Map of the city containing the intersections of the way-points of this request.
     */
    public CityMap getCityMap() {
        return this.cityMap;
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
