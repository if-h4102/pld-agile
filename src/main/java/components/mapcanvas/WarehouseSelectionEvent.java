package components.mapcanvas;

import models.Warehouse;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * 
 * This class corresponds to the selection of the warehouse in the mapCanvas, it is usually send to the MapScreen 
 * which will update the corresponding tooltip. 
 *
 */
public class WarehouseSelectionEvent extends Event {
	public static final EventType<WarehouseSelectionEvent> WAREHOUSE_SELECTION = new EventType<>("WAREHOUSE_SELECTION");
	double canvasEventX;
	double canvasEventY;
	
	Warehouse warehouse;
	/**Constructor of the Warehouse selection Event
	 * 
	 * @param house - The warehouse selected by clicking on its position on the mapCanvas
	 * @param posX - The position along the X axis of the mouse when the click happened
	 * @param posY - The position along the Y axis of the mouse when the click happened
	 */
	public WarehouseSelectionEvent(Warehouse house, double posX, double posY) {
		super(WAREHOUSE_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		warehouse = house;
		// TODO Auto-generated constructor stub
	}
	
	
	/** Get the position X of the warehouse on the canvas.
	 * 
	 * @return canvasEventX
	 */
	public double getX(){
		return canvasEventX;
	}
	/**Get the position Y of the warehouse on the canvas.
	 * 
	 * @return canvasEventY
	 */
	public double getY(){
		return canvasEventY;
	}
	/**Get the warehouse associated to the selectionEvent
	 * 
	 * @return warehouse
	 */
	public Warehouse getWarehouse(){
		return warehouse;
	}
}
