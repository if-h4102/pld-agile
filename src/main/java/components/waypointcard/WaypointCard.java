package components.waypointcard;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import models.AbstractWaypoint;
import models.DeliveryAddress;
import models.Warehouse;

import java.io.IOException;

import components.events.SaveDeliveryAddress;

/**
 * This component is a wrapper for a DeliveryAddressCard or WarehouseCard.
 * It chooses the best concrete card according to the type of the `waypoint` property.
 */
public class WaypointCard extends AnchorPane implements IWaypointCard<AbstractWaypoint> {
    private final ReadOnlyObjectWrapper<WaypointCardBase<?>> content = new ReadOnlyObjectWrapper<>(this, "content", null);
    private final SimpleListProperty<Node> cornerControls = new SimpleListProperty<>(this, "cornerControls", FXCollections.observableArrayList());
    private final SimpleObjectProperty<AbstractWaypoint> waypoint = new SimpleObjectProperty<>(this, "waypoint", null);
    private final SimpleBooleanProperty editable = new SimpleBooleanProperty(this, "editable", false);
    @FXML
    protected AnchorPane root;

    /** Constructor of the class WayPointCard.
     * Load the associate fxml file.
     * 
     */
    public WaypointCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/waypointcard/WaypointCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.waypointProperty().addListener(event -> this.onWaypointChange());
        this.contentProperty().addListener(event -> this.onContentChange());
        this.cornerControlsProperty().addListener((observableValue, oldValue, newValue) -> {
            this.onCornerControlsChange(oldValue, newValue);
        });
    }

    /**
     * Get the observable property for the waypoint attached to this node.
     *
     * @return The observable property for the waypoint attached to this node.
     */
    public final SimpleObjectProperty<AbstractWaypoint> waypointProperty() {
        return this.waypoint;
    }

    /**
     * Get the waypoint currently attached to this node.
     *
     * @return The waypoint currently attached to this node.
     */
    public final AbstractWaypoint getWaypoint() {
        return this.waypointProperty().getValue();
    }

    /**
     * Set the waypoint displayed by this node.
     *
     * @param value The waypoint to attach to this node.
     */
    public final void setWaypoint(AbstractWaypoint value) {
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
     * @return The observable property for the current content (most specific card for the current waypoint).
     */
    public ReadOnlyObjectProperty<WaypointCardBase<?>> contentProperty() {
        return this.content.getReadOnlyProperty();
    }

    /**
     * Get the content node, this is the most specific card based on the type of the waypoint.
     *
     * @return The content node.
     */
    public WaypointCardBase<?> getContent() {
        return this.content.getValue();
    }

    /**
     * Set a new content node.
     *
     * @param content A new content node.
     */
    protected void setContent(WaypointCardBase<?> content) {
        this.content.setValue(content);
    }

    /**
     * @return The observable property for the current content (most specific card for the current waypoint).
     */
    public SimpleListProperty<Node> cornerControlsProperty() {
        return this.cornerControls;
    }

    /**
     * Get the content node, this is the most specific card based on the type of the waypoint.
     *
     * @return The content node.
     */
    public ObservableList<Node> getCornerControls() {
        return this.cornerControlsProperty().getValue();
    }

    /**
     * Set a new content node.
     *
     * @param content A new content node.
     */
    protected void setCornerControls(ObservableList<Node> content) {
        this.cornerControlsProperty().setValue(content);
    }

    /** Update the content of the WayPointCard according to the instance of the waypoint
     * 
     */
    protected void onWaypointChange() {
        AbstractWaypoint waypoint = getWaypoint();
        if (waypoint == null) {
            this.setContent(null);
            return;
        }

        WaypointCardBase<?> newContent;

        if (waypoint instanceof Warehouse) {
            WarehouseCard node = new WarehouseCard();
            node.setWaypoint((Warehouse) waypoint);
            newContent = node;
        } else if (waypoint instanceof DeliveryAddress) {
            DeliveryAddressCard node = new DeliveryAddressCard();
            node.setWaypoint((DeliveryAddress) waypoint);
            newContent = node;
        } else {
            Exception e = new Exception("Unknown concrete waypoint");
            e.printStackTrace();
            return;
        }

        this.setContent(newContent);
    }

    /** Update the inforations displayed on the card.
     * 
     */
    protected void onContentChange() {
        root.getChildren().clear();
        WaypointCardBase<?> newContent = getContent();
        if (newContent == null) {
            return;
        }
        AnchorPane.setLeftAnchor(newContent, 0.0);
        AnchorPane.setRightAnchor(newContent, 0.0);
        this.setCornerControls(newContent.getCornerControls());
        root.getChildren().add(newContent);
    }

    protected void onCornerControlsChange(ObservableList<Node> oldValue, ObservableList<Node> newValue) {
        if (newValue == oldValue) {
            return;
        }
        newValue.clear();
        newValue.addAll(oldValue);
    }
}
