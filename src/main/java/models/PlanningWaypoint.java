package models;

public class PlanningWaypoint {
    /**
     * The time at which a delivery man must start moving.
     */
    private final int startMovingTime;

    /**
     * The time at which a delivery man must start waiting.
     */
    private final int startWaitingTime;

    /**
     * The associated waypoint.
     */
    private final AbstractWaypoint targetWaypoint;

    /**
     * The time at which a delivery man must start unloading his truck.
     */
    private final int startUnloadingTime;

    /**
     * The time at which a delivery man must be done unloading his truck.
     */
    private final int endUnloadingTime;

    /**
     * Whether or not this waypoint is correctly deliverable.
     */
    private final boolean isPossible;

    /**
     * Instantiate a planning waypoint based on the given parameters.
     * @param startMovingTime The time at which a delivery man must start moving.
     * @param startWaitingTime The time at which a delivery man must start waiting.
     * @param targetWaypoint The associated waypoint.
     * @param startUnloadingTime The time at which a delivery man must be done unloading his truck.
     * @param endUnloadingTime The time at which a delivery man must be done unloading his truck.
     * @param isPossible Whether or not this waypoint is correctly deliverable.
     */
    public PlanningWaypoint(int startMovingTime, int startWaitingTime, AbstractWaypoint targetWaypoint, int startUnloadingTime, int endUnloadingTime, boolean isPossible) {
        this.startMovingTime = startMovingTime;
        this.startWaitingTime = startWaitingTime;
        this.targetWaypoint = targetWaypoint;
        this.startUnloadingTime = startUnloadingTime;
        this.endUnloadingTime = endUnloadingTime;
        this.isPossible = isPossible;
    }

    /**
     * Get the time at which a delivery man must start moving.
     * @return the time at which a delivery man must start moving.
     */
    public int getStartMovingTime() {
        return this.startMovingTime;
    }

    /**
     * Get the time at which a delivery man must start waiting.
     * @return the time at which a delivery man must start waiting.
     */
    public int getStartWaitingTime() {
        return this.startWaitingTime;
    }

    /**
     * Get the waypoint associated to the current planning waypoint.
     * @return the associated waypoint.
     */
    public AbstractWaypoint getTargetWaypoint() {
        return this.targetWaypoint;
    }

    /**
     * Get the time at which a delivery man must start unloading his truck.the time at which a delivery man must start moving.
     * @return time at which a delivery man must start unloading his truck.
     */
    public int getStartUnloadingTime() {
        return this.startUnloadingTime;
    }

    /**
     * Get the time at which a delivery man must be done unloading his truck.
     * @return the time at which a delivery man must be done unloading his truck.
     */
    public int getEndUnloadingTime() {
        return this.endUnloadingTime;
    }

    /**
     * Whether or not this waypoint is correctly deliverable.
     * @return true only of the delivery can be correctly done.
     */
    public boolean getIsPossible() {
        return this.isPossible;
    }
}
