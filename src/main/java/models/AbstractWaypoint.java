package models;

import com.google.java.contract.Requires;

public abstract class AbstractWaypoint implements Comparable<AbstractWaypoint> {

    /**
     * The intersection on which is the current waypoint.
     */
    final protected Intersection intersection;

    /**
     * The starting time on which the delivery man can pass.
     */
    private int timeStart;

    /**
     * The ending time on which the delivery man can pass.
     */
    private int timeEnd;

    /**
     * Initialize attributes of an abstract waypoint.
     * @param intersection
     * @param timeStart
     * @param timeEnd
     */
    public AbstractWaypoint(Intersection intersection, int timeStart, int timeEnd) {
        this.intersection = intersection;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public AbstractWaypoint(Intersection intersection) {
        this.intersection = intersection;
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
