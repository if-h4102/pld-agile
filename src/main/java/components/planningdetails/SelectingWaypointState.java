package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.*;
import org.jetbrains.annotations.NotNull;
import services.map.IMapService;

import java.util.ArrayList;
import java.util.List;

/**
 * The SelectingWaypointState represents the state of the PlanningDetails
 * when the use clicked on the "add" button to add a new waypoint and we are
 * waiting for him to choose the address (Intersecion) where to add the new
 * Waypoint.
 */
public class SelectingWaypointState extends PlanningDetailsState {
    /**
     * The index (position) where the new waypoint should be added in the
     * planning.
     */
    private final int index;

    public SelectingWaypointState(PlanningDetails planningDetails, int index) {
        super(planningDetails);
        this.index = index;
    }

    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
        super.enterState(previousState);

        IMapService mapService = this.planningDetails.getMapService();
        if (mapService == null) {
            System.err.println("Missing map service");
            return previousState;
        }
        mapService.promptIntersection().thenAccept(this::onIntersection);
        return this;
    }

    protected void onIntersection (Intersection intersection) {
        this.planningDetails.changeState(new AddingWaypointState(this.planningDetails, this.index, intersection));
    }

    /**
     * Restore the default state when the user cancels the selection of address
     * for the waypoint.
     *
     * @param action An object representing the user action.
     * @return The default state to use.
     */
    @Override
    @NotNull
    public IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action) {
        return new DefaultState(this.planningDetails);
    }

    /**
     * In the SelectingWaypointState state, a node is creating for each Planning
     * Waypoint. The "add" and "remove" buttons are disabled for all the
     * spaces and existing waypoints. The space were the waypoint currently
     * be constructed will be added contains a "cancel" button to abort the
     * addition of the waypoint.
     */
    @Override
    public void refreshView() {
        super.refreshView();
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
        for (PlanningDetailsItem pdi : itemNodes) {
            pdi.setDisplayAddButton(false);
            pdi.setDisplayRemoveButton(false);
        }
        itemNodes.get(this.index).setDisplayAddButton(false);
        itemNodes.get(this.index).setDisplayCancelAddButton(true);
        itemNodes.get(this.index).setDisplayDataBefore(false);
    }
}
