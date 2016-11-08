package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.DeliveryAddress;

public class SaveDeliveryAddress extends Event {
	public static final EventType<SaveDeliveryAddress> TYPE = new EventType<>("SAVE_DELIVERY_ADDRESS");

    private final DeliveryAddress deliveryAddress;

	public SaveDeliveryAddress(DeliveryAddress deliveryAddress) {
		super(SaveDeliveryAddress.TYPE);
        this.deliveryAddress = deliveryAddress;
	}

	public DeliveryAddress getDeliveryAddress() {
        return this.deliveryAddress;
    }
}
