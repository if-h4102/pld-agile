package components.waypointcard;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.AbstractWaypoint;
import org.jetbrains.annotations.Nullable;

/**
 * This interface describes a component displaying informations about a waypoint
 * (DeliveryAddress or Warehouse)
 *
 * @param <WP> The concrete class of the waypoint.
 */
public interface IWaypointCard<WP extends AbstractWaypoint> {
    /**
     * @return The observable object for the waypoint to display.
     */
    ObservableObjectValue<WP> waypointProperty();

    /**
     * @return The current value of the displayed waypoint.
     */
    @Nullable
    WP getWaypoint();

    /**
     * Update the displayed waypoint.
     *
     * @param waypoint The new waypoint to display in this component
     */
    void setWaypoint(@Nullable WP waypoint);

    /**
     * Give access to the top-right corner of the waypoint to allow other
     * components to extend this one with buttons.
     *
     * @return The list of nodes in the node for extensions.
     */
    ObservableList<Node> getCornerControls();
}
