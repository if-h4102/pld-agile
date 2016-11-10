package components.planningdetails;

import components.events.AddWaypointAction;
import javafx.beans.value.ObservableValue;
import models.AbstractWaypoint;
import models.Planning;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This is the default state of the PlanningDetails component.
 * Its main purpose is to display the planning: the ordered way points with
 * the time of the various events involved.
 */
public class DefaultState extends PlanningDetailsState {
    /**
     * Creates a DefaultState bound to a PlanningDetails component.
     *
     * @param planningDetails The planning managed by this state object.
     */
    public DefaultState(PlanningDetails planningDetails) {
        super(planningDetails);
    }

    /**
     * Changes the state of the component to "selecting an intersection".
     * It is triggered when the user clicks on the "add" button between two
     * existing waypoints.
     *
     * @param action The object represent the action of the user. Mainly contains
     *               the index where to add the new waypoint.
     * @return The SelectingWaypointsState to use as the new state of the planning
     * component.
     */
    @NotNull
    public IPlanningDetailsState onAddWaypointAction(@NotNull AddWaypointAction action) {
        return new SelectingWaypointState(this.planningDetails, action.getIndex());
    }
}
