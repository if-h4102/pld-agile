package components.mapcanvas;

import models.DeliveryAddress;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public class DeliverySelectionEvent extends Event {
	public static final EventType<DeliverySelectionEvent> DELIVERY_SELECTION = new EventType<>("DELIVERY_SELECTION");
	
	double canvasEventX;
	double canvasEventY;
	DeliveryAddress delivery;
	public DeliverySelectionEvent(DeliveryAddress deliv, double posX, double posY) {
		super(DELIVERY_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		delivery = deliv;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public double getX(){
		return canvasEventX;
	}
	public double getY(){
		return canvasEventY;
	}
	public DeliveryAddress getDeliveryAddress(){
		return delivery;
	}
}
