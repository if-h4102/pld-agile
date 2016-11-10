package components.mapcanvas;

import models.Intersection;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * This class corresponds to the selection of an intersection in the mapCanvas, it is usually send to the MapScreen 
 * which will update the corresponding tooltip. 
 *
 */
public class IntersectionSelectionEvent extends Event {
	public static final EventType<IntersectionSelectionEvent> INTERSECTION_SELECTION = new EventType<>("INTERSECTION_SELECTION");
	
	double canvasEventX;
	double canvasEventY;
	Intersection intersection;
	/**Constructor of the Intersection selection Event
	 * 
	 * @param inter - The intersection selected by clicking on its position on the mapCanvas
	 * @param posX - The position along the X axis of the mouse when the click happened
	 * @param posY - The position along the Y axis of the mouse when the click happened
	 */
	public IntersectionSelectionEvent(Intersection inter, double posX, double posY) {
		super(INTERSECTION_SELECTION);
		canvasEventX = posX;
		canvasEventY = posY;
		intersection = inter;
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
	/**Get the intersection associated to the selectionEvent
	 * 
	 * @return intersection
	 */
	public Intersection getIntersection(){
		return intersection;
	}
}
