package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Intersection;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    protected BorderPane root;

    private SimpleStringProperty btnText = new SimpleStringProperty();
    private SimpleObjectProperty<Intersection> origin = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setBtnText("Hello world");
        this.setOrigin(new Intersection(42, 10, 20));
    }

    public void openCityMap(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open City Map");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("City Map file (*.xml)", "xml"));
        File cityMapFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (cityMapFile == null) { // Cancelled
            return;
        }

        // TODO: use service to load and parse the cityMap
        // this.setCurrentCityMap(this.parser.parseCityMap(cityMapFile));
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
