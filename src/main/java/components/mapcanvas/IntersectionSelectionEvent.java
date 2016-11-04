package components.mapcanvas;

import models.Intersection;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public class IntersectionSelectionEvent extends Event {
	public static final EventType<IntersectionSelectionEvent> INTERSECTION_SELECTION = new EventType<>("INTERSECTION_SELECTION");
	
	double canvasEventX;
	double canvasEventY;
	Intersection intersection;
	public IntersectionSelectionEvent(Intersection inter, double posX, double posY) {
		super(INTERSECTION_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		intersection = inter;
		// TODO Auto-generated constructor stub
	}
	
	public double getX(){
		return canvasEventX;
	}
	public double getY(){
		return canvasEventY;
	}
	public Intersection getIntersection(){
		return intersection;
	}
}
