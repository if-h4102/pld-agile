package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;
import models.DeliveryAddress;

public class SaveDeliveryAddress extends Event {

    public static final EventType<SaveDeliveryAddress> TYPE = new EventType<>("SAVE_DELIVERY_ADDRESS");

    private final DeliveryAddress deliveryAddress;
    private int index;

    public SaveDeliveryAddress(DeliveryAddress deliveryAddress) {
	super(SaveDeliveryAddress.TYPE);
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryAddress getDeliveryAddress() {
        return this.deliveryAddress;
    }
	
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
}
