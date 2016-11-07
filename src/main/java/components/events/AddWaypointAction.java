package components.events;

import javafx.event.Event;
import javafx.event.EventType;
import models.AbstractWaypoint;

public class AddWaypointAction extends Event {
	public static final EventType<AddWaypointAction> TYPE = new EventType<>("ADD_WAYPOINT");

    private final int index;

	public AddWaypointAction(int index) {
		super(AddWaypointAction.TYPE);
        this.index = index;
	}

	public int getIndex() {
        return this.index;
    }
}
