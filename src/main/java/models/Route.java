package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Route {
    final private SimpleIntegerProperty duration = new SimpleIntegerProperty();
    final private SimpleObjectProperty<AbstractWayPoint> start = new SimpleObjectProperty();
    final private SimpleObjectProperty<AbstractWayPoint> end = new SimpleObjectProperty();

    // TODO
    public Route() {

    }

    public AbstractWayPoint getStartWaypoint() {
        return this.start.getValue();
    }

    public AbstractWayPoint getEndWaypoint() {
        return this.end.getValue();
    }

    public int getDuration() {
        return this.duration.getValue();
    }
}
