package models;

import javafx.beans.property.SimpleIntegerProperty;

public class Point {
    private SimpleIntegerProperty x = new SimpleIntegerProperty();
    private SimpleIntegerProperty y = new SimpleIntegerProperty();

    public Point(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public SimpleIntegerProperty xProperty() {
        return this.x;
    }

    public int getX() {
        return this.x.getValue();
    }

    public void setX(int x) {
        this.x.setValue(x);
    }

    public SimpleIntegerProperty yProperty() {
        return this.y;
    }

    public int getY() {
        return this.y.getValue();
    }

    public void setY(int y) {
        this.y.setValue(y);
    }
}
