package components.application;


import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import services.tsp.AbstractTspSolver;
import services.tsp.BasicBoundTspSolver;

import java.util.concurrent.CompletableFuture;

public class SelectingIntersectionState extends WaitOpenDeliveryRequestState {
    final private CompletableFuture<Intersection> future;

    public SelectingIntersectionState(CompletableFuture<Intersection> future) {
        this.future = future;
    }

    public MainControllerState onIntersectionSelection(MainController mainController, Intersection intersection) {
        this.future.complete(intersection);
        return new ReadyState();
    }
}
