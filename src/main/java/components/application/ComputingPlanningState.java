package components.application;


import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Planning;
import services.tsp.BasicBoundTspSolver;
import services.tsp.ThreadedTspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    final private MainController mainController;
    private Thread solverThread;
    private long beforeDijkstraTime;
    private long beforeTspTime;
    private long completionTime;

    ComputingPlanningState(MainController mainController) {
        this.mainController = mainController;
    }


    public void enterState(MainController mainController) {
        DeliveryRequest deliveryRequest = mainController.getDeliveryRequest();

        this.beforeDijkstraTime = System.nanoTime();
        DeliveryGraph deliveryGraph = deliveryRequest.computeDeliveryGraph();

        this.beforeTspTime = System.nanoTime();
        ThreadedTspSolver tspSolver = new BasicBoundTspSolver();
        tspSolver.setDeliveryGraph(deliveryGraph);
        this.solverThread = new Thread(tspSolver);
        this.solverThread.start();

        try {
            this.solverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Planning planning = tspSolver.getBestPlanning();
        mainController.setPlanning(planning);
        mainController.applyState(new ComputedPlanningState(mainController));
    }

    public void leaveState(MainController mainController) {
        this.completionTime = System.nanoTime();
        long fullDuration = (this.completionTime - this.beforeDijkstraTime) / 1000000;
        long tspDuration = (this.completionTime - this.beforeTspTime) / 1000000;
        long dijkstraDuration = (this.beforeTspTime - this.beforeDijkstraTime) / 1000000;
        System.out.println("Computed in " + (fullDuration) + " ms (tsp: " + (tspDuration) + " ms, dijktra: " + (dijkstraDuration) + " ms)");
    }

    public MainControllerState onComputePlanningButtonAction(MainController mainController) {
        System.out.println("Computing");
        return this;
    }
}
