package services.tsp;

import com.google.java.contract.Requires;
import models.AbstractWaypoint;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class WaypointIterator implements Iterator<AbstractWaypoint> {

    private AbstractWaypoint[] waypoints;
    private int waypointsLeft;

    /**
     * Construct an iterator on the given structure.
     * @param points the collection in which you want to iterate.
     * @param costs the costs associated with points.
     */
    public WaypointIterator(Collection<AbstractWaypoint> points, Map<AbstractWaypoint, Integer> costs, int seenCost, int startTime){
        //call basic constructor
        this(points);
        //sort array the bigger cost first as the table is read end first
        Arrays.sort(waypoints, (a,b) -> costs.get(b).compareTo(costs.get(a) - (a.canBePassed(startTime+seenCost+costs.get(a)) ? 86400 : 0)));
    }
    /**
     * Construct an iterator on the given structure.
     * @param points the collection in which you want to iterate.
     * @param costs the costs associated with points.
     */
    public WaypointIterator(Collection<AbstractWaypoint> points, Map<AbstractWaypoint, Integer> costs){
        //call basic constructor
        this(points);
        //sort array the bigger cost first as the table is read end first
        Arrays.sort(waypoints, (a,b) -> costs.get(b).compareTo(costs.get(a)));
    }
    /**
     * Construct an iterator on the given structure.
     * @param points the collection in which you want to iterate.
     */
    public WaypointIterator(Collection<AbstractWaypoint> points) {
        this.waypoints = new AbstractWaypoint[points.size()];
        this.waypointsLeft = 0;
        for(AbstractWaypoint point: points) {
            this.waypoints[waypointsLeft++] = point;
        }
    }




    /**
     *
     * @return true only if there is a Waypoint left in the collection.
     */
    @Override
    public boolean hasNext() {
        return this.waypointsLeft > 0;
    }

    /**
     *
     * @return the next item in the collection.
     */
    @Override
    @Requires("this.hasNext()")
    public AbstractWaypoint next() {
        return this.waypoints[--waypointsLeft];
    }
}
