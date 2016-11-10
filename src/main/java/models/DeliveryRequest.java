package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

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

    /**
     * Instantiate a delivery request based on the given parameters.
     * @param cityMap the city map on which teh request is based on.
     * @param warehouse the warehouse in which the delivery has to start.
     * @param deliveryAddresses the list of all addresses that must be delivered.
     * @param startPlanningTimestamp the timestamp representing the starting time of the delivery.
     */
    public DeliveryRequest(CityMap cityMap, Warehouse warehouse, Collection<DeliveryAddress> deliveryAddresses, int startPlanningTimestamp) {
        this.cityMap = cityMap;
        this.warehouse = warehouse;
        this.deliveryAddresses = new TreeSet<DeliveryAddress>();
        //this.deliveryAddresses.forEach(this::addDeliveryAddress);
        this.deliveryAddresses = new TreeSet<DeliveryAddress>();
        for (DeliveryAddress deliveryAddress : deliveryAddresses) {
            this.deliveryAddresses.add(deliveryAddress);
        }
        this.startPlanningTimestamp = startPlanningTimestamp;
    }

    /**
     * Add the given delivery address to the current request.
     * If the request already contains the address, it won't be added.
     * @param deliveryAddress the delivery address to add.
     */
    @Requires("!deliveryAddresses.contains(deliveryAddress)")
    @Ensures("deliveryAddresses.contains(deliveryAddress)")
    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean added = this.deliveryAddresses.add(deliveryAddress);
        assert added;
    }

    /**
     * Removes the given deliver address from the current request.
     * If the request doesn't contain the address, does nothing.
     * @param deliveryAddress the delivery address to remove.
     */
    @Requires("deliveryAddresses.contains(deliveryAddress)")
    @Ensures("!deliveryAddresses.contains(deliveryAddress)")
    public void removeDeliveryAddress(DeliveryAddress deliveryAddress) {
        boolean removed = this.deliveryAddresses.remove(deliveryAddress);
        assert removed;
    }

    /**
     * Get the warehouse of the current request.
     * @return the warehouse by which the delivery should begin.
     */
    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    /**
     * Get an iterator on the delivery addresses of the current request.
     * @return an iterator on the delivery addresses of the current request.
     */
    public Iterable<DeliveryAddress> getDeliveryAddresses() {
        return this.deliveryAddresses;
    }

    /**
     * Get the city map associated to the request.
     * @return Map of the city containing the intersections of the way-points of this request.
     */
    public CityMap getCityMap() {
        return this.cityMap;
    }

    /**
     * Compute a complete graph of routes between the way-points of this delivery graph.
     * @return A complete graph containing all the routes between all the waypoints of this request.
     */
    public DeliveryGraph computeDeliveryGraph() {
        // TODO: Move the computation of the delivery graph here ?
        return this.getCityMap().computeDeliveryGraph(this);
    }

    /**
     * Return whether or not the current request is the same that the given one.
     * @param obj the object to check.
     * @return true only if the given object is exactly the same that the current one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryRequest)) {
            return false;
        }

        DeliveryRequest other = (DeliveryRequest) obj;

        if (this.startPlanningTimestamp != other.startPlanningTimestamp) {
            return false;
        }
        if (!this.warehouse.equals(other.warehouse)) {
            return false;
        }
        if (this.deliveryAddresses.size() != other.deliveryAddresses.size()) {
            return false;
        }
        for (DeliveryAddress deliveryAddress : this.deliveryAddresses) {
            if (!other.deliveryAddresses.contains(deliveryAddress)) {
                return false;
            }
        }

        return true;
    }
}
