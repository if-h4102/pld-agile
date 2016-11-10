package components.events;


import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class ChangeWaypointAction extends Event {
	
    /**
     * Define the event type for the changing of a way point.
     */
    public static final EventType<ChangeWaypointAction> TYPE = new EventType<>("CHANGE_WAYPOINT");

    /**
     * The waypoint which is changed.
     */
    private final AbstractWaypoint waypoint;

    /**
     * Construct a new type event and define the way point which is changed.
     * @param waypoint The way point which is changed.
     */
    public ChangeWaypointAction(AbstractWaypoint waypoint) {
	super(ChangeWaypointAction.TYPE);
	this.waypoint = waypoint;
    }

    /**
     * Return the way point which is changed.
     * @return Return the way point which is changed.
     */
    public AbstractWaypoint getWaypoint() {
        return this.waypoint;
    }
}
