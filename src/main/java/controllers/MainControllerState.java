package controllers;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Intersection;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public abstract class MainControllerState {
    public void enterState (@NotNull MainController mainController) {

    }
    public void leaveState (@NotNull MainController mainController) {

    }

    public @NotNull MainControllerState onOpenCityMapButtonAction(MainController mainController) {
        return this;
    }

    public @NotNull MainControllerState onOpenDeliveryRequestButtonAction(MainController mainController) {
        return this;
    }

    public @NotNull MainControllerState onCumputePlanningButtonAction(MainController mainController) {
        return this;
    }
}
