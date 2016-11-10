package components.application;

public class ReadyToComputeState extends WaitOpenDeliveryRequestState {

    public ReadyToComputeState(MainController mainController) {
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
