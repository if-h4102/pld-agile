package services.tsp;

import models.DeliveryGraph;
import models.Planning;

public abstract class AbstractTspSolver {
    /**
     * Solve the TSP problem from a DeliveryGraph.
     *
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    public abstract Planning solve(DeliveryGraph graph);
}
