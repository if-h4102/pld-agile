package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Intersection {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleObjectProperty<Point> coordinates = new SimpleObjectProperty<>();

    public Intersection (int id, Point coordinates) {
        this.setId(id);
        this.setCoordinates(coordinates);
    }

    public Intersection (int id, int x, int y) {
        this(id, new Point(x, y));
    }

    public SimpleIntegerProperty idProperty() {
        return this.id;
    }

    public int getId() {
        return this.id.getValue();
    }

    public void setId(int id) {
        this.id.setValue(id);
    }

    public SimpleObjectProperty<Point> coordinatesProperty() {
        return this.coordinates;
    }

    public Point getCoordinates() {
        return this.coordinates.getValue();
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates.setValue(coordinates);
    }
}
