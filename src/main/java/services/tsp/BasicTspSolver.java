package services.tsp;

import models.DeliveryGraph;
import models.Planning;

public class BasicTspSolver extends AbstractTspSolver {
    /**
     * The constructor for a basic TSP solver.
     * It doesn't need anything for now.
     */
    BasicTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the TSP problem from a DeliveryGraph.
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    @Override
    public Planning solve(DeliveryGraph graph) {
        // TODO
        return null;
    }
}
