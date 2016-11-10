package components.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SelectIntersectionAction extends Event {
    
    /**
     * Define the event type for the selection of an intersection.
     */
    public static final EventType<SelectIntersectionAction> TYPE = new EventType<>("SELECT_INTERSECTION_ACTION");

    /** 
     * The index of the selected intersection.
     */
    private final int index;

    /**
     * Construct a new event type and define the index of the selected intersection.
     * @param index The index of the selected intersection.
     */
    public SelectIntersectionAction(int index) {
	super(SelectIntersectionAction.TYPE);
        this.index = index;
    }

    /**
     * Return the index of the selected intersection.
     * @return Return the index of the selected intersection.
     */
    public int getIndex() {
        return this.index;
    }
}
