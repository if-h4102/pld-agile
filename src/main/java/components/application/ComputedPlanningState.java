package components.application;

public class ComputedPlanningState extends WaitOpenDeliveryRequestState {
    final private MainController mainController;

    ComputedPlanningState(MainController mainController) {
        this.mainController = mainController;
    }

    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onComputePlanningButtonAction(MainController mainController) {
        System.out.println("Computed");
        return this;
    }
}
