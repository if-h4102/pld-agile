package models;

public class DeliveryAddress extends AbstractWayPoint {

    final private int deliveryDuration;

    public DeliveryAddress(Intersection intersection, int deliveryDuration) {
        super(intersection);
        this.deliveryDuration = deliveryDuration;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryAddress))
            return false;

        DeliveryAddress other = (DeliveryAddress) obj;
        return this.deliveryDuration == other.deliveryDuration && super.equals(other);
    }
}
