package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import models.AbstractWaypoint;
import models.Planning;

public interface IPlanningDetailsState {
    IPlanningDetailsState enterState(IPlanningDetailsState previousState);
    IPlanningDetailsState leaveState(IPlanningDetailsState nextState);
    IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends AbstractWaypoint> listChange);
    IPlanningDetailsState onPlanningChange(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue);
    IPlanningDetailsState onAddWaypointAction(AddWaypointAction action);
    IPlanningDetailsState onRemoveWaypointAction(RemoveWaypointAction action);
    IPlanningDetailsState onCancelAddWaypointAction(CancelAddWaypointAction action);
    IPlanningDetailsState onSaveNewWaypoint(SaveDeliveryAddress action);
    void refreshView();
}
