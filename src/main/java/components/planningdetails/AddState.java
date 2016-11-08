package components.planningdetails;

import components.events.AddWaypointAction;
import models.Planning;

public class AddState implements IPlanningDetailsState {
    private final PlanningDetails planningDetails;
    private final int index;

    AddState (PlanningDetails planningDetails, int index) {
        this.planningDetails = planningDetails;
        this.index = index;
    }

    public IPlanningDetailsState onPlanningChange(Planning oldValue, Planning newValue) {
        return this;
    }

    public IPlanningDetailsState onAddWaypoint(AddWaypointAction action) {
        return this;
    }
}
