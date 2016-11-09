package components.planningdetails;

import components.events.AddWaypointAction;
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
        this.planningDetails.waypointsToPlanningDetails();
        ObservableList<Node> nodes = this.planningDetails.vBox.getChildren();

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
        planning.addWaypoint(deliveryAddress, this.index);

        this.planningDetails.waypointsToPlanningDetails();

        return new DefaultState(this.planningDetails);
    }

    public IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends AbstractWaypoint> listChange) {
        this.planningDetails.waypointsToPlanningDetails();
        return this;
    }

    public IPlanningDetailsState onAddWaypoint(AddWaypointAction action) {
        return this;
    }

    public IPlanningDetailsState onPlanningChange(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue) {
        if (oldValue == newValue) {
            return this;
        }
        if (oldValue != null) {
            oldValue.waypointsProperty().removeListener(this.planningDetails::onPlanningWaypointsChange);
        }
        if (newValue != null) {
            newValue.waypointsProperty().addListener(this.planningDetails::onPlanningWaypointsChange);
        }
        return this;
    }

    @Override
    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return this;
    }
}
