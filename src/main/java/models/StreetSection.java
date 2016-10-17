package models;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class StreetSection {
    final private SimpleStringProperty streetName = new SimpleStringProperty();
    final private SimpleIntegerProperty length = new SimpleIntegerProperty();
    final private SimpleIntegerProperty speed = new SimpleIntegerProperty();
    final private NumberBinding duration = this.length.multiply(this.speed);
    final private SimpleObjectProperty<Intersection> start = new SimpleObjectProperty<>();
    final private SimpleObjectProperty<Intersection> end = new SimpleObjectProperty<>();

    public StreetSection(int length, int speed, String streetName, Intersection start, Intersection end) {
        this.length.setValue(length);
        this.speed.setValue(speed);
        this.streetName.setValue(streetName);
        this.start.setValue(start);
        this.end.setValue(end);
    }

    public int getDuration() {
        return this.duration.getValue().intValue();
    }

    public String getStreetName() {
        return this.streetName.getValue();
    }

    public Intersection getStartIntersection() {
        return start.getValue();
    }

    public Intersection getEndIntersection() {
        return end.getValue();
    }

    public boolean equals(StreetSection other) {
        return this.streetName.getValue().equals(other.streetName.getValue())
            && this.length.getValue().equals(other.length.getValue())
            && this.speed.getValue().equals(other.speed.getValue());
    }
}
