package services.tsp;

import models.DeliveryGraph;
import models.Planning;
import models.Route;

import java.util.ArrayList;
import java.util.List;

public class BasicTspSolver extends AbstractTspSolver {
    /**
     * The constructor for a basic TSP solver.
     * It doesn't need anything for now.
     */
    BasicTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     * IMPORTANT: we need to assume that nodes have an ID between 0 and graph.size().
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    @Override
    public Planning solve(DeliveryGraph graph) {
        // Initialize solver parameters
        this.bestSolutionCost = Integer.MAX_VALUE;
        this.bestSolution = new Integer[graph.size()];
        // Initialize unseen nodes
        ArrayList<Integer> unseen = new ArrayList<Integer>();           // TODO
        for (int i=1; i<graph.size(); i++) unseen.add(i);               // TODO
        // Initialize seen nodes
        ArrayList<Integer> seen = new ArrayList<Integer>(graph.size()); // TODO
        seen.add(0); // The first seen node is the 0th                  // TODO
        // Get the cost for all routes
        int[][] costs = new int[graph.size()][graph.size()];
        for(int i = 0; i < graph.size(); i++) {
            for(int j = 0; j < graph.size(); j++) {
                costs[i][j] = graph.getRoute(i, j).getDuration();
            }
        }
        // Compute solution
        branchAndBound(0, unseen, seen, 0, costs);
        // Construct Planning based on the previous result
        List<Route> routes = new ArrayList<>(graph.size());
        for(int i = 0; i < graph.size(); i++) {
            routes.add(graph.getRoute(this.bestSolution[i], this.bestSolution[(i+1)%graph.size()]));
        }
        return new Planning(routes);
    }

    private void branchAndBound(int lastSeenNodeId, ArrayList<Integer> unseen, ArrayList<Integer> seen, int seenCost, int[][] costs) {

    }
}
