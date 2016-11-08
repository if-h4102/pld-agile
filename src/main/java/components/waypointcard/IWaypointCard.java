package components.waypointcard;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.AbstractWaypoint;

public interface IWaypointCard<WP extends AbstractWaypoint> {
    ObservableObjectValue<WP> waypointProperty();

    WP getWaypoint();

    void setWaypoint(WP waypoint);

    ObservableBooleanValue editableProperty();

    boolean getEditable();

    void setEditable(boolean editable);

    ObservableList<Node> getCornerControls();
}
