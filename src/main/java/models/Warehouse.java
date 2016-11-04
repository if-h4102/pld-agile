package models;

public class Warehouse extends AbstractWayPoint {

    public Warehouse(Intersection intersection) {
        super(intersection);
    }
    
    public Warehouse(Intersection intersection, int startPlanningTime) {
        super(intersection, startPlanningTime, 86400);
    }

    @Override
    public int getDuration() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "" + getId();
    }
}
