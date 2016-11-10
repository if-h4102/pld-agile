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

public class SelectingWaypointState extends PlanningDetailsState {
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

    @Override
    @NotNull
    public IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends PlanningWaypoint> listChange) {
        // Prevent
        return this;
    }

    @Override
    @NotNull
    public IPlanningDetailsState onAddWaypoint(AddWaypointAction action) {
        return this;
    }

    @Override
    @NotNull
    public IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action) {
        return new DefaultState(this.planningDetails);
    }

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
