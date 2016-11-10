package models;

public class Intersection {

    /**
     * The x coordinate of the intersection.
     */
    private int x;

    /**
     * The y coordinate of the intersection.
     */
    private int y;

    /**
     * The ID of the intersection.
     */
    private int id;

    /**
     * Instantiate an intersection based on the given parameters.
     * @param id the ID of the intersection.
     * @param x the x coordinate of the intersection.
     * @param y the y coordinate of the intersection.
     */
    public Intersection(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Get the current value of the x coordinate.
     * @return Current value of the x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the current value of the y coordinate.
     * @return Current value of the y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the current value of the `id` property
     *
     * @return Current value of `id`
     */
    public int getId() {
        return this.id;
    }

    /**
     * Return whether or not the current intersection is the same that the given one.
     * @param obj the object to check.
     * @return true only if the given object is exactly the same that the current one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Intersection)) {
            return false;
        }
        Intersection other = (Intersection) obj;
        return this.id == other.id;
    }

    /**
     * Get a string representing the current intersection.
     * @return a string representation of the current intersection.
     */
    @Override
    public String toString() {
        return "Intersection [x=" + x + ", y=" + y + ", id=" + id + "]";
    }

}
