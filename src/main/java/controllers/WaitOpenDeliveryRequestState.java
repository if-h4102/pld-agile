package controllers;


import com.sun.istack.internal.NotNull;
import javafx.stage.FileChooser;
import models.CityMap;

import java.io.File;

public class WaitOpenDeliveryRequestState extends WaitOpenCityMapState {
    public void enterState() {

    }

    public void leaveState() {

    }

    public void onOpenDeliveryRequestButtonAction(@NotNull MainController mainController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Delivery Request");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Delivery Request file (*.xml)", "xml"));
        File deliverRequestFile = fileChooser.showOpenDialog(mainController.getRoot().getScene().getWindow());

        if (deliverRequestFile == null) { // User cancelled operation
            return;
        }

        CityMap currentCityMap = mainController.getParserService().getCityMap(deliverRequestFile);

        mainController.setCurrentCityMap(currentCityMap);
    }
}
