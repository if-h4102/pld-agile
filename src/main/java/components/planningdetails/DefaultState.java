package components.planningdetails;

import components.events.AddWaypointAction;
import components.waypointcard.EditableDeliveryAddressCard;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.AbstractWaypoint;
import models.DeliveryAddress;
import models.Planning;
import models.Route;
import services.map.IMapService;

public class DefaultState extends PlanningDetailsState {
    public DefaultState(PlanningDetails planningDetails) {
        super(planningDetails);
    }

    protected IPlanningDetailsState onSaveDeliveryAddress() {
        return this;
    }

    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return new SelectingWaypointState(this.planningDetails, action.getIndex());
    }

    @Override
    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
        return this;
    }

    @Override
    public IPlanningDetailsState leaveState(IPlanningDetailsState nextState) {
        return nextState;
    }

    public IPlanningDetailsState onPlanningChange(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue) {
        if (oldValue == newValue) {
            return this;
        }
        super.onPlanningChange(observable, oldValue, newValue);
        this.planningDetails.waypointsToPlanningDetails();
        return this;
    }
}
