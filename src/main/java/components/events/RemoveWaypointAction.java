package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class RemoveWaypointAction extends Event {
	public static final EventType<RemoveWaypointAction> TYPE = new EventType<>("REMOVE_WAYPOINT");

    private final AbstractWaypoint waypoint;

	public RemoveWaypointAction(AbstractWaypoint waypoint) {
		super(RemoveWaypointAction.TYPE);
        this.waypoint = waypoint;
	}

	public AbstractWaypoint getWaypoint() {
        return this.waypoint;
    }
}
