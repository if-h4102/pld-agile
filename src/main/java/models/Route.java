package models;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private int duration;
    private AbstractWaypoint start;
    private AbstractWaypoint end;
    private List<StreetSection> streetSections;

    public Route(AbstractWaypoint startWaypoint, AbstractWaypoint endWaypoint, List<StreetSection> streetSections) {
        this.start = startWaypoint; // TODO add a clone to avoid modification?
        this.end = endWaypoint;

        this.streetSections = new ArrayList<StreetSection>(streetSections);
        int sumStreetSectionDuration = 0;
        for (StreetSection streetSection : streetSections) {
            sumStreetSectionDuration += streetSection.getDuration();
        }

        this.duration = sumStreetSectionDuration;
    }

    public AbstractWaypoint getStartWaypoint() {
        return start;
    }

    public AbstractWaypoint getEndWaypoint() {
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
