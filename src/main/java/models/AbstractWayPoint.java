package models;

import com.google.java.contract.Requires;

public abstract class AbstractWayPoint implements Comparable<AbstractWayPoint> {

    final protected Intersection intersection;

    public AbstractWayPoint(Intersection intersection) {
        this.intersection = intersection; // TODO clone to avoid a later modification?
    }

    public abstract int getDuration();

    public Intersection getIntersection() {
        return intersection;
    }

    public int getX() {
        return getIntersection().getX();
    }

    public int getY() {
        return getIntersection().getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractWayPoint))
            return false;

        AbstractWayPoint other = (AbstractWayPoint) obj;
        return getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return new Integer(this.getId()).hashCode();
    }

    @Override
    @Requires({ "other != null" })
    public int compareTo(AbstractWayPoint other) {
        return getId() - other.getId();
    }

    protected int getId() {
        return this.intersection.getId();
    }
}
