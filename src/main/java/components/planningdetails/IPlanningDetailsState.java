package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import models.AbstractWaypoint;
import models.Planning;
import models.PlanningWaypoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents the state of the controller of a PlanningDetails
 * component.
 */
public interface IPlanningDetailsState {
    /**
     * This method is called automatically when entering to this state.
     *
     * @param previousState A reference to the previous state of the component.
     * @return An eventual redirection for the state. Not supported currently.
     */
    @NotNull
    IPlanningDetailsState enterState(@NotNull IPlanningDetailsState previousState);

    /**
     * This method is called automatically when leaving this state.
     *
     * @param nextState A reference to the next state of the component.
     * @return An eventual redirection for the state. Not supported currently.
     */
    @NotNull
    IPlanningDetailsState leaveState(@NotNull IPlanningDetailsState nextState);

    /**
     * Reacts to a change in the list of PlanningWaypoints in the current
     * planning.
     *
     * @param listChange An object representing the changes that occured in the list.
     * @return The state of the component after the change was handled.
     */
    @NotNull
    IPlanningDetailsState onPlanningWaypointsChange(@NotNull ListChangeListener.Change<? extends PlanningWaypoint> listChange);

    /**
     * Reacts to a change of the Planning object.
     *
     * @param observable The observable value wrapping the Planning object.
     * @param oldValue   The old value of the Planning object.
     * @param newValue   The new value of the Planning object.
     * @return The state of the component after the change was handled.
     */
    @NotNull
    IPlanningDetailsState onPlanningChange(@NotNull ObservableValue<? extends Planning> observable, @Nullable Planning oldValue, @Nullable Planning newValue);

    /**
     * Reacts to a change of the active Waypoint.
     *
     * @param observable The observable value wrapping the AbstractWaypoint object.
     * @param oldValue   The old value of the active waypoint.
     * @param newValue   The new value of the active waypoint.
     * @return The state of the component after the change was handled.
     */
    @NotNull
    IPlanningDetailsState onActiveWaypointChange(@NotNull ObservableValue<? extends AbstractWaypoint> observable, @Nullable AbstractWaypoint oldValue, @Nullable AbstractWaypoint newValue);

    /**
     * Handles the "add waypoint" user action.
     * This is triggered by a click on the "add" button to add a waypoint between two
     * existing waypoints.
     *
     * @param action An object representing the user action. It mainly has the
     *               index where to insert the new waypoint.
     * @return The state of the component after the user action was handled.
     */
    @NotNull
    IPlanningDetailsState onAddWaypointAction(@NotNull AddWaypointAction action);

    /**
     * Handles the "remove waypoint" user action.
     * This is triggered by a click on the "remove" icon at the top right of
     * the card of the waypoint.
     *
     * @param action An object representing the user action. It mainly has a
     *               reference to the waypoint to be removed.
     * @return The state of the component after the user action was handled.
     */
    @NotNull
    IPlanningDetailsState onRemoveWaypointAction(@NotNull RemoveWaypointAction action);

    /**
     * Handles the "cancel waypoint addition" user action.
     * This is triggered by a click on the "cancel" icon that appears when
     * the user is selecting an intersection or is entering the data of the
     * waypoint.
     *
     * @param action An object representing the user action.
     * @return The state of the component after the user action was handled.
     */
    @NotNull
    IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action);

    /**
     * Handles the "save new waypoint" user action.
     * This is triggered by a click on the "check" icon that appears when the
     * user finishes entering the data of the new waypoint.
     *
     * @param action An object representig the user action. It contains the
     *               the waypoint object to add and its index.
     * @return The state of the component after the user action was handled.
     */
    @NotNull
    IPlanningDetailsState onSaveNewWaypoint(@NotNull SaveDeliveryAddress action);

    /**
     * Refresh the view of the component depending on its state.
     */
    void refreshView();
}
