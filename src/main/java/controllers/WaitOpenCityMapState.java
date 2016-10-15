package controllers;


import com.sun.istack.internal.NotNull;
import javafx.stage.FileChooser;
import models.CityMap;

import java.io.File;

public class WaitOpenCityMapState extends MainControllerState {
    public void enterState(@NotNull MainController mainController) {

    }

    public void leaveState(@NotNull MainController mainController) {

    }

    public MainControllerState onOpenCityMapButtonAction(@NotNull MainController mainController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open City Map");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("City Map file (*.xml)", "xml"));
        File cityMapFile = fileChooser.showOpenDialog(mainController.getRoot().getScene().getWindow());

        if (cityMapFile == null) { // User cancelled operation
            return this;
        }

        CityMap currentCityMap = mainController.getParserService().getCityMap(cityMapFile);

        mainController.setCurrentCityMap(currentCityMap);

        return new WaitOpenDeliveryRequestState();
    }
}
