package components.events;

import javafx.event.Event;
import javafx.event.EventType;

public class CancelAddWaypointAction extends Event {
    
    /**
     * Define the event type for the canceling of an add way point action.
     */
    public static final EventType<CancelAddWaypointAction> TYPE = new EventType<>("CANCEL_ADD_WAYPOINT");

    /**
     * Construct a new event type.
     */
    public CancelAddWaypointAction() {
	super(CancelAddWaypointAction.TYPE);
    }
}
