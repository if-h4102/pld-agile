package components.mapcanvas;

import models.DeliveryAddress;
import models.Warehouse;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * This class corresponds to the selection of a delivery address in the mapCanvas, it is usually send to the MapScreen 
 * which will update the corresponding tooltip. 
 *
 */
public class DeliverySelectionEvent extends Event {
	public static final EventType<DeliverySelectionEvent> DELIVERY_SELECTION = new EventType<>("DELIVERY_SELECTION");
	double canvasEventX;
	double canvasEventY;
	
	DeliveryAddress delivery;
	/**Constructor of the Delivery selection Event
	 * 
	 * @param deliv - The delivery address selected by clicking on its position on the mapCanvas
	 * @param posX - The position along the X axis of the mouse when the click happened
	 * @param posY - The position along the Y axis of the mouse when the click happened
	 */
	public DeliverySelectionEvent(DeliveryAddress deliv, double posX, double posY) {
		super(DELIVERY_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		delivery = deliv;
	}
	
	
	/** Get the position X of the warehouse on the canvas.
	 * 
	 * @return canvasEventX
	 */
	public double getX(){
		return canvasEventX;
	}
	/** Get the position Y of the warehouse on the canvas.
	 * 
	 * @return canvasEventY
	 */
	public double getY(){
		return canvasEventY;
	}
	/** Get the delivery address X of the delivery address associated to the canvas.
	 * 
	 * @return delivery
	 */
	public DeliveryAddress getDeliveryAddress(){
		return delivery;
	}
}
