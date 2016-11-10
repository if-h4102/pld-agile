package models;

public class Warehouse extends AbstractWaypoint {

    /**
     * Instantiate a warehouse at the given intersection.
     * @param intersection the intersection on which will be the wanted warehouse.
     */
    public Warehouse(Intersection intersection) {
        super(intersection);
    }

    /**
     * Instantiate a warehouse based on the given parameters.
     * @param intersection the intersection on which will be the wanted warehouse.
     * @param startPlanningTime the time at which a delivery man can depart from the warehouse.
     */
    public Warehouse(Intersection intersection, int startPlanningTime) {
        super(intersection, startPlanningTime, 86400);
    }

    /**
     * Get the duration needed by a delivery man to load his truck in the current warehouse.
     * @return the duration needed by a delivery man to load his truck in the current warehouse.
     */
    @Override
    public int getDuration() {
        // Not taken in consideration in our current model
        return 0;
    }

    /**
     * Get a string representing the current warehouse.
     * @return a string representation of the current warehouse.
     */
    @Override
    public String toString() {
        return "" + getId();
    }
}
