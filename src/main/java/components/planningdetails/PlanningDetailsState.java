package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.Planning;
import models.PlanningWaypoint;

import java.util.ArrayList;
import java.util.List;

public abstract class PlanningDetailsState implements IPlanningDetailsState {
    protected final PlanningDetails planningDetails;

    public PlanningDetailsState(PlanningDetails planningDetails) {
        this.planningDetails = planningDetails;
    }

    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
        this.refreshView();
        return this;
    }

    public IPlanningDetailsState leaveState(IPlanningDetailsState nextState) {
        return nextState;
    }

    public IPlanningDetailsState onSaveNewWaypoint(SaveDeliveryAddress action) {
        return this;
    }

    public IPlanningDetailsState onAddWaypoint(AddWaypointAction action) {
        return this;
    }

    public IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends PlanningWaypoint> listChange) {
        this.refreshView();
        return this;
    }

    public IPlanningDetailsState onPlanningChange(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue) {
        if (oldValue == newValue) {
            return this;
        }
        if (oldValue != null) {
            oldValue.planningWaypointsProperty().removeListener(this.planningDetails::onPlanningWaypointsChange);
        }
        if (newValue != null) {
            newValue.planningWaypointsProperty().addListener(this.planningDetails::onPlanningWaypointsChange);
        }
        this.refreshView();
        return this;
    }

    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return this;
    }

    public IPlanningDetailsState onRemoveWaypointAction(RemoveWaypointAction action) {
        return this;
    }

    public IPlanningDetailsState onCancelAddWaypointAction(CancelAddWaypointAction action) {
        return this;
    }

    public void refreshView() {
        this.planningDetails.planningWaypointsToView();
        ObservableList<Node> nodes = this.planningDetails.planningDetailsVBox.getChildren();
        if (nodes.size() == 0) {
            return;
        }
        List<PlanningDetailsItem> itemNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof PlanningDetailsItem) {
                itemNodes.add((PlanningDetailsItem) node);
            } else {
                System.err.println("Unexpected node");
                System.err.println(node);
            }
        }
        itemNodes.get(0).setDisplayPathBefore(false);
        itemNodes.get(0).setDisplayDataAfter(false);
        itemNodes.get(0).setDisplayRemoveButton(false);
        itemNodes.get(itemNodes.size() - 1).setDisplayPathAfter(false);
        itemNodes.get(itemNodes.size() - 1).setDisplayRemoveButton(false);
    }
}
