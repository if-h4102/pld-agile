package components.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SelectIntersectionAction extends Event {
	public static final EventType<SelectIntersectionAction> TYPE = new EventType<>("SELECT_INTERSECTION_ACTION");

    private final int index;

	public SelectIntersectionAction(int index) {
		super(SelectIntersectionAction.TYPE);
        this.index = index;
	}

	public int getIndex() {
        return this.index;
    }
}
