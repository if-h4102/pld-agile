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
    
    public List<StreetSection> getStreetSections(){
    	return streetSections;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route))
            return false;
        
        Route other = (Route) obj;
        if (this.duration != other.duration)
            return false;
        
        if (!this.start.equals(other.start))
            return false;
        
        if (!this.end.equals(other.end))
            return false;
        
        if (this.streetSections.size() != other.streetSections.size())
            return false;
        for (int i = 0; i < streetSections.size(); i++) {
            if (!this.streetSections.get(i).equals(other.streetSections.get(i)))
                return false;
        }
        return true;
    }
}
