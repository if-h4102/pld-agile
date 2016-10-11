package gui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RootModel {
    private StringProperty state;

    public RootModel() {
        this.state = new SimpleStringProperty("init");
    }
}
