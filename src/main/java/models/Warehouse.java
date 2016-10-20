package models;

public class Warehouse extends AbstractWayPoint {

    public Warehouse(Intersection intersection) {
        super(intersection);
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
