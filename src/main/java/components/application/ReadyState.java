package components.application;

public class ReadyState extends WaitOpenDeliveryRequestState {

    public ReadyState(MainController mainController) {
        super(mainController);
    }

    @Override
    public void enterState() {
        mainController.modifyComputePlanningButtonDisabledProperty(false);
    }

    @Override
    public void leaveState() {

    }

    @Override
    public MainControllerState onComputePlanningButtonAction() {
        return new ComputingPlanningState(mainController);
    }
}
