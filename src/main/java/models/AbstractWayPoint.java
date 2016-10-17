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

    @Override
    public boolean equals(Object o) {
        return o instanceof AbstractWayPoint && this.getId() == ((AbstractWayPoint)o).getId();
    }

    @Override
    public int hashCode() {
        return new Integer(this.getId()).hashCode();
    }

    protected int getId() {
        return this.intersection.getValue().getId();
    }
}
