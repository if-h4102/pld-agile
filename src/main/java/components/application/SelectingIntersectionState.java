package components.application;


import components.mapcanvas.IntersectionSelectionEvent;
import models.Intersection;

import java.util.concurrent.CompletableFuture;
// TODO class to comment
public class SelectingIntersectionState extends WaitOpenDeliveryRequestState {;
    final private CompletableFuture<Intersection> future;

    public SelectingIntersectionState(MainController mainController, CompletableFuture<Intersection> future) {
        super(mainController);
        this.future = future;
    }

    @Override
    public void enterState() {

    }

    @Override
    public void leaveState() {

    }

    @Override
    public MainControllerState onIntersectionSelection(IntersectionSelectionEvent event) {
        Intersection intersection = event.getIntersection();
        if (intersection == null) {
            return this;
        }
        mainController.modifyComputePlanningButtonDisabledProperty(true);
        this.future.complete(intersection);
        return new ComputedPlanningState(this.mainController);
    }
}
