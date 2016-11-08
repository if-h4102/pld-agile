package components.application;

import models.Intersection;

import java.util.concurrent.CompletableFuture;

public class ComputedPlanningState extends WaitOpenDeliveryRequestState {
    final private MainController mainController;

    ComputedPlanningState(MainController mainController) {
        this.mainController = mainController;
    }

    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onPromptIntersection(MainController mainController, CompletableFuture<Intersection> future) {
        return new SelectingIntersectionState(this.mainController, future);
    }

    public MainControllerState onComputePlanningButtonAction(MainController mainController) {
        System.out.println("Computed");
        return this;
    }
}
