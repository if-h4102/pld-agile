package models;

public class DeliveryAddress extends AbstractWayPoint {

    final private int deliveryDuration;

    @Override
    public int getDuration() {
        return this.deliveryDuration;
    }

    public DeliveryAddress(Intersection intersection, int deliveryDuration) {
        super(intersection);
        this.deliveryDuration = deliveryDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeliveryAddress))
            return false;

        return super.equals(obj);
    }

    @Override
    public String toString() {
        //return "DeliveryAddress [intersection=" + intersection + ", deliveryDuration=" + deliveryDuration + "]";
        return "" + getId();
    }
}
