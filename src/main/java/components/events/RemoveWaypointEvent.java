package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class RemoveWaypointEvent extends Event {
	public static final EventType<RemoveWaypointEvent> TYPE = new EventType<>("REMOVE_WAYPOINT");

    private final AbstractWaypoint waypoint;

	public RemoveWaypointEvent(AbstractWaypoint waypoint) {
		super(RemoveWaypointEvent.TYPE);
        this.waypoint = waypoint;
	}

	public AbstractWaypoint getWaypoint() {
        return this.waypoint;
    }
}
