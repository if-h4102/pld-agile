package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWayPoint;

public class RemoveWaypointEvent extends Event {
	public static final EventType<RemoveWaypointEvent> TYPE = new EventType<>("REMOVE_WAYPOINT");

    private final AbstractWayPoint waypoint;

	public RemoveWaypointEvent(AbstractWayPoint waypoint) {
		super(RemoveWaypointEvent.TYPE);
        this.waypoint = waypoint;
	}

	public AbstractWayPoint getWaypoint() {
        return this.waypoint;
    }
}
