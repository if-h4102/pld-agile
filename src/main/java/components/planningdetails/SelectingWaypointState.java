package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.*;
import org.jetbrains.annotations.NotNull;
import services.map.IMapService;

public class SelectingWaypointState extends PlanningDetailsState {
    private final int index;

    public SelectingWaypointState(PlanningDetails planningDetails, int index) {
        super(planningDetails);
        this.index = index;
    }

    public IPlanningDetailsState enterState(IPlanningDetailsState previousState) {
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
    public IPlanningDetailsState onPlanningWaypointsChange(ListChangeListener.Change<? extends AbstractWaypoint> listChange) {
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
    public IPlanningDetailsState onAddWaypointAction(AddWaypointAction action) {
        return this;
    }
}
