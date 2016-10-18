package services.tsp;

import models.*;

import java.util.*;

public class BasicTspSolver extends AbstractTspSolver {
    /**
     * The constructor for a basic TSP solver.
     * It doesn't need anything for now.
     */
    public BasicTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the TSP problem for the given DeliveryGraph.
     * @param graph The (complete) graph representing all delivery points and the warehouse.
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
        // Let's say that the first seen node is the first one of the graph
        seen.add(graph.iterator().next().getKey());
        unseen.remove(graph.iterator().next().getKey());
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
        branchAndBound(graph.iterator().next().getKey(), unseen, seen, 0, costs, deliveryDurations);
        // Construct Planning based on the previous result
        List<Route> routes = new ArrayList<>(graph.size());
        for(int i = 0; i < graph.size(); i++) {
            routes.add(graph.getRoute(this.bestSolution[i], this.bestSolution[(i+1)%graph.size()]));
        }
        return new Planning(routes);
    }

    /**
     * Basic branch an bound algorithm
     * @param lastSeenNode the last explored node.
     * @param unseen all nodes not explored yet.
     * @param seen all nodes already explored.
     * @param seenCost the cost of all explored nodes.
     * @param costs the cost of the path between each node.
     * @param deliveryDurations the delivery duration of each node.
     */
    private void branchAndBound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen, ArrayList<AbstractWayPoint> seen, int seenCost, Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations) {
        if (unseen.size() == 0) {
            // All nodes have been seen
            // Just complete the circuit...
            seenCost += costs.get(lastSeenNode).get(seen.get(0));   // TODO: is that the right cost ?
            // ...and check if this was a better solution
            if (seenCost < this.bestSolutionCost) {
                // Indeed it was ! Let's update the previous one
                seen.toArray(this.bestSolution);
                this.bestSolutionCost = seenCost;
            }
        } else if (seenCost + this.bound(lastSeenNode, unseen, costs, deliveryDurations) < this.bestSolutionCost) {
            // We have a great candidate !
            Iterator<AbstractWayPoint> it = this.iterator(lastSeenNode, unseen, costs, deliveryDurations);
            while (it.hasNext()){
                AbstractWayPoint nextNode = it.next();
                seen.add(nextNode);
                unseen.remove(nextNode);
                branchAndBound(nextNode, unseen, seen, seenCost + costs.get(lastSeenNode).get(nextNode) + deliveryDurations.get(nextNode), costs, deliveryDurations);
                unseen.add(nextNode);
                seen.remove(nextNode);
            }
        }
    }

    /**
     * The most basic bounding algorithm.
     * @param lastSeenNode
     * @param unseen
     * @param costs
     * @param deliveryDurations
     * @return
     */
    private int bound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen, Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations) {
        // TODO: improve that, or is this enough for this solver ?
        return 0;   // The most basic bound
    }

    /**
     * Return a very basic iterator on the given collection.
     * @param lastSeenNode
     * @param unseen the collection in which you want to iterate.
     * @param costs
     * @param deliveryDurations
     * @return
     */
    private Iterator<AbstractWayPoint> iterator(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen, Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations) {
        // NOTE: for the moment, this just returns a basic iterator,
        //       which won't look for the best node to return.
        return new WayPointIterator(unseen);
    }
}
