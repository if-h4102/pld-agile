package models;

import java.util.ArrayList;
import java.util.List;

public class Route {

    /**
     * The total amount of time needed to travel across the route.
     */
    private int duration;

    /**
     * The starting waypoint of the route.
     */
    private AbstractWaypoint start;

    /**
     * The ending waypoint of the route.
     */
    private AbstractWaypoint end;

    /**
     * The list of all street sections in the current route.
     */
    private List<StreetSection> streetSections;

    /**
     * Instantiate a route based on the given parameters.
     * @param startWaypoint the starting waypoint of the route.
     * @param endWaypoint the ending waypoint of the route.
     * @param streetSections the list of all street sections needed.
     */
    public Route(AbstractWaypoint startWaypoint, AbstractWaypoint endWaypoint, List<StreetSection> streetSections) {
        this.start = startWaypoint;
        this.end = endWaypoint;
        this.streetSections = new ArrayList<StreetSection>(streetSections);
        this.duration = 0;
        for (StreetSection streetSection : streetSections) {
            this.duration += streetSection.getDuration();
        }
    }

    /**
     * Get the starting waypoint of the route.
     * @return the starting waypoint of the route.
     */
    public AbstractWaypoint getStartWaypoint() {
        return this.start;
    }

    /**
     * Get the ending waypoint of the route.
     * @return the ending waypoint of the route.
     */
    public AbstractWaypoint getEndWaypoint() {
        return this.end;
    }

    /**
     * Get the list of all street sections in the current route.
     * @return the list of all street sections in the current route.
     */
    public List<StreetSection> getStreetSections(){
    	return this.streetSections;
    }

    /**
     * Get the total amount of time needed to travel across the route.
     * @return the total amount of time needed to travel across the route.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Return whether or not the current route is the same that the given one.
     * @param obj the object to check.
     * @return true only if the given object is exactly the same that the current one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }
        Route other = (Route) obj;

        if (this.duration != other.duration) {
            return false;
        }
        if (!this.start.equals(other.start)) {
            return false;
        }
        if (!this.end.equals(other.end)) {
            return false;
        }
        if (this.streetSections.size() != other.streetSections.size())
            return false;
        for (int i = 0; i < streetSections.size(); i++) {
            if (!this.streetSections.get(i).equals(other.streetSections.get(i))) {
                return false;
            }
        }
        return true;
    }
}
