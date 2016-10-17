package models;

public class Intersection {
    private int x;
    private int y;
    private int id;

    public Intersection(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Get the current value of the `x` property
     *
     * @return Current value of `x`
     */
    public int getX() {
        return x;
    }

    /**
     * Get the current value of the `y` property
     *
     * @return Current value of `y`
     */
    public int getY() {
        return y;
    }

    /**
     * Get the current value of the `id` property
     *
     * @return Current value of `id`
     */
    protected int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Intersection))
            return false;
        
        Intersection other = (Intersection) obj;
        return this.id == other.id
                && this.x == other.x
                && this.y == other.y;
    }
}
