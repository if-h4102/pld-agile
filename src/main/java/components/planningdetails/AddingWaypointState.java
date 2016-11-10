package components.planningdetails;

import components.events.CancelAddWaypointAction;
import components.events.SaveDeliveryAddress;
import components.waypointcard.EditableDeliveryAddressCard;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import models.DeliveryAddress;
import models.Intersection;
import models.Planning;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The AddingWaypointState state is used when the user is adding a new waypoint
 * and is inputting the delivery duration and the time constraints (it is the
 * state that comes after he choose an address with the map).
 */
public class AddingWaypointState extends PlanningDetailsState {
    /**
     * The index (order in the planning) where to insert the waypoint once
     * it is created.
     */
    private final int index;

    /**
     * The component used to input the information about the new waypoint.
     * (It is a form.)
     */
    private final EditableDeliveryAddressCard editableCard;

    /**
     * The address where the user wants to add the new waypoint.
     */
    private final Intersection intersection;

    /**
     * @param planningDetails The component to attach to this state.
     * @param index           The index (order in the planning) where to insert the waypoint once
     *                        it is created.
     * @param intersection    The address where the user wants to add the new waypoint.
     */
    public AddingWaypointState(@NotNull PlanningDetails planningDetails, int index, @NotNull Intersection intersection) {
        super(planningDetails);
        this.index = index;
        this.intersection = intersection;
        this.editableCard = new EditableDeliveryAddressCard();
    }

    /**
     * This method applies the view corresponding to the AddingWaypointState
     * state.
     * The "add" and "remove" butonns of the existing waypoints are disabled.
     * The form is located at the position where the new waypoint would be once
     * created.
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
        itemNodes.get(this.index).setDisplayDataBefore(false);
    }

    @Override
    @NotNull
    public IPlanningDetailsState enterState(@NotNull IPlanningDetailsState previousState) {
        super.enterState(previousState);
        ObservableList<Node> nodes = this.planningDetails.planningDetailsVBox.getChildren();
        DeliveryAddress tmpDeliveryAddress = new DeliveryAddress(intersection, 0);
        EditableDeliveryAddressCard editable = new EditableDeliveryAddressCard();
        editable.setWaypoint(tmpDeliveryAddress);

        nodes.add(this.index, editable);
        return this;
    }

    /**
     * Handle the "save" user action.
     * <p>
     * It simply adds the newly created waypoint to the planning at the chosen
     * index.
     *
     * @param event An object representing the saveDeliveryAddress user action.
     * @return A instance of DefaultState for this component.
     */
    @Override
    @NotNull
    public IPlanningDetailsState onSaveNewWaypoint(@NotNull SaveDeliveryAddress event) {
        DeliveryAddress deliveryAddress = event.getDeliveryAddress();
        Planning planning = this.planningDetails.getPlanning();
        // Hack to add the waypoint in the main controller instead of here.
        event.setIndex(this.index);

        return new DefaultState(this.planningDetails);
    }

    /**
     * Restore the default state upon cancellation.
     * <p>
     * There is no button in GUI to trigger this action currently.
     *
     * @param action The object representing the user action.
     * @return A DefaultState state for the component.
     */
    @Override
    @NotNull
    public IPlanningDetailsState onCancelAddWaypointAction(@NotNull CancelAddWaypointAction action) {
        return new DefaultState(this.planningDetails);
    }
}
