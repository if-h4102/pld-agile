package components.application;

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
}
