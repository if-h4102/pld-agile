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
     * Initialize attributes of an abstract waypoint with constraints.
     * @param intersection the intersection on which is the current waypoint
     * @param timeStart the starting time on which the delivery man can pass
     * @param timeEnd the ending time on which the delivery man can pass
     */
    public AbstractWaypoint(Intersection intersection, int timeStart, int timeEnd) {
        this.intersection = intersection;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    /**
     * Initialize attributes of an abstract waypoint without constraints.
     * @param intersection the intersection on which is the current waypoint
     */
    public AbstractWaypoint(Intersection intersection) {
        this.intersection = intersection;
        this.timeStart = 0;
        this.timeEnd = 86400; // end of a day
    }

    /**
     * Whether or not a delivery man can pass at the given time of paa
     * @param timeOfPassage the time of passage to check.
     * @return true if the time of passage is more than deliveryTimeStart and time of passage plus delivery duration is less than
     * timeEnd if timeOfPassage is greater than a day (86400 sec) is modulus by 86400 is used.
     */
    public boolean canBePassed(int timeOfPassage) {
        timeOfPassage %= 86400;
        return this.timeStart <= timeOfPassage && this.timeEnd >= (timeOfPassage + this.getDuration());
    }

    /**
     * Get the duration during which a delivery man can pass.
     * @return the duration during which a delivery man can pass.
     */
    public abstract int getDuration();

    /**
     * Get the starting time on which the delivery man can pass.
     * @return the starting time on which the delivery man can pass
     */
    public int getTimeStart() {
        return this.timeStart;
    }

    /**
     * Get the ending time on which the delivery man can pass.
     * @return the ending time on which the delivery man can pass
     */
    public int getTimeEnd() {
        return this.timeEnd;
    }

    /**
     * Get intersection on which is the current waypoint.
     * @return the intersection on which is the current waypoint
     */
    public Intersection getIntersection() {
        return this.intersection;
    }

    /**
     * Get the x coordinate of the current waypoint.
     * @return the x coordinate of the current waypoint
     */
    public int getX() {
        return this.intersection.getX();
    }

    /**
     * Get the y coordinate of the current waypoint.
     * @return the y coordinate of the current waypoint
     */
    public int getY() {
        return this.intersection.getY();
    }

    /**
     * Get the ID of the current waypoint (i.e. the one from the associated intersection).
     * @return the ID of the current waypoint
     */
    public int getId() {
        return this.intersection.getId();
    }

    /**
     * Return whether or not the current waypoint is the same that the given one.
     * @param obj the object to check
     * @return true only if the given object is exactly the same that the current one
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractWaypoint)) {
            return false;
        }

        AbstractWaypoint other = (AbstractWaypoint) obj;
        return this.getId() == other.getId();
    }

    /**
     * Hash the current object.
     * @return the code representing the hashed object.
     */
    @Override
    public int hashCode() {
        return new Integer(this.getId()).hashCode();
    }

    /**
     * Compare the given waypoint to the current one.
     * @param other the object to compare.
     * @return the difference between the IDs of teh two waypoints.
     */
    @Override
    @Requires({"other != null"})
    public int compareTo(AbstractWaypoint other) {
        return getId() - other.getId();
    }
}
