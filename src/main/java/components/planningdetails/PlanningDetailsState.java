package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.CancelAddWaypointAction;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.AbstractWaypoint;
import models.Planning;
import models.PlanningWaypoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a basis to build the states of the planning details.
 * It implements most of the IPlanningDetailsState interface.
 */
public abstract class PlanningDetailsState implements IPlanningDetailsState {
    /**
     * A reference to the component controlled by this state object.
     */
    @NotNull
    protected final PlanningDetails planningDetails;

    /**
     * Creates a new PlanningDetailsState and binds it to the component to control.
     *
     * @param planningDetails The planning managed by this state object.
     */
    public PlanningDetailsState(@NotNull PlanningDetails planningDetails) {
        this.planningDetails = planningDetails;
    }

    /**
     * This default method simply refreshes the view when entering to this
     * state.
     *
     * @param previousState A reference to the previous state of the component.
     * @return This state.
     */
    @NotNull
    public IPlanningDetailsState enterState(@NotNull IPlanningDetailsState previousState) {
        this.refreshView();
        return this;
    }

    /**
     * This basic implementation does nothing, extend it to perform special
     * actions when leaving this state.
     *
     * @param nextState A reference to the planned next state of the component.
     * @return The provided next state.
     */
    @NotNull
    public IPlanningDetailsState leaveState(@NotNull IPlanningDetailsState nextState) {
        return nextState;
    }

    @NotNull
    public IPlanningDetailsState onSaveNewWaypoint(@NotNull SaveDeliveryAddress action) {
        return this;
    }

    @NotNull
    public IPlanningDetailsState onAddWaypoint(@NotNull AddWaypointAction action) {
        return this;
    }

    /**
     * This basic implementation simply refreshes the view when list of
     * PlanningWaypoint's changes.
     *
     * @param listChange An object representing the changes that occured in the list.
     * @return This state
     */
    @NotNull
    public IPlanningDetailsState onPlanningWaypointsChange(@NotNull ListChangeListener.Change<? extends PlanningWaypoint> listChange) {
        this.refreshView();
        return this;
    }

    /**
     *  This method refreshes the view and resets the current state of the list by
     *  returning a new DefaultState.
     *
     * @param observable The observable value wrapping the Planning object.
     * @param oldValue   The old value of the Planning object.
     * @param newValue   The new value of the Planning object.
     * @return A new DefaultState
     */
    @NotNull
    public IPlanningDetailsState onPlanningChange(@NotNull ObservableValue<? extends Planning> observable, @Nullable Planning oldValue, @Nullable Planning newValue) {
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
        return new DefaultState(this.planningDetails);
    }

    public IPlanningDetailsState onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue) {
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

    /**
     * Refreshes the view. It creates a PlanningDetailsItem components for each
     * PlanningWaypoint and attaches it to its value. They are created in the
     * order of the planning.
     * It also ensures that the warehouse is not removable and it hides the
     * parts of the "path" outside of the start and end.
     */
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
