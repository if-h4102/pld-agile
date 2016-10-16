package models;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Route {

    final private SimpleIntegerProperty duration = new SimpleIntegerProperty();
    final private SimpleObjectProperty<AbstractWayPoint> start = new SimpleObjectProperty();
    final private SimpleObjectProperty<AbstractWayPoint> end = new SimpleObjectProperty();
    final private List<StreetSection> streetSections;

    public Route(AbstractWayPoint startWayPoint, AbstractWayPoint endWayPoint, List<StreetSection> streetSections) {
        this.start.setValue(startWayPoint);
        this.end.setValue(endWayPoint);

        this.streetSections = new ArrayList<StreetSection>(streetSections);
        int sumStreetSectionDuration = 0;
        for (StreetSection streetSection : streetSections) {
            sumStreetSectionDuration += streetSection.getDuration();
        }

        this.duration.setValue(sumStreetSectionDuration);
    }

    public AbstractWayPoint getStartWaypoint() {
        return this.start.getValue();
    }

    public AbstractWayPoint getEndWaypoint() {
        return this.end.getValue();
    }

    protected int getDuration() {
        return duration.getValue();
    }
}
