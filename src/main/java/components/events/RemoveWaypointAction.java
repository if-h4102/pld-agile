package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class RemoveWaypointAction extends Event {
    
    /**
     * Define the event type for the removing of a way point.
     */
    public static final EventType<RemoveWaypointAction> TYPE = new EventType<>("REMOVE_WAYPOINT");

    /**
     * The way point which is removed.
     */
    private final AbstractWaypoint waypoint;

    /**
     * Construct a new event type and define the way point which is removed.
     * @param waypoint The way point which is removed.
     */
    public RemoveWaypointAction(AbstractWaypoint waypoint) {
	super(RemoveWaypointAction.TYPE);
        this.waypoint = waypoint;
    }

    /**
     * Return the way point which is removed.
     * @return Return the way point which is removed.
     */
    public AbstractWaypoint getWaypoint() {
        return this.waypoint;
    }
}
