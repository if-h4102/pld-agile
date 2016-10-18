package models;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Route {

    private int duration;
    private AbstractWayPoint start;
    private AbstractWayPoint end;
    private List<StreetSection> streetSections;

    public Route(AbstractWayPoint startWayPoint, AbstractWayPoint endWayPoint, List<StreetSection> streetSections) {
        this.start = startWayPoint; // TODO add a clone to avoid modification?
        this.end = endWayPoint;

        this.streetSections = new ArrayList<StreetSection>(streetSections);
        int sumStreetSectionDuration = 0;
        for (StreetSection streetSection : streetSections) {
            sumStreetSectionDuration += streetSection.getDuration();
        }

        this.duration = sumStreetSectionDuration;
    }

    public AbstractWayPoint getStartWaypoint() {
        return start;
    }

    public AbstractWayPoint getEndWaypoint() {
        return end;
    }

    public int getDuration() {
        return duration;
    }
}
