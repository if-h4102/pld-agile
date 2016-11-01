package services.tsp;

import com.google.java.contract.Requires;
import models.AbstractWayPoint;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class WayPointIterator implements Iterator<AbstractWayPoint> {

    private AbstractWayPoint[] wayPoints;
    private int wayPointsLeft;

    /**
     * Construct an iterator on the given structure.
     * @param points the collection in which you want to iterate.
     * @param costs the costs associated with points.
     */
    public WayPointIterator(Collection<AbstractWayPoint> points, Map<AbstractWayPoint, Integer> costs){
        //call basic constructor
        this(points);
        //sort array the bigger cost first as the table is read end first
        Arrays.sort(wayPoints, (a,b) -> costs.get(b).compareTo(costs.get(a)));
    }
    /**
     * Construct an iterator on the given structure.
     * @param points the collection in which you want to iterate.
     */
    public WayPointIterator(Collection<AbstractWayPoint> points) {
        this.wayPoints = new AbstractWayPoint[points.size()];
        this.wayPointsLeft = 0;
        for(AbstractWayPoint point: points) {
            this.wayPoints[wayPointsLeft++] = point;
        }
    }




    /**
     *
     * @return true only if there is a WayPoint left in the collection.
     */
    @Override
    public boolean hasNext() {
        return this.wayPointsLeft > 0;
    }

    /**
     *
     * @return the next item in the collection.
     */
    @Override
    @Requires("this.hasNext()")
    public AbstractWayPoint next() {
        return this.wayPoints[--wayPointsLeft];
    }

    /**
     * @param time the time at witch you want to get the "best" next node
     * @return the next item in the collection available at the time given in parameter.
     * if none satisfy this condition the first is returned.
     */
    @Requires("this.hasNext()")
    public AbstractWayPoint next(int time) {
        for(int i=wayPointsLeft-1 ; i>=0 ; i--) {
            if(this.wayPoints[i].canBePassed(time)){
                AbstractWayPoint ret = this.wayPoints[i];
                for(int n=i ; n<wayPointsLeft-2 ; n++){
                    wayPoints[n] = wayPoints[n+1];
                }
                this.wayPointsLeft--;
                return ret;
            }
        }
        return next();
    }
}
