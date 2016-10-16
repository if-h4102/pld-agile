package services.tsp;

import com.google.java.contract.Requires;
import models.AbstractWayPoint;

import java.util.Collection;
import java.util.Iterator;

public class WayPointIterator implements Iterator<AbstractWayPoint> {

    private AbstractWayPoint[] wayPoints;
    private int wayPointsLeft;

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
}
