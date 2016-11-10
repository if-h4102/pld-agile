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

    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return new SelectingWaypointState(this.planningDetails, action.getIndex());
    }
}
