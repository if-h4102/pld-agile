package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import models.AbstractWaypoint;
import models.Planning;

public abstract class PlanningDetailsState implements IPlanningDetailsState {
    protected final PlanningDetails planningDetails;

    public PlanningDetailsState(PlanningDetails planningDetails) {
        this.planningDetails = planningDetails;
    }

    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
        return this;
    }

    public IPlanningDetailsState leaveState(IPlanningDetailsState nextState) {
        return nextState;
    }

    public IPlanningDetailsState onSaveNewWaypoint(SaveDeliveryAddress action) {
        return this;
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

    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return new SelectingWaypointState(this.planningDetails, 1);
    }
}
