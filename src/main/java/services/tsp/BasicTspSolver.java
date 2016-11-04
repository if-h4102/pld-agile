package services.tsp;

import models.*;
import java.util.*;

public class BasicTspSolver extends AbstractTspSolver {

    protected AbstractWayPoint startPoint;

    /**
     * Branch and bound const (lossy branch cutting)
     */
    private final int MIN_EXPLORATION_WIDTH = 3; //min number of route tried from a given point
    private final int EXPLORATION_WIDTH_DIVISOR = 1; //divisor of the total number of accessible points
        //finale width exploration is: MIN_EXPLORATION_WIDTH + (number of accessible points) / EXPLORATION_WIDTH_DIVISOR
        //set to 1 to disable width exploration limitation
    private final int MAX_NUMBER_OF_MIN_COST = 1000; //branch cut if cost of currant branch is bigger than this constant
                                                  //multiply by the minimum cost to reach an accessible point.
        //set to 1000 or a an other big value to disable, Interger.MAX_VALUE is too big and has overflow problems
    /**
     * The constructor for a basic TSP solver. It doesn't need anything for now.
     */
    public BasicTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     *
     * @param graph
     *            The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    @Override
    public Planning solve(DeliveryGraph graph) {
        // Initialize solver parameters
        this.bestSolutionCost = Integer.MAX_VALUE;
        this.bestSolution = new AbstractWayPoint[graph.size()];
        // Initialize unseen nodes
        ArrayList<AbstractWayPoint> unseen = graph.getNodes();
        // Initialize seen nodes
        ArrayList<AbstractWayPoint> seen = new ArrayList<AbstractWayPoint>(graph.size());
        // Let's say that the starting point is the first warehouse found
        for (AbstractWayPoint point : unseen) {
            if (point instanceof Warehouse) {
                startPoint = point;
                break;
            }
        }
        seen.add(startPoint);
        unseen.remove(startPoint);

        // Get the cost for all routes
        Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs = new HashMap<>();
        graph.iterator().forEachRemaining((startPoint) -> {
            HashMap<AbstractWayPoint, Integer> costsFromStartPoint = new HashMap<>();
            startPoint.getValue().entrySet().forEach((endPoint) -> {
                costsFromStartPoint.put(endPoint.getKey(), endPoint.getValue().getDuration());
            });
            costs.put(startPoint.getKey(), costsFromStartPoint);
        });

        // Get the time needed to deliver each way point
        Map<AbstractWayPoint, Integer> deliveryDurations = graph.getDeliveryDurations();
        // Compute solution
        branchAndBound(startPoint, unseen, seen, 0, costs, deliveryDurations);
        // Construct Planning based on the previous result
        List<Route> routes = new ArrayList<>(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            routes.add(graph.getRoute(this.bestSolution[i], this.bestSolution[(i + 1) % graph.size()]));
        }
        return new Planning(routes);
    }

    /**
     * Basic branch an bound algorithm
     *
     * @param lastSeenNode
     *            the last explored node.
     * @param unseen
     *            all nodes not explored yet.
     * @param seen
     *            all nodes already explored.
     * @param seenCost
     *            the cost of all explored nodes.
     * @param costs
     *            the cost of the path between each node.
     * @param deliveryDurations
     *            the delivery duration of each node.
     */
    private void branchAndBound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                                ArrayList<AbstractWayPoint> seen, int seenCost,
                                Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs,
                                Map<AbstractWayPoint, Integer> deliveryDurations) {
        if (unseen.size() == 0) {
            // All nodes have been seen
            // Just complete the circuit...
            seenCost += costs.get(lastSeenNode).get(this.startPoint) + deliveryDurations.get(startPoint);
            // ...and check if this was a better solution
            if (seenCost < this.bestSolutionCost) {
                // Indeed it was ! Let's update the previous one
                seen.toArray(this.bestSolution);
                this.bestSolutionCost = seenCost;
            }
        } //else if the estimation of time left show possible new best solution
        else if (seenCost + this.bound(lastSeenNode, unseen, costs, deliveryDurations,seenCost) < this.bestSolutionCost) {
            // We have a great candidate !
            Iterator<AbstractWayPoint> it = this.iterator(lastSeenNode, unseen, costs, deliveryDurations,seenCost);
            int i=0;
            int minCost = Integer.MAX_VALUE;
            while (it.hasNext() && i++ < unseen.size()/EXPLORATION_WIDTH_DIVISOR+MIN_EXPLORATION_WIDTH) {
                AbstractWayPoint nextNode = it.next();
                seen.add(nextNode);
                unseen.remove(nextNode);
                int costRouteAndDelivery = costs.get(lastSeenNode).get(nextNode);
                if(i==1)
                    minCost = costRouteAndDelivery;
                else if(costRouteAndDelivery > MAX_NUMBER_OF_MIN_COST*minCost)
                    break; //if currant cost is bigger than two time the min value cut the currant branch.
                //if we can pass to the selected node
                if(!nextNode.canBePassed(this.startPoint.getDeliveryTimeStart()+seenCost+costRouteAndDelivery)){
                    //add a one day cost (longer than the max delivery time)
                    costRouteAndDelivery += 86400;
                }
                costRouteAndDelivery += deliveryDurations.get(nextNode);
                branchAndBound(nextNode, unseen, seen, seenCost + costRouteAndDelivery, costs, deliveryDurations);
                unseen.add(nextNode);
                seen.remove(nextNode);
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
    protected int bound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                        Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs,
                        Map<AbstractWayPoint, Integer> deliveryDurations,
                        int seenCost) {
        // TODO: improve that, or is this enough for this solver ?
        return 0; // The most basic bound
    }

    /**
     * Return a very basic iterator on the given collection.
     *
     * @param lastSeenNode
     * @param unseen
     *            the collection in which you want to iterate.
     * @param costs
     * @param deliveryDurations
     * @return
     */
    @Override
    protected Iterator<AbstractWayPoint> iterator(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                                                  Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs,
                                                  Map<AbstractWayPoint, Integer> deliveryDurations,
                                                  int seenCost) {
        // NOTE: for the moment, this just returns a basic iterator,
        // which won't look for the best node to return.
        return new WayPointIterator(unseen, costs.get(lastSeenNode));
    }
}
