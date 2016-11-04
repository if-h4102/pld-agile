package models;

import com.google.java.contract.Requires;

public abstract class AbstractWayPoint implements Comparable<AbstractWayPoint> {

    final protected Intersection intersection;
    private int timeStart;
    private int timeEnd;

    public AbstractWayPoint(Intersection intersection, int deliveryTimeStart, int deliveryTimeEnd) {
        this.intersection = intersection; // TODO clone to avoid a later modification?
        this.timeStart = deliveryTimeStart;
        this.timeEnd = deliveryTimeEnd;
    }

    public AbstractWayPoint(Intersection intersection) {
        this.intersection = intersection; // TODO clone to avoid a later modification?
        this.timeStart = 0;
        this.timeEnd = 86400; // end of a day
    }

    // TODO put a @Requires ( {"timeOfPassage <= 86400", "timeOfPassage >= 0"} ) ? <- no: modulus is here to handle case where a malus is already added because of wrong passage time
    /**
     * @param timeOfPassage
     * @return true if the time of passage is more than deliveryTimeStart and time of passage plus delivery duration is less than
     *         timeEnd if timeOfPassage is greater than a day (86400 sec) is modulus by 86400 is used.
     */
    public boolean canBePassed(int timeOfPassage) {
        timeOfPassage = timeOfPassage % 86400;
        return timeStart < timeOfPassage && timeEnd > (timeOfPassage + this.getDuration());
    }

    public abstract int getDuration();

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public int getX() {
        return intersection.getX();
    }

    public int getY() {
        return intersection.getY();
    }

    protected int getId() {
        return intersection.getId();
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
}
