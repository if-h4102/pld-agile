package services.tsp;

import models.AbstractWaypoint;
import models.DeliveryGraph;
import models.Planning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractTspSolver {

    protected AbstractWaypoint[] bestSolution;
    protected Map<AbstractWaypoint, Integer> bestSolutionWaitingTime;
    protected int bestSolutionCost;

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     *
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    public abstract Planning solve(DeliveryGraph graph);

    protected abstract Iterator<AbstractWaypoint> iterator(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                                           Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs,
                                                           Map<AbstractWaypoint, Integer> deliveryDurations,
                                                           int seenCost);

    protected abstract int bound(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                 Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs, Map<AbstractWaypoint, Integer> deliveryDurations,
                                 int seenCost);
}
