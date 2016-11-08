package models;

public class StreetSection {

    private String streetName;
    private int length;
    private int duration;
    private Intersection start;
    private Intersection end;

    public StreetSection(int length, int speed, String streetName, Intersection start, Intersection end) {
        this.length = length;
        this.duration = length / speed;
        this.streetName = streetName;
        this.start = start; // TODO clone start and end to avoid modification?
        this.end = end;
    }

    public int getDuration() {
        return duration;
    }

    public String getStreetName() {
        return streetName;
    }

    public Intersection getStartIntersection() {
        return start;
    }

    public Intersection getEndIntersection() {
        return end;
    }

    public int getLength(){
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StreetSection))
            return false;

        StreetSection other = (StreetSection) obj;
        return this.start.equals(other.start) && this.end.equals(other.end);
    }
}
