package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.RemoveWaypointAction;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.AbstractWaypoint;
import models.Planning;
import models.PlanningWaypoint;
import org.jetbrains.annotations.NotNull;
import services.map.IMapService;

import java.io.IOException;

/**
 * This class represents an item of the PlanningDetails list. It describes a
 * step of the planning: the departure time, arrival time, time of delivery
 * (after eventually waiting for the opening), the status (possible, impossible)
 * and of course the concerned waypoint. It also contains buttons to add a
 * a waypoint just before this one or to remove the current waypoint.
 */
public class PlanningDetailsItem extends AnchorPane {
    /**
     * Content (with the blue line) before the waypoint card
     */
    @FXML
    protected StackPane pathBefore;

    /**
     * Time information
     */
    @FXML
    protected VBox dataBefore;

    /**
     * Content (with the blue line) after the waypoint card
     */
    @FXML
    protected StackPane pathAfter;

    /**
     * Eventual data after the waypoint, unused
     */
    @FXML
    protected VBox dataAfter;

    /**
     * Button to add a waypoint just before the current waypoint.
     */
    @FXML
    protected Button addButton;

    /**
     * Button to cancel the addition of a waypoint just before the current waypoint.
     */
    @FXML
    protected Button cancelAddButton;

    /**
     * Button to remove the current waypoint.
     */
    @FXML
    protected Button removeButton;

    /**
     * The current item (waypoint with data from the planning)
     */
    private final SimpleObjectProperty<PlanningWaypoint> item = new SimpleObjectProperty<>(this, "item", null);

    /**
     * A reference to the planning of where this item belongs to.
     */
    private final SimpleObjectProperty<Planning> planning = new SimpleObjectProperty<>(this, "planning", null);

    /**
     * A map service to activate waypoints on the map when click on them in the
     * PlanningDetails pane.
     */
    private SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this, "mapService", null);

    /**
     * The index (+ 1) of the current waypoint.
     */
    private final SimpleIntegerProperty index = new SimpleIntegerProperty(this, "index", 0);

    private final SimpleBooleanProperty displayPathBefore = new SimpleBooleanProperty(this, "displayPathBefore", true);
    private final SimpleBooleanProperty displayDataBefore = new SimpleBooleanProperty(this, "displayDataBefore", true);
    private final SimpleBooleanProperty displayPathAfter = new SimpleBooleanProperty(this, "displayPathAfter", true);
    private final SimpleBooleanProperty displayDataAfter = new SimpleBooleanProperty(this, "displayDataAfter", true);
    private final SimpleBooleanProperty displayAddButton = new SimpleBooleanProperty(this, "displayAddButton", true);
    private final SimpleBooleanProperty displayCancelAddButton = new SimpleBooleanProperty(this, "displayCancelAddButton", false);
    private final SimpleBooleanProperty displayRemoveButton = new SimpleBooleanProperty(this, "displayRemoveButton", true);

    public PlanningDetailsItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetailsItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mapServiceProperty().addListener(this::onMapServiceChange);
        this.pathBefore.managedProperty().bindBidirectional(this.pathBefore.visibleProperty());
        this.dataBefore.managedProperty().bindBidirectional(this.dataBefore.visibleProperty());
        this.pathAfter.managedProperty().bindBidirectional(this.pathAfter.visibleProperty());
        this.dataAfter.managedProperty().bindBidirectional(this.dataAfter.visibleProperty());
        this.addButton.managedProperty().bindBidirectional(this.addButton.visibleProperty());
        this.cancelAddButton.managedProperty().bindBidirectional(this.cancelAddButton.visibleProperty());
        this.removeButton.managedProperty().bindBidirectional(this.removeButton.visibleProperty());

        this.pathBefore.visibleProperty().bindBidirectional(this.displayPathBeforeProperty());
        this.dataBefore.visibleProperty().bindBidirectional(this.displayDataBeforeProperty());
        this.pathAfter.visibleProperty().bindBidirectional(this.displayPathAfterProperty());
        this.dataAfter.visibleProperty().bindBidirectional(this.displayDataAfterProperty());
        this.addButton.visibleProperty().bindBidirectional(this.displayAddButtonProperty());
        this.cancelAddButton.visibleProperty().bindBidirectional(this.displayCancelAddButtonProperty());
        this.removeButton.visibleProperty().bindBidirectional(this.displayRemoveButtonProperty());
        this.itemProperty().addListener(this::onItemChange);
    }

    /**
     * @return The observable property for the PlanningWaypoint displayed by
     * this component
     */
    public final SimpleObjectProperty<PlanningWaypoint> itemProperty() {
        return this.item;
    }

    /**
     * @return The currently displayed PlanningWaypoint
     */
    public final PlanningWaypoint getItem() {
        return this.itemProperty().getValue();
    }

    /**
     * @param value The new PlanningWaypoint to display.
     */
    public final void setItem(PlanningWaypoint value) {
        this.itemProperty().setValue(value);
    }

    /**
     * @return The observable property for the planning containing the currently
     * displayed item.
     */
    @NotNull
    public final SimpleObjectProperty<Planning> planningProperty() {
        return this.planning;
    }

    /**
     * @return The planning containing this PlanningWaypoint
     */
    @NotNull
    public final Planning getPlanning() {
        return this.planningProperty().getValue();
    }

    /**
     * @param value The new value for the planning.
     */
    public final void setPlanning(@NotNull Planning value) {
        this.planningProperty().setValue(value);
    }

    /**
     * @return The current index (+1) of the displayed PlanningWaypoint.
     */
    @NotNull
    public final SimpleIntegerProperty indexProperty() {
        return this.index;
    }

    /**
     * @param value The new value of the index (+1)
     */
    public final void setIndex(int value) {
        this.indexProperty().setValue(value);
    }

    /**
     * @return the current index (+1) of the displayed PlanningWaypoint
     */
    public final int getIndex() {
        return this.indexProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayPathBeforeProperty() {
        return this.displayPathBefore;
    }

    public final void setDisplayPathBefore(boolean value) {
        this.displayPathBeforeProperty().setValue(value);
    }

    public final boolean getDisplayPathBefore() {
        return this.displayPathBeforeProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayDataBeforeProperty() {
        return this.displayDataBefore;
    }

    public final void setDisplayDataBefore(boolean value) {
        this.displayDataBeforeProperty().setValue(value);
    }

    public final boolean getDisplayDataBefore() {
        return this.displayDataBeforeProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayPathAfterProperty() {
        return this.displayPathAfter;
    }

    public final void setDisplayPathAfter(boolean value) {
        this.displayPathAfterProperty().setValue(value);
    }

    public final boolean getDisplayPathAfter() {
        return this.displayPathAfterProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayDataAfterProperty() {
        return this.displayDataAfter;
    }

    public final void setDisplayDataAfter(boolean value) {
        this.displayDataAfterProperty().setValue(value);
    }

    public final boolean getDisplayDataAfter() {
        return this.displayDataAfterProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayAddButtonProperty() {
        return this.displayAddButton;
    }

    public final void setDisplayAddButton(boolean value) {
        this.displayAddButtonProperty().setValue(value);
    }

    public final boolean getDisplayAddButton() {
        return this.displayAddButtonProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayCancelAddButtonProperty() {
        return this.displayCancelAddButton;
    }

    public final void setDisplayCancelAddButton(boolean value) {
        this.displayCancelAddButtonProperty().setValue(value);
    }

    public final boolean getDisplayCancelAddButton() {
        return this.displayCancelAddButtonProperty().getValue();
    }

    @NotNull
    public final SimpleBooleanProperty displayRemoveButtonProperty() {
        return this.displayRemoveButton;
    }

    public final void setDisplayRemoveButton(boolean value) {
        this.displayRemoveButtonProperty().setValue(value);
    }

    public final boolean getDisplayRemoveButton() {
        return this.displayRemoveButtonProperty().getValue();
    }

    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    public void setMapService(IMapService value) {
        this.mapServiceProperty().setValue(value);
    }

    /**
     * Handle change of displayed PlanningWaypoint.
     * It ensures that the style of the component matches the possible
     * (time constraints are respected) attribute of the PlanningWaypoint.
     *
     * @param observable The observable object that triggered the event
     * @param oldValue   The old value of the PlanningWaypoint
     * @param newValue   The new value of the Planning Waypoint
     */
    protected void onItemChange(ObservableValue<? extends PlanningWaypoint> observable, PlanningWaypoint oldValue, PlanningWaypoint newValue) {
        if (newValue == oldValue) {
            return;
        }
        if (newValue != null) {
            if (!newValue.getIsPossible()) {
                this.getStyleClass().add("invalid");
            } else {
                this.getStyleClass().remove("invalid");
            }
        }
    }

    /**
     * Handle the mouse click on the remove button and emit a RemoveWaypointAction
     * representing this user action.
     *
     * @param actionEvent The event that triggered this handler.
     */
    public void onRemoveButtonAction(@NotNull ActionEvent actionEvent) {
        PlanningWaypoint item = this.getItem();
        if (item == null) {
            return;
        }
        AbstractWaypoint startWaypoint = item.getTargetWaypoint();
        if (startWaypoint == null) {
            return;
        }
        fireEvent(new RemoveWaypointAction(startWaypoint));
    }

    /**
     * Handle the mouse click on the add button and emit a AddWaypointAction
     * representing this user action.
     *
     * @param actionEvent The event that triggered this handler.
     */
    public void onAddButtonAction(@NotNull ActionEvent actionEvent) {
        fireEvent(new AddWaypointAction(this.getIndex() - 1));
    }

    /**
     * Handle the mouse click on the cancel addition button and emit a
     * CancelAddWaypointAction representing this user action.
     *
     * @param actionEvent The event that triggered this handler.
     */
    public void onCancelAddButtonAction(@NotNull ActionEvent actionEvent) {
        fireEvent(new CancelAddWaypointAction());
    }

    /**
     * Handle changes of the map service and ensure that the "sub-listeners" are
     * always attached to the latest value.
     *
     * @param observable The observable object for the map service
     * @param oldValue Previous value of the map service
     * @param newValue New value of the map service.
     */
    protected void onMapServiceChange(ObservableValue<? extends IMapService> observable, IMapService oldValue, IMapService newValue) {
        if (oldValue == newValue) {
            return;
        }
        // System.out.println("Item: Service changed");
        if (oldValue != null) {
            oldValue.activeWaypointProperty().removeListener(this::onActiveWaypointChange);
        }
        if (newValue != null) {
            newValue.activeWaypointProperty().addListener(this::onActiveWaypointChange);
        }
    }

    /**
     * Handle changes of the active waypoint (highlighted waypoint on the map, with
     * a tooltip)
     *
     * @param observable The observable value of the active waypoint
     * @param oldValue Previous active waypoint
     * @param newValue New active waypoint
     */
    protected void onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue) {
//        System.out.println("Item: ActiveWaypoint change" + newValue.getId());
    }

    /**
     * Update the active waypoint when clicking on the waypointCard of this item.
     *
     * @param event The mouse click that triggered this handler.
     */
    public void onWaypointCardClickedAction(MouseEvent event) {
        this.getMapService().setActiveWaypoint(this.getItem().getTargetWaypoint());
    }
}
