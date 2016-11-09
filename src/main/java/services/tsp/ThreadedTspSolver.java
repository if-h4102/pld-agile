package services.tsp;

import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;
import models.DeliveryGraph;
import models.Planning;
import models.Warehouse;

import java.util.*;

public class ThreadedTspSolver extends AbstractTspSolver implements Runnable {

    protected Warehouse startPoint;
    private DeliveryGraph graph;
    private SimpleObjectProperty<Planning> bestPlanningObservable = new SimpleObjectProperty<>(this, "planning", null);
    private Planning bestPlanning;

    /**
     * Branch and bound const (lossy branch cutting)
     */
    private final int MIN_EXPLORATION_WIDTH = 3; //min number of route tried from a given point
    private final int EXPLORATION_WIDTH_DIVISOR = 1; //divisor of the total number of accessible points
    //final width exploration is: MIN_EXPLORATION_WIDTH + (number of accessible points) / EXPLORATION_WIDTH_DIVISOR
    //set to 1 to disable width exploration limitation
    private final int MAX_NUMBER_OF_MIN_COST = 1000; //branch cut if cost of currant branch is bigger than this constant
    //multiply by the minimum cost to reach an accessible point.
    //set to 1000 or a an other big value to disable, Interger.MAX_VALUE is too big and has overflow problems

    /**
     * The constructor for a basic TSP solver. It doesn't need anything for now.
     */
    public ThreadedTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the last set DeliveryGraph
     * the best result can be get using getBestPlanning(), during compute time it return the best found so far
     * once compute finished it's the best the algo can found.
     */
    @Override
    public void run() {
        if (graph == null) {
            System.err.println("Please set a deliveryGraph before trying to solve TSP");
            return;
        }
        // Initialize solver parameters
        this.bestSolutionCost = Integer.MAX_VALUE;
        this.bestSolution = new AbstractWaypoint[graph.size()];
        // Initialize unseen nodes
        ArrayList<AbstractWaypoint> unseen = graph.getNodes();
        // Initialize seen nodes
        ArrayList<AbstractWaypoint> seen = new ArrayList<AbstractWaypoint>(graph.size());
        // Initialize map of by node waiting time
        Map<AbstractWaypoint, Integer> wayPointWaitingTime = new HashMap<>();
        // Let's say that the starting point is the first warehouse found
        for (AbstractWaypoint point : unseen) {
            if (point instanceof Warehouse) {
                startPoint = (Warehouse) point;
                break;
            }
        }
        seen.add(startPoint);
        unseen.remove(startPoint);

        // Get the cost for all routes
        Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs = new HashMap<>();
        graph.iterator().forEachRemaining((startPoint) -> {
            HashMap<AbstractWaypoint, Integer> costsFromStartPoint = new HashMap<>();
            startPoint.getValue().entrySet().forEach((endPoint) -> {
                costsFromStartPoint.put(endPoint.getKey(), endPoint.getValue().getDuration());
            });
            costs.put(startPoint.getKey(), costsFromStartPoint);
        });

        // Get the time needed to deliver each way point
        Map<AbstractWaypoint, Integer> deliveryDurations = graph.getDeliveryDurations();
        // Compute solution
        branchAndBound(startPoint, unseen, seen, 0, costs, deliveryDurations, wayPointWaitingTime);

    }

    /**
     * Generate bestPlanning from bestSolution list, the city map, the waiting time and bestSolutionCost
     */
    private void updateBestPlanning() {
        this.bestPlanning = new Planning(graph.getCityMap(), Arrays.asList(this.bestSolution), bestSolutionWaitingTime, bestSolutionCost);
        this.bestPlanningObservable.setValue(bestPlanning);
    }

    /**
     * set the DeliveryGraph.
     *
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     */
    public void setDeliveryGraph(DeliveryGraph graph) {
        this.graph = graph;
        bestPlanning = null;
        bestPlanningObservable.setValue(bestPlanning);
    }

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     *
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    @Override
    public Planning solve(DeliveryGraph graph) {
        setDeliveryGraph(graph);
        run(); //solve in the current thread
        return bestPlanning;
    }

    /**
     * Basic branch an bound algorithm
     *
     * @param lastSeenNode      the last explored node.
     * @param unseen            all nodes not explored yet.
     * @param seen              all nodes already explored.
     * @param seenCost          the cost of all explored nodes.
     * @param costs             the cost of the path between each node.
     * @param deliveryDurations the delivery duration of each node.
     */
    private void branchAndBound(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                ArrayList<AbstractWaypoint> seen, int seenCost,
                                Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs,
                                Map<AbstractWaypoint, Integer> deliveryDurations,
                                Map<AbstractWaypoint, Integer> wayPointWaitingTime) {
        if (unseen.size() == 0) {
            // All nodes have been seen
            // Just complete the circuit...
            seenCost += costs.get(lastSeenNode).get(this.startPoint) + deliveryDurations.get(startPoint);
            // ...and check if this was a better solution
            if (seenCost < this.bestSolutionCost) {
                // Indeed it was ! Let's update the previous one
                seen.toArray(this.bestSolution);
                bestSolutionWaitingTime = new HashMap<>(wayPointWaitingTime);
                this.bestSolutionCost = seenCost;
                this.updateBestPlanning();
            }
        } //else if the estimation of time left show possible new best solution
        else if (seenCost + this.bound(lastSeenNode, unseen, costs, deliveryDurations, seenCost) < this.bestSolutionCost) {
            // We have a great candidate !
            Iterator<AbstractWaypoint> it = this.iterator(lastSeenNode, unseen, costs, deliveryDurations, seenCost);
            int i = 0;
            int minCost = Integer.MAX_VALUE;
            while (it.hasNext() && i++ < unseen.size() / EXPLORATION_WIDTH_DIVISOR + MIN_EXPLORATION_WIDTH) {
                AbstractWaypoint nextNode = it.next();
                seen.add(nextNode);
                unseen.remove(nextNode);
                int costRouteAndDelivery = costs.get(lastSeenNode).get(nextNode);
                if (i == 1)
                    minCost = costRouteAndDelivery;
                else if (costRouteAndDelivery > MAX_NUMBER_OF_MIN_COST * minCost)
                    break; //if currant cost is bigger than two time the min value cut the currant branch.
                //if we can pass to the selected node
                int arrivalTime = this.startPoint.getTimeStart() + seenCost + costRouteAndDelivery;
                arrivalTime %= 86400;
                if (!nextNode.canBePassed(arrivalTime)) {
                    if (arrivalTime < nextNode.getTimeStart()) {
                        //wait until opening of the delivery point
                        int waitingDuration = nextNode.getTimeStart() - arrivalTime;
                        costRouteAndDelivery += waitingDuration;
                        wayPointWaitingTime.put(nextNode, waitingDuration);
                    } else {
                        //add a one day cost (longer than the max delivery time)
                        costRouteAndDelivery += 86400;
                    }
                }
                costRouteAndDelivery += deliveryDurations.get(nextNode);
                branchAndBound(nextNode, unseen, seen, seenCost + costRouteAndDelivery, costs, deliveryDurations, wayPointWaitingTime);
                unseen.add(nextNode);
                seen.remove(nextNode);
                wayPointWaitingTime.remove(nextNode); //remove the possible waiting time
            }
        }
    }

    /**
     * The most basic bounding algorithm.
     *
     * @param lastSeenNode
     * @param unseen
     * @param costs
     * @param deliveryDurations
     * @return
     */
    @Override
    protected int bound(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                        Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs,
                        Map<AbstractWaypoint, Integer> deliveryDurations,
                        int seenCost) {
        return 0; // The most basic bound
    }

    /**
     * Return a very basic iterator on the given collection.
     *
     * @param lastSeenNode
     * @param unseen            the collection in which you want to iterate.
     * @param costs
     * @param deliveryDurations
     * @return
     */
    @Override
    protected Iterator<AbstractWaypoint> iterator(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                                                  Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs,
                                                  Map<AbstractWaypoint, Integer> deliveryDurations,
                                                  int seenCost) {
        // NOTE: for the moment, this just returns a basic iterator,
        // which won't look for the best node to return.
        return new WaypointIterator(unseen, costs.get(lastSeenNode));
    }

    /**
     * @return the currant best planning found
     * if computation is still in progress it's probably not the best the algo can found
     * (can be null if no computation had been run yet)
     */
    public Planning getBestPlanning() {
        return bestPlanning;
    }

    /**
     * @return an observable version of the best planning
     */
    public SimpleObjectProperty<Planning> bestPlanningProperty(){
        return this.bestPlanningObservable;
    }
}
