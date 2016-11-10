package components.events;

import javafx.event.Event;
import javafx.event.EventType;

public class CancelAddWaypointAction extends Event {
	public static final EventType<CancelAddWaypointAction> TYPE = new EventType<>("CANCEL_ADD_WAYPOINT");

	public CancelAddWaypointAction() {
		super(CancelAddWaypointAction.TYPE);
	}
}
