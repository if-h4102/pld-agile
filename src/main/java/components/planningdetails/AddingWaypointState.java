package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.SaveDeliveryAddress;
import components.mapcanvas.DeliverySelectionEvent;
import components.mapcanvas.IntersectionSelectionEvent;
import components.mapcanvas.WarehouseSelectionEvent;
import components.waypointcard.EditableDeliveryAddressCard;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import models.*;
import services.command.AddWaypointAfterCommand;
import services.command.CommandManager;

import org.jetbrains.annotations.NotNull;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class AddingWaypointState extends PlanningDetailsState {
    private final int index;
    private final EditableDeliveryAddressCard editableCard;
    private final Intersection intersection;

    public AddingWaypointState(PlanningDetails planningDetails, int index, Intersection intersection) {
        super(planningDetails);
        this.index = index;
        this.intersection = intersection;
        this.editableCard = new EditableDeliveryAddressCard();
    }

    @Override
    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
        super.enterState(previousState);
        ObservableList<Node> nodes = this.planningDetails.planningDetailsVBox.getChildren();
        DeliveryAddress tmpDeliveryAddress = new DeliveryAddress(intersection, 0);
        EditableDeliveryAddressCard editable = new EditableDeliveryAddressCard();
        editable.setWaypoint(tmpDeliveryAddress);
        nodes.add(this.index, editable);
        return this;
    }

    @Override
    public IPlanningDetailsState leaveState(IPlanningDetailsState nextState) {
        return nextState;
    }

    @Override
    @NotNull
    public IPlanningDetailsState onSaveNewWaypoint(@NotNull SaveDeliveryAddress event) {
        System.out.println("Saving new address");
        DeliveryAddress deliveryAddress = event.getDeliveryAddress();
        Planning planning = this.planningDetails.getPlanning();
        event.setIndex(this.index);

        return new DefaultState(this.planningDetails);
    }

    public IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends PlanningWaypoint> listChange) {
        // Prevent refresh of nodes ?
        return this;
    }

    @Override
    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return this;
    }

    @Override
    @NotNull
    public IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action) {
        return new DefaultState(this.planningDetails);
    }
}
