package controllers;


public class ReadyState extends WaitOpenDeliveryRequestState {
    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onCumputePlanningButtonAction(MainController mainController) {
        System.out.println("Computing Planning...");

        return this;
    }
}
