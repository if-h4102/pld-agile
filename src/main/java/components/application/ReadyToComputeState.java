package components.application;

/**
 * This class represents the state when the city map and delivery request are loaded, but the user has yet to click on "Compute"
 */
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
