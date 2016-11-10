package services.tsp;

import models.AbstractWaypoint;

import java.util.ArrayList;
import java.util.Map;

public class BasicBoundTspSolver extends TspSolver{
    /**
     * Bound using the sum of min cost per unseen node (including warehouse) plus the cost of the node. Complexity: O(n²) with n the number
     * of unseen node.
     * Min cost is defined as average of the two min cost (with only one cost per linked node, i.e. the to min cost can't
     * link the two same points, as we know that the next node can't be the same the previous)
     * A one day malus is added by point that cannot be reached on time.
     *
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
     * @return a min bound of the cost to see each unseen node
     */
    @Override
    protected int bound(AbstractWaypoint lastSeenNode, ArrayList<AbstractWaypoint> unseen,
                        Map<AbstractWaypoint, Map<AbstractWaypoint, Integer>> costs, Map<AbstractWaypoint, Integer> deliveryDurations, int seenCost) {

        // init bound
        int bound;
        {
            // first get the min half cost out of lastSeenNode
            int minCost = Integer.MAX_VALUE;
            for (AbstractWaypoint possibleDestination : unseen) {
                int cost = costs.get(lastSeenNode).get(possibleDestination);
                if (minCost >= cost) {
                    minCost = cost;
                }
            }
            bound = minCost / 2;
            // then get the min half cost to the warehouse
            minCost = Integer.MAX_VALUE;
            for (AbstractWaypoint possibleDestination : unseen) {
                int cost = costs.get(possibleDestination).get(this.startPoint);
                if (minCost >= cost) {
                    minCost = cost;
                }
            }
            bound += minCost / 2;
        }

        // add to bound the average of the two min cost of each unseen node and is duration
        for (AbstractWaypoint waypoint : unseen) {
            // linkCosts = costs.get(waypoint);
            int minCost = costs.get(waypoint).get(this.startPoint); // init using the cost to the warehouse
            int cost = costs.get(lastSeenNode).get(waypoint); // first check the cost from the last seen node
            int secondMinCost = cost;
            if (minCost >= cost) {
                secondMinCost = minCost;
                minCost = cost;
            }
            // find the min cost and the second min cost
            for (AbstractWaypoint possibleDestination : unseen) {
                if (waypoint != possibleDestination) {
                    // as we cannot go from and to the same node we take the min of the two possibilities
                    int costFrom = costs.get(waypoint).get(possibleDestination);
                    int costTo = costs.get(possibleDestination).get(waypoint);
                    cost = Math.min(costFrom, costTo);
                    if (minCost >= cost) {
                        secondMinCost = minCost;
                        minCost = cost;
                    } else if (secondMinCost > cost) {
                        secondMinCost = cost;
                    }
                }
            }
            // add found values to bound
            bound += waypoint.getDuration();
            bound += (minCost + secondMinCost) / 2;

            // if endDeliveryTime is already passed, add a malus
            int deliveryFirstPossibleTime = startPoint.getTimeStart() + seenCost + costs.get(lastSeenNode).get(waypoint);
            if (deliveryFirstPossibleTime + waypoint.getDuration() > waypoint.getTimeEnd())
                bound += 86400;
        }
        return bound; // Basic bound
    }
}
