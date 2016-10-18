package models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class Intersection {
    final private ReadOnlyIntegerWrapper id = new ReadOnlyIntegerWrapper();
    final private ReadOnlyIntegerWrapper x = new ReadOnlyIntegerWrapper();
    final private ReadOnlyIntegerWrapper y = new ReadOnlyIntegerWrapper();

    public Intersection(int id, int x, int y) {
        this.setId(id);
        this.setX(id);
        this.setY(id);
    }

    /**
     * Get the observable integer property for `x`
     *
     * @return The observable integer property for `x`
     */
    public ReadOnlyIntegerProperty xProperty() {
        return this.x.getReadOnlyProperty();
    }

    /**
     * Get the current value of the `x` property
     *
     * @return Current value of `x`
     */
    public int getX() {
        return this.x.getValue();
    }

    /**
     * Set a new value for the `x` property
     *
     * @param x New value of `x`
     */
    private void setX(int x) {
        this.x.setValue(x);
    }

    /**
     * Get the observable integer property for `y`
     *
     * @return The observable integer property for `y`
     */
    public ReadOnlyIntegerProperty yProperty() {
        return this.y.getReadOnlyProperty();
    }

    /**
     * Get the current value of the `y` property
     *
     * @return Current value of `y`
     */
    public int getY() {
        return this.y.getValue();
    }

    /**
     * Set a new value for the `y` property
     *
     * @param y New value of `y`
     */
    private void setY(int y) {
        this.y.setValue(y);
    }

    /**
     * Get the observable integer property for `id`
     *
     * @return The observable integer property for `id`
     */
    protected ReadOnlyIntegerProperty idProperty() {
        return this.id.getReadOnlyProperty();
    }

    /**
     * Get the current value of the `id` property
     *
     * @return Current value of `id`
     */
    protected int getId() {
        return this.id.getValue();
    }

    /**
     * Set a new value for the `id` property
     *
     * @param id New value of `id`
     */
    private void setId(int id) {
        this.id.setValue(id);
    }

    public boolean equals(Intersection other) {
        return this.getId() == other.getId();
    }
   
    public String toString(){
    	return "("+x+";"+y+")";
    }
}
