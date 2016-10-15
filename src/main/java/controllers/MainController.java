package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.CityMap;
import models.Intersection;
import services.xml.Parser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private BorderPane root;

    final private SimpleObjectProperty<CityMap> currentCityMap = new SimpleObjectProperty<>();
    final private Parser parserService = new Parser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.setBtnText("Hello world");
//        this.setOrigin(new Intersection(42, 10, 20));
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

    protected Parent getRoot() {
        return this.root;
    }

    protected Parser getParserService() {
        return this.parserService;
    }

    protected SimpleObjectProperty<CityMap> currentCityMapProperty() {
        return this.currentCityMap;
    }

    protected CityMap getOrigin () {
        return this.currentCityMap.getValue();
    }

    protected void setCurrentCityMap (CityMap currentCityMap) {
        this.currentCityMap.setValue(currentCityMap);
    }
}
