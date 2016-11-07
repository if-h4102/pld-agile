package components.planningdetails;

import components.events.AddWaypointAction;
import models.Planning;

public class DisplayState implements IPlanningDetailsState {
    private final PlanningDetails planningDetails;

    DisplayState (PlanningDetails planningDetails, int index) {
        this.planningDetails = planningDetails;
    }

    public IPlanningDetailsState onPlanningChange(Planning oldValue, Planning newValue) {
        return this;
    }

    public IPlanningDetailsState onAddWaypoint(AddWaypointAction action) {
        return this;
    }
}
