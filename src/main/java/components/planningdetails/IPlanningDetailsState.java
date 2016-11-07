package components.planningdetails;

import components.events.AddWaypointAction;
import models.Planning;

public interface IPlanningDetailsState {
    IPlanningDetailsState onPlanningChange(Planning oldValue, Planning newValue);
    IPlanningDetailsState onAddWaypoint(AddWaypointAction action);
}
