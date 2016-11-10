package services.tsp;

import models.AbstractWaypoint;
import models.DeliveryGraph;
import models.Planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import components.application.TspCompletedListener;

public abstract class AbstractThreadedTspSolver extends Thread{

    private Set<TspCompletedListener> listeners;

    protected AbstractWaypoint[] bestSolution;
    protected Map<AbstractWaypoint, Integer> bestSolutionWaitingTime;
    protected int bestSolutionCost;
    protected boolean stop;

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     *
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    public abstract Planning solve(DeliveryGraph graph);

    public AbstractThreadedTspSolver() {
        listeners = new HashSet<TspCompletedListener>();
        stop = false;
    }


    /**
     * @param lastSeenNode
     *            the last explored node.
     * @param unseen
     *            all nodes not explored yet.
     * @param seenCost
     *            the cost of all explored nodes.
     * @param costs
     *            the cost of the path between each node.
     * @param deliveryDurations
     *            the delivery duration of each node.
     * @return
     *            the best possible iterator
     */
    protected abstract Iterator<AbstractWaypoint> iterator(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                                           Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs,
                                                           Map<AbstractWaypoint, Integer> deliveryDurations,
                                                           int seenCost);
    /**
     * Basic branch an bound algorithm
     *
     * @param lastSeenNode
     *            the last explored node.
     * @param unseen
     *            all nodes not explored yet.
     * @param seenCost
     *            the cost of all explored nodes.
     * @param costs
     *            the cost of the path between each node.
     * @param deliveryDurations
     *            the delivery duration of each node.
     */
    protected abstract int bound(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                 Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs, Map<AbstractWaypoint, Integer> deliveryDurations,
                                 int seenCost);

    /**
     * Add the given listener to the list of listener
     * @param listener the listener to add to the list
     */
    public void addListener(TspCompletedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TspCompletedListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all the listener fom the list of a planning update
     * @param bestPlanning the new planning
     */
    protected void notifyListeners(Planning bestPlanning) {
        for (TspCompletedListener listener : listeners) {
            listener.notifyOfTspComplete(bestPlanning);
        }
        System.out.println(bestPlanning);
    }

    /**
     * Interrupt the currant computing on a valid state
     */
    public void stopComputing() {
        stop = true;
    }

}
