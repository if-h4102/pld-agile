package components.application;


import models.*;
import services.pdf.planningPrinter;
import services.tsp.AbstractTspSolver;
import services.tsp.BasicBoundTspSolver;
import services.tsp.BasicTspSolver;
import services.tsp.TimeConstraintBoundTspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    public void enterState(MainController mainController) {
        System.out.println("Computing...");
        AbstractTspSolver solver = new TimeConstraintBoundTspSolver();
//        AbstractTspSolver solver = new BasicBoundTspSolver();
//        AbstractTspSolver solver = new BasicTspSolver();
        DeliveryRequest deliveryRequest = mainController.getDeliveryRequest();
        long beforeDijkstra = System.nanoTime();
        DeliveryGraph deliveryGraph = deliveryRequest.computeDeliveryGraph();
        long startTime = System.nanoTime();
        Planning planning = solver.solve(deliveryGraph);
        long endTime = System.nanoTime();
        System.out.println(planning);
        mainController.setPlanning(planning);
        System.out.println("Computed in "+((endTime-beforeDijkstra)/1000000)+" ms (tsp: "+((endTime-startTime)/1000000)+" ms, dijktra: "+((startTime-beforeDijkstra)/1000000)+" ms)");
        //TODO: call pdf generator at a better place !!!
        planningPrinter.generatePdfFromPlanning(planning,"");
    }

    public void leaveState(MainController mainController) {

    }
}
