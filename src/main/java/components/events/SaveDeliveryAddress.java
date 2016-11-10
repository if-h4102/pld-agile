package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;
import models.DeliveryAddress;

public class SaveDeliveryAddress extends Event {

    /**
     * Define the event type for the saving of a delivery address.
     */
    public static final EventType<SaveDeliveryAddress> TYPE = new EventType<>("SAVE_DELIVERY_ADDRESS");

    /**
     * The delivery address to save.
     */
    private final DeliveryAddress deliveryAddress;
    
    /**
     * The index of the delivery address which is saved.
     */
    private int index;

    /**
     * Construct a new event type and define de delivery address which is saved.
     * @param deliveryAddress The delivery address which is saved.
     */
    public SaveDeliveryAddress(DeliveryAddress deliveryAddress) {
	super(SaveDeliveryAddress.TYPE);
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Return the delivery address which is saved.
     * @return Return the delivery address which is saved.
     */
    public DeliveryAddress getDeliveryAddress() {
        return this.deliveryAddress;
    }
	
    /** 
     * Return the index of the delivery address which is saved.
     * @return Return the index of the delivery address which is saved.
     */
    public int getIndex() {
        return this.index;
    }
    
    /**
     * Change the index of the delivery address which is saved.
     * @param index The index of the delivery addres which is saved.
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
