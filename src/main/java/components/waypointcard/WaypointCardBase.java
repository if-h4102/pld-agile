package components.waypointcard;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import models.AbstractWaypoint;

public abstract class WaypointCardBase<WP extends AbstractWaypoint> extends AnchorPane implements IWaypointCard<WP> {
    private final SimpleObjectProperty<WP> waypoint = new SimpleObjectProperty<>(this, "waypoint", null);
    private final SimpleBooleanProperty editable = new SimpleBooleanProperty(this, "editable", false);
    private final SimpleStringProperty name = new SimpleStringProperty(this, "name", "");

    /**
     * Get the observable property for the waypoint attached to this node.
     *
     * @return The observable property for the waypoint attached to this node.
     */
    public final SimpleObjectProperty<WP> waypointProperty() {
        return this.waypoint;
    }

    /**
     * Get the waypoint currently attached to this node.
     *
     * @return The waypoint currently attached to this node.
     */
    public final WP getWaypoint() {
        return this.waypointProperty().getValue();
    }

    /**
     * Set the waypoint displayed by this node.
     *
     * @param value The waypoint to attach to this node.
     */
    public final void setWaypoint(WP value) {
        this.waypointProperty().setValue(value);
    }

    /**
     * Get the observable property for the editable state of this waypoint card.
     *
     * @return The observable property for the editable state of this waypoint card.
     */
    public final SimpleBooleanProperty editableProperty() {
        return this.editable;
    }

    /**
     * Get the editable state of this waypoint card.
     *
     * @return The current value for the editable state of this waypoint card.
     */
    public final boolean getEditable() {
        return this.editableProperty().getValue();
    }

    /**
     * Set the editable state of this waypoint card.
     *
     * @param value The new value for the editable state of this waypoint card.
     */
    public final void setEditable(boolean value) {
        this.editableProperty().setValue(value);
    }

    /**
     * Get the observable property for the displayed name of this waypoint.
     *
     * @return The observable property for the displayed name of this waypoint.
     */
    public final SimpleStringProperty nameProperty() {
        return this.name;
    }

    /**
     * @return The currently displayed name of the waypoint.
     */
    public final String getName() {
        return this.nameProperty().getValue();
    }

    /**
     * @param value The new value for the currently displayed name of the waypoint.
     */
    public final void setName(String value) {
        this.nameProperty().setValue(value);
    }

    abstract public ObservableList<Node> getCornerControls();
    abstract protected String computeName();
}
