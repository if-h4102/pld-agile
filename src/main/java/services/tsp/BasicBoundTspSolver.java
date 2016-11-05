package services.tsp;

import models.AbstractWayPoint;
import models.Warehouse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class BasicBoundTspSolver extends BasicTspSolver {

    /**
     * Bound using the sum of min cost per unseen node (including warehouse) plus the cost of the node. Complexity: O(n²) with n the number
     * of unseen node.
     * 
     * @param lastSeenNode
     * @param unseen
     * @param costs
     * @param deliveryDurations
     * @return a min bound of the cost to see each unseen node
     */
    @Override
    protected int bound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
            Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations, int seenCost) {

        // init bound
        int bound;
        {
            // first get the min half cost out of lastSeenNode
            int minCost = Integer.MAX_VALUE;
            for (AbstractWayPoint possibleDestination : unseen) {
                int cost = costs.get(lastSeenNode).get(possibleDestination);
                if (minCost >= cost) {
                    minCost = cost;
                }
            }
            bound = minCost / 2;
            // then get the min half cost to the warehouse
            minCost = Integer.MAX_VALUE;
            for (AbstractWayPoint possibleDestination : unseen) {
                int cost = costs.get(possibleDestination).get(this.startPoint);
                if (minCost >= cost) {
                    minCost = cost;
                }
            }
            bound += minCost / 2;
        }

        // add to bound the average of the two min cost of each unseen node and is duration
        for (AbstractWayPoint wayPoint : unseen) {
            // linkCosts = costs.get(wayPoint);
            int minCost = costs.get(wayPoint).get(this.startPoint); // init using the cost to the warehouse
            int cost = costs.get(lastSeenNode).get(wayPoint); // first check the cost from the last seen node
            int secondMinCost = cost;
            if (minCost >= cost) {
                secondMinCost = minCost;
                minCost = cost;
            }
            // find the min cost and the second min cost
            for (AbstractWayPoint possibleDestination : unseen) {
                if (wayPoint != possibleDestination) {
                    // as we cannot go from and to the same node we take the min of the two possibilities
                    int costFrom = costs.get(wayPoint).get(possibleDestination);
                    int costTo = costs.get(possibleDestination).get(wayPoint);
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
            bound += wayPoint.getDuration();
            bound += (minCost + secondMinCost) / 2;

            // if endDeliveryTime is already passed, add a malus
            int deliveryFirstPossibleTime = startPoint.getTimeStart() + seenCost + costs.get(lastSeenNode).get(wayPoint);
            if (deliveryFirstPossibleTime + wayPoint.getDuration() > wayPoint.getTimeEnd())
                bound += 86400;
        }
        return bound; // Basic bound
    }
}
