package models;

import javafx.beans.property.SimpleIntegerProperty;

public class DeliveryAddress extends AbstractWayPoint {
    final private SimpleIntegerProperty deliveryDuration = new SimpleIntegerProperty();

    public DeliveryAddress(Intersection intersection, int deliveryDuration) {
        super(intersection);
        this.deliveryDuration.setValue(deliveryDuration);
    }

    public int getDeliveryDuration() {
        return this.deliveryDuration.getValue();
    }
    
    @Override
    public boolean equals(Object obj)  {
        if (!(obj instanceof DeliveryAddress))
            return false;
        
        DeliveryAddress other = (DeliveryAddress) obj;
        return this.deliveryDuration.getValue() == other.deliveryDuration.getValue() && super.equals(other);
    }
}
