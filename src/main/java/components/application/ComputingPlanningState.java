package components.application;


import java.lang.management.PlatformLoggingMXBean;
import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Planning;
import services.tsp.BasicBoundTspSolver;
import services.tsp.TspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState implements TspCompletedListener{
    private long beforeDijkstraTime;
    private long beforeTspTime;
    private long completionTime;
    private TspSolver tspSolver;

    ComputingPlanningState(MainController mainController) {
        super(mainController);
    }


    @Override
    public void enterState() {

        DeliveryRequest deliveryRequest = mainController.getDeliveryRequest();

        this.beforeDijkstraTime = System.nanoTime();
        DeliveryGraph deliveryGraph = deliveryRequest.computeDeliveryGraph();

        this.beforeTspTime = System.nanoTime();
        tspSolver = new BasicBoundTspSolver();
        tspSolver.setDeliveryGraph(deliveryGraph);
        tspSolver.addListener(this);
        tspSolver.start();
        mainController.setTextToComputePlanningButton("Interrupt");
        
//        try {
//            this.solverThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Planning planning = tspSolver.getBestPlanning();
//        mainController.setPlanning(planning);
//
//        mainController.applyState(new ComputedPlanningState(mainController));
    }

    @Override
    public void leaveState() {
        this.completionTime = System.nanoTime();
        long fullDuration = (this.completionTime - this.beforeDijkstraTime) / 1000000;
        long tspDuration = (this.completionTime - this.beforeTspTime) / 1000000;
        long dijkstraDuration = (this.beforeTspTime - this.beforeDijkstraTime) / 1000000;
        System.out.println("Computed in " + (fullDuration) + " ms (tsp: " + (tspDuration) + " ms, dijktra: " + (dijkstraDuration) + " ms)");
        mainController.setTextToComputePlanningButton("Computing");
    }

    @Override
    public MainControllerState onComputePlanningButtonAction() {
        if (tspSolver != null)
            tspSolver.stopComputing();
        
        return new ReadyToComputeState(mainController);
    }


    @Override
    public void notifyOfTspComplete(Planning bestPlanning) {
        mainController.setPlanning(bestPlanning);
        mainController.applyState(new ComputedPlanningState(mainController));
    }
}
