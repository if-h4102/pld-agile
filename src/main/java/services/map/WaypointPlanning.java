package services.map;

import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;

public class WaypointPlanning {
	final private SimpleObjectProperty<AbstractWaypoint> waypoint = new SimpleObjectProperty<>();
    /**
     * @return The observable property for the waypoint containing the currently
     * displayed item.
     */
    public final SimpleObjectProperty<AbstractWaypoint> waypointProperty() {
        return this.waypoint;
    }

    public final void setWaypoint(AbstractWaypoint value) {
        this.waypointProperty().setValue(value);
    }

    public final AbstractWaypoint getWaypoint() {
        return this.waypointProperty().getValue();
    }
}
