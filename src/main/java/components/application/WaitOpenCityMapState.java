package components.application;


import javafx.stage.FileChooser;
import models.CityMap;
import services.xml.exception.ParserException;
import java.io.File;
import java.io.IOException;

public class WaitOpenCityMapState extends MainControllerState {
    public void enterState(MainController mainController) {

    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onOpenCityMapButtonAction(MainController mainController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open City Map");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("City Map file (*.xml)", "xml"));
        File cityMapFile = fileChooser.showOpenDialog(mainController.getRoot().getScene().getWindow());

        if (cityMapFile == null) { // User cancelled operation
            return this;
        }

        CityMap currentCityMap = null;
        try {
            currentCityMap = mainController.getParserService().getCityMap(cityMapFile);
        } catch (IOException | ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mainController.setCityMap(currentCityMap);

        return new WaitOpenDeliveryRequestState();
    }
}
