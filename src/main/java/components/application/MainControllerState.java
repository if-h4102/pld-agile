package components.application;

import components.mapcanvas.IntersectionSelectionEvent;
import models.Intersection;

import java.util.concurrent.CompletableFuture;

public abstract class MainControllerState {
    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onOpenCityMapButtonAction(MainController mainController) {
        return this;
    }

    public MainControllerState onOpenDeliveryRequestButtonAction(MainController mainController) {
        return this;
    }

    public MainControllerState onComputePlanningButtonAction(MainController mainController) {
        return this;
    }

    public MainControllerState onPromptIntersection(MainController mainController, CompletableFuture<Intersection> future) {
        return this;
    }

    public MainControllerState onIntersectionSelection(MainController mainController, IntersectionSelectionEvent event) {
        return this;
    }
}
