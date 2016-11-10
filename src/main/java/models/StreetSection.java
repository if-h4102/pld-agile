package models;

public class StreetSection {

    /**
     * The name of the street in which is the current street section.
     */
    private String streetName;

    /**
     * The length of the current street section, in meters.
     */
    private int length;

    /**
     * The total amount of time needed to travel across the street section.
     */
    private int duration;

    /**
     * The starting intersection of the street section.
     */
    private Intersection start;

    /**
     * The ending intersection of the street section.
     */
    private Intersection end;

    /**
     * Instantiate a street section based on the given parameters.
     * @param length the length of the current street section, in meters.
     * @param speed the maximal speed authorized in this streeet section.
     * @param streetName the name of the street in which is wanted street section.
     * @param start the starting intersection of the street section.
     * @param end the ending intersection of the street section.
     */
    public StreetSection(int length, int speed, String streetName, Intersection start, Intersection end) {
        this.length = length;
        this.duration = length / speed;
        this.streetName = streetName;
        this.start = start;
        this.end = end;
    }

    /**
     * Get the total amount of time needed to travel across the current street section.
     * @return the total amount of time needed to travel across the current street section.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Get the name of the street in which is the current street section.
     * @return the name of the street in which is the current street section.
     */
    public String getStreetName() {
        return this.streetName;
    }

    /**
     * Get the starting intersection of the street section.
     * @return the starting intersection of the street section.
     */
    public Intersection getStartIntersection() {
        return this.start;
    }

    /**
     * Get the ending intersection of the street section.
     * @return the ending intersection of the street section.
     */
    public Intersection getEndIntersection() {
        return this.end;
    }

    /**
     * Get the length of the current street section, in meters.
     * @return the length of the current street section, in meters.
     */
    public int getLength(){
        return this.length;
    }

    /**
     * Return whether or not the current street section is the same that the given one.
     * @param obj the object to check.
     * @return true only if the given object is exactly the same that the current one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StreetSection)) {
            return false;
        }

        StreetSection other = (StreetSection) obj;
        return this.start.equals(other.start) && this.end.equals(other.end);
    }
}
