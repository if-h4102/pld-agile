package models;

import com.google.java.contract.Requires;
import javafx.beans.property.SimpleObjectProperty;

public abstract class AbstractWayPoint implements Comparable<AbstractWayPoint>{
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
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractWayPoint))
            return false;
        
        AbstractWayPoint other = (AbstractWayPoint) obj;
        return this.intersection.getValue().equals(other.intersection.getValue());
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

    protected int getId() {
        return this.intersection.getValue().getId();
    }
}
