package components.application;

import javafx.stage.FileChooser;
import models.DeliveryRequest;
import services.xml.exception.ParserException;
import java.io.File;
import java.io.IOException;
import components.exceptionwindow.ExceptionWindow;

public class WaitOpenDeliveryRequestState extends WaitOpenCityMapState {

    public WaitOpenDeliveryRequestState(MainController mainController) {
        super(mainController);
    }

    @Override
    public void enterState() {
        mainController.setDeliveryRequest(null); // Reset any previous delivery request
        mainController.setPlanning(null);
    }

    @Override
    public void leaveState() {

    }

    @Override
    public MainControllerState onOpenDeliveryRequestButtonAction() {
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
            new ExceptionWindow(e.getMessage());
            return this; // Cancel the operation
        }

        mainController.setDeliveryRequest(currentDeliveryRequest);
        mainController.setPlanning(null);

        return new ReadyToComputeState(mainController);
    }
}
