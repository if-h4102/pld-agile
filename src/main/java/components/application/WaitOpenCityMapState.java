package components.application;

import javafx.stage.FileChooser;
import models.CityMap;
import services.xml.exception.ParserException;
import java.io.File;
import java.io.IOException;
import components.exceptionwindow.ExceptionWindow;

public class WaitOpenCityMapState extends MainControllerState {

    public WaitOpenCityMapState(MainController mController) {
        super(mController);
    }

    @Override
    public void enterState() {

    }

    @Override
    public void leaveState() {

    }

    @Override
    public MainControllerState onOpenCityMapButtonAction() {
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
            new ExceptionWindow(e.getMessage());
        }

        mainController.setCityMap(currentCityMap);

        return new WaitOpenDeliveryRequestState(mainController);
    }
}
