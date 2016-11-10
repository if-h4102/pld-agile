package components.application;

import components.mapcanvas.IntersectionSelectionEvent;
import models.Intersection;
import java.util.concurrent.CompletableFuture;

/**
 * This class is the default state, of the design pattern state. It defines all events possible, but do nothing on them.
 */
public abstract class MainControllerState {
    final protected MainController mainController;
    
    /**
     * @param mainController The main controller of the application, which will get all events.
     */
    public MainControllerState(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * This method is called when the application enter in this state.
     */
    public void enterState() {

    }

    /**
     * This method is called when the application leave this state.
     */
    public void leaveState() {

    }

    /**
     * This method is called when the user click on the button "open city map".
     * @return The new state of the application.
     */
    public MainControllerState onOpenCityMapButtonAction() {
        return this;
    }

    /**
     * This method is called when the user click on the button "open delivery request".
     * @return The new state of the application.
     */
    public MainControllerState onOpenDeliveryRequestButtonAction() {
        return this;
    }

    /**
     * This method is called when the user click on the button "compute".
     * @return The new state of the application.
     */
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
