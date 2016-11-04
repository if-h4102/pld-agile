package services.tsp;

import models.AbstractWayPoint;
import models.DeliveryGraph;
import models.Planning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractTspSolver {

    protected AbstractWayPoint[] bestSolution;
    protected Map<AbstractWayPoint,Integer> bestSolutionWaitingTime;
    protected int bestSolutionCost;


    /**
     * Solve the TSP problem for the given DeliveryGraph.
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    public abstract Planning solve(DeliveryGraph graph);

    protected abstract Iterator<AbstractWayPoint> iterator(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                                                           Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs,
                                                           Map<AbstractWayPoint, Integer> deliveryDurations,
                                                           int seenCost);

    protected abstract int bound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                                 Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations,
                                 int seenCost);
}
