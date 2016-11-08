package models;

import com.google.java.contract.Requires;

public abstract class AbstractWaypoint implements Comparable<AbstractWaypoint> {

    final protected Intersection intersection;
    private int timeStart;
    private int timeEnd;

    public AbstractWaypoint(Intersection intersection, int deliveryTimeStart, int deliveryTimeEnd) {
        this.intersection = intersection; // TODO clone to avoid a later modification?
        this.timeStart = deliveryTimeStart;
        this.timeEnd = deliveryTimeEnd;
    }

    public AbstractWaypoint(Intersection intersection) {
        this.intersection = intersection; // TODO clone to avoid a later modification?
        this.timeStart = 0;
        this.timeEnd = 86400; // end of a day
    }

    /**
     * @param timeOfPassage
     * @return true if the time of passage is more than deliveryTimeStart and time of passage plus delivery duration is less than
     * timeEnd if timeOfPassage is greater than a day (86400 sec) is modulus by 86400 is used.
     */
    public boolean canBePassed(int timeOfPassage) {
        timeOfPassage %= 86400;
        return timeStart <= timeOfPassage && timeEnd >= (timeOfPassage + this.getDuration());
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

    public int getId() {
        return intersection.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractWaypoint))
            return false;

        AbstractWaypoint other = (AbstractWaypoint) obj;
        return getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return new Integer(this.getId()).hashCode();
    }

    @Override
    @Requires({"other != null"})
    public int compareTo(AbstractWaypoint other) {
        return getId() - other.getId();
    }
}
