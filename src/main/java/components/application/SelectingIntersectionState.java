package components.application;


import components.mapcanvas.IntersectionSelectionEvent;
import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import services.tsp.AbstractTspSolver;
import services.tsp.BasicBoundTspSolver;

import java.util.concurrent.CompletableFuture;

public class SelectingIntersectionState extends WaitOpenDeliveryRequestState {
    final private MainController mainController;
    final private CompletableFuture<Intersection> future;

    public SelectingIntersectionState(MainController mainController, CompletableFuture<Intersection> future) {
        this.mainController = mainController;
        this.future = future;
    }

    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onIntersectionSelection(MainController mainController, IntersectionSelectionEvent event) {
        Intersection intersection = event.getIntersection();
        if (intersection == null) {
            return this;
        }
        this.future.complete(intersection);
        return new ComputedPlanningState(this.mainController);
    }
}
