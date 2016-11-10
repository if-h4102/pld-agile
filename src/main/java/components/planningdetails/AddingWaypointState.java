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

import java.util.ArrayList;
import java.util.List;

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
    public void refreshView() {
        super.refreshView();
        ObservableList<Node> nodes = this.planningDetails.planningDetailsVBox.getChildren();
        if (nodes.size() == 0) {
            return;
        }
        List<PlanningDetailsItem> itemNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof PlanningDetailsItem) {
                itemNodes.add((PlanningDetailsItem) node);
            } else {
                System.err.println("Unexpected node");
                System.err.println(node);
            }
        }
        for (PlanningDetailsItem pdi : itemNodes) {
            pdi.setDisplayAddButton(false);
            pdi.setDisplayRemoveButton(false);
        }
        itemNodes.get(this.index).setDisplayDataBefore(false);
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

    public IPlanningDetailsState onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue) {
        if (oldValue == newValue) {
            return this;
        }
        if (oldValue != null) {
            //oldValue.waypointsProperty().removeListener(this.planningDetails::onPlanningWaypointsChange);
        }
        if (newValue != null) {
            //newValue.waypointsProperty().addListener(this.planningDetails::onPlanningWaypointsChange);
        }
        return this;
    }

    @Override
    @NotNull
    public IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action) {
        return new DefaultState(this.planningDetails);
    }
}
