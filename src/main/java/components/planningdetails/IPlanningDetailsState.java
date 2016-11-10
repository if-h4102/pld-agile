package components.planningdetails;

import components.events.AddWaypointAction;
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
    IPlanningDetailsState onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue);
    IPlanningDetailsState onAddWaypointAction(AddWaypointAction action);
    IPlanningDetailsState onSaveNewWaypoint(SaveDeliveryAddress action);

}
