package components.application;


import models.CityMap;
import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Planning;
import services.tsp.AbstractTspSolver;
import services.tsp.BasicBoundTspSolver;
import services.tsp.BasicTspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    public void enterState(MainController mainController) {
        System.out.println("Computing...");
        AbstractTspSolver solver = new BasicBoundTspSolver();
//        AbstractTspSolver solver = new BasicTspSolver();
        DeliveryRequest dg = mainController.getDeliveryRequest();
        CityMap cm = mainController.getCityMap();
        long beforeDijkstra = System.nanoTime();
        DeliveryGraph deliveryGraph = cm.computeDeliveryGraph(dg);
        long startTime = System.nanoTime();
        Planning planning = solver.solve(deliveryGraph);
        long endTime = System.nanoTime();
        System.out.println(planning);
        mainController.setPlanning(planning);
        System.out.println("Computed in "+((endTime-beforeDijkstra)/1000000)+" ms (tsp: "+((endTime-startTime)/1000000)+" ms, dijktra: "+((startTime-beforeDijkstra)/1000000)+" ms)");
    }

    public void leaveState(MainController mainController) {

    }
}
