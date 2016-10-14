package models;

import javafx.beans.property.SimpleObjectProperty;

public abstract class AbstractWayPoint {
    final protected SimpleObjectProperty<Intersection> intersection = new SimpleObjectProperty<>();

    public AbstractWayPoint(Intersection intersection) {
        this.intersection.setValue(intersection);
    }

    public Intersection getIntersection() {
        return this.intersection.getValue();
    }

    public int getX() {
        return this.getIntersection().getX();
    }

    public int getY() {
        return this.getIntersection().getX();
    }

    // TODO
    protected int getId() {
        return 0;
    }
}
