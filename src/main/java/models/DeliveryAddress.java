package models;

public class DeliveryAddress extends AbstractWaypoint {

    /**
     * The time that a delivery man need to deliver this waypoint.
     */
    final private int deliveryDuration;

    /**
     * Instantiate a new delivery address, without time constraints.
     * @param intersection the intersection on which the delivery address is.
     * @param deliveryDuration the time that a delivery man needs to deliver this waypoint.
     */
    public DeliveryAddress(Intersection intersection, int deliveryDuration) {
        super(intersection);
        this.deliveryDuration = deliveryDuration;
    }

    /**
     * Instantiate a new delivery address, with time constraints.
     * @param intersection the intersection on which the delivery address is.
     * @param deliveryDuration the time that a delivery man needs to deliver this waypoint.
     * @param deliveryTimeStart the starting time at which a delivery man can pass.
     * @param deliveryTimeEnd the ending time at which a delivery man can pass.
     */
    public DeliveryAddress(Intersection intersection, int deliveryDuration, int deliveryTimeStart, int deliveryTimeEnd) {
        super(intersection, deliveryTimeStart, deliveryTimeEnd);
        this.deliveryDuration = deliveryDuration;
    }

    /**
     * Get the duration during which a delivery man can pass.
     * @return the duration during which a delivery man can pass.
     */
    @Override
    public int getDuration() {
        return this.deliveryDuration;
    }

    /**
     * Return whether or not the current waypoint is the same that the given one.
     * @param obj the object to check.
     * @return true only if the given object is exactly the same that the current one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryAddress)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Get a string representing the current delivery address.
     * @return a string representation of the current delivery address.
     */
    @Override
    public String toString() {
        return "" + this.getId();
    }
}
