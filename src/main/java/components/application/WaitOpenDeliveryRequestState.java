package components.application;


import javafx.stage.FileChooser;
import models.DeliveryRequest;
import services.xml.exception.ParserException;
import java.io.File;
import java.io.IOException;

import components.exceptionwindow.ExceptionWindow;

public class WaitOpenDeliveryRequestState extends WaitOpenCityMapState {
    public void enterState(MainController mainController) {
        mainController.setDeliveryRequest(null); // Reset any previous delivery request
    }

    public void leaveState(MainController mainController) {

    }

    public MainControllerState onOpenDeliveryRequestButtonAction(MainController mainController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Delivery Request");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Delivery Request file (*.xml)", "xml"));
        File deliveryRequestFile = fileChooser.showOpenDialog(mainController.getRoot().getScene().getWindow());

        if (deliveryRequestFile == null) { // User cancelled operation
            return this;
        }

        DeliveryRequest currentDeliveryRequest = null;
        try {
            currentDeliveryRequest = mainController.getParserService().getDeliveryRequest(deliveryRequestFile, mainController.getCityMap());
        } catch (IOException | ParserException e) {
            e.printStackTrace();
            ExceptionWindow exceptionWindow = new ExceptionWindow(e.getMessage());
        }

        mainController.setDeliveryRequest(currentDeliveryRequest);
        mainController.setPlanning(null);

        return new ReadyState();
    }
}
