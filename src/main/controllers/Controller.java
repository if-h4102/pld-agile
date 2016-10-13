package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.Intersection;
import models.Point;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private SimpleStringProperty btnText = new SimpleStringProperty();
    private SimpleObjectProperty<Intersection> origin = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setBtnText("Hello world");
        this.setOrigin(new Intersection(42, 10, 20));
    }

    public void helloWorld(ActionEvent actionEvent) {
        this.setBtnText("Hello world!!!!!");
        this.getOrigin().getCoordinates().setX(50);
    }

    public SimpleStringProperty btnTextProperty() {
        return this.btnText;
    }

    public String getBtnText() {
        return this.btnText.getValue();
    }

    public void setBtnText(String btnText) {
        this.btnText.setValue(btnText);
    }

    public SimpleObjectProperty<Intersection> originProperty() {
        return this.origin;
    }

    public Intersection getOrigin () {
        return this.origin.getValue();
    }

    public void setOrigin (Intersection origin) {
        this.origin.setValue(origin);
    }
}
