package components.application;


import models.CityMap;
import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Planning;
import services.tsp.AbstractTspSolver;
import services.tsp.BasicTspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    public void enterState(MainController mainController) {
        System.out.println("Computing...");
        AbstractTspSolver solver = new BasicTspSolver();
        DeliveryRequest dg = mainController.getDeliveryRequest();
        CityMap cm = mainController.getCityMap();
        DeliveryGraph deliveryGraph = cm.computeDeliveryGraph(dg);
        Planning planning = solver.solve(deliveryGraph);
        System.out.println(planning);
        mainController.setPlanning(planning);
        System.out.println("Computed");
    }

    public void leaveState(MainController mainController) {

    }
}
