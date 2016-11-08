package components.mapcanvas;

import models.Warehouse;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public class WarehouseSelectionEvent extends Event {
	public static final EventType<WarehouseSelectionEvent> WAREHOUSE_SELECTION = new EventType<>("WAREHOUSE_SELECTION");
	double canvasEventX;
	double canvasEventY;
	
	Warehouse warehouse;
	public WarehouseSelectionEvent(Warehouse house, double posX, double posY) {
		super(WAREHOUSE_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		warehouse = house;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public double getX(){
		return canvasEventX;
	}
	public double getY(){
		return canvasEventY;
	}
	public Warehouse getWarehouse(){
		return warehouse;
	}
}
