package components.application;

import components.mapcanvas.IntersectionSelectionEvent;
import models.Intersection;
import java.util.concurrent.CompletableFuture;

public abstract class MainControllerState {
    final protected MainController mainController;
    
    public MainControllerState(MainController mainController) {
        this.mainController = mainController;
    }

    public void enterState() {

    }

    public void leaveState() {

    }

    public MainControllerState onOpenCityMapButtonAction() {
        return this;
    }

    public MainControllerState onOpenDeliveryRequestButtonAction() {
        return this;
    }

    public MainControllerState onComputePlanningButtonAction() {
        return this;
    }

    @SuppressWarnings("unused")
    public MainControllerState onPromptIntersection(CompletableFuture<Intersection> future) {
        return this;
    }

    @SuppressWarnings("unused")
    public MainControllerState onIntersectionSelection(IntersectionSelectionEvent event) {
        return this;
    }
}
