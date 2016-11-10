package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class AddWaypointAction extends Event {
    
    /**
     * Define the event type for the adding of a way point.
     */
    public static final EventType<AddWaypointAction> TYPE = new EventType<>("ADD_WAYPOINT");

    /**
     * Index at which the way point has to be added.
     */
    private final int index;

    /**
     * Construct a new event type and define the index at which the way point
     * has to be added.
     * @param index The index at which the way point has to be added.
     */
    public AddWaypointAction(int index) {
	super(AddWaypointAction.TYPE);
        this.index = index;
    }

    /**
     * Return the index at which the way point has to be added.
     * @return Return the index at which the way point has to be added.
     */
    public int getIndex() {
        return this.index;
    }
}
