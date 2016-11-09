package components.application;


import models.DeliveryGraph;
import models.DeliveryRequest;
import models.Planning;
import services.pdf.planningPrinter;
import services.tsp.BasicBoundTspSolver;
import services.tsp.ThreadedTspSolver;

public class ComputingPlanningState extends WaitOpenDeliveryRequestState {
    private Thread solverThread;
    private long beforeDijkstraTime;
    private long beforeTspTime;
    private long completionTime;

    ComputingPlanningState(MainController mainController) {
        super(mainController);
    }


    @Override
    public void enterState() {

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

        planningPrinter.generatePdfFromPlanning(planning,"planning.pdf");

        mainController.applyState(new ComputedPlanningState(mainController));
    }

    @Override
    public void leaveState() {
        this.completionTime = System.nanoTime();
        long fullDuration = (this.completionTime - this.beforeDijkstraTime) / 1000000;
        long tspDuration = (this.completionTime - this.beforeTspTime) / 1000000;
        long dijkstraDuration = (this.beforeTspTime - this.beforeDijkstraTime) / 1000000;
        System.out.println("Computed in " + (fullDuration) + " ms (tsp: " + (tspDuration) + " ms, dijktra: " + (dijkstraDuration) + " ms)");
    }

    @Override
    public MainControllerState onComputePlanningButtonAction() {
        System.out.println("Computing");
        return this;
    }
}
