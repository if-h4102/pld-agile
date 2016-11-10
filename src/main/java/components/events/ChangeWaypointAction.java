package components.events;


import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class ChangeWaypointAction extends Event {
	public static final EventType<ChangeWaypointAction> TYPE = new EventType<>("CHANGE_WAYPOINT");

    private final AbstractWaypoint waypoint;

	public ChangeWaypointAction(AbstractWaypoint waypoint) {
		super(ChangeWaypointAction.TYPE);
		this.waypoint = waypoint;
	}

	public AbstractWaypoint getWaypoint() {
        return this.waypoint;
    }
}
