package services.tsp;

import models.AbstractWayPoint;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by nicolas on 24/10/16.
 */
public class BasicBoundTspSolver extends BasicTspSolver{
    /**
     * Bound using the sum of min cost per unseen node (including warehouse)
     * plus the cost of the node.
     * Complexity: O(n²)
     * with n the number of unseen node.
     * @param lastSeenNode
     * @param unseen
     * @param costs
     * @param deliveryDurations
     * @return a min bound of the cost to see each unseen node
     */
    @Override
    protected int bound(AbstractWayPoint lastSeenNode, ArrayList<AbstractWayPoint> unseen,
                        Map<AbstractWayPoint, Map<AbstractWayPoint, Integer>> costs, Map<AbstractWayPoint, Integer> deliveryDurations) {
        int bound = 0;
        for (AbstractWayPoint wayPoint : unseen) {
            Map<AbstractWayPoint,Integer> linkCosts = costs.get(wayPoint);
            int minCost = linkCosts.get(this.startPoint); //init using the cost of the warehouse
            for (AbstractWayPoint destination : unseen){
                if(wayPoint != destination){ //if everything is reference it work
                    int cost = linkCosts.get(destination);
                    if(cost < minCost)
                        minCost = cost;
                }
            }
            bound += wayPoint.getDuration();
            bound += minCost;
        }
        return bound; // Basic bound
    }
}
