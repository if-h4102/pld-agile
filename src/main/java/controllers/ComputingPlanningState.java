package controllers;


public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    public void enterState(MainController mainController) {
        System.out.println("Computing...");
    }

    public void leaveState(MainController mainController) {

    }
}
