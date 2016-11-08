package models;

public class DeliveryAddress extends AbstractWaypoint {
    final private int deliveryDuration;

    public DeliveryAddress(Intersection intersection, int deliveryDuration) {
        super(intersection);
        this.deliveryDuration = deliveryDuration;
    }

    public DeliveryAddress(Intersection intersection, int deliveryDuration, int deliveryTimeStart, int deliveryTimeEnd) {
        super(intersection, deliveryTimeStart, deliveryTimeEnd);
        this.deliveryDuration = deliveryDuration;
    }

    @Override
    public int getDuration() {
        return this.deliveryDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryAddress))
            return false;

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}
