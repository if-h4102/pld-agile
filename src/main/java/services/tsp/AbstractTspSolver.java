package services.tsp;

import models.AbstractWayPoint;
import models.DeliveryGraph;
import models.Planning;

public abstract class AbstractTspSolver {

    protected AbstractWayPoint[] bestSolution;
    protected int bestSolutionCost;

    /**
     * Solve the TSP problem from a DeAbstractWayPointiveryGraph.
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    public abstract Planning solve(DeliveryGraph graph);
}
