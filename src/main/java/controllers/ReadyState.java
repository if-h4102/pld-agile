package controllers;


import com.sun.istack.internal.NotNull;
import javafx.stage.FileChooser;
import models.DeliveryRequest;

import java.io.File;

public class ReadyState extends WaitOpenDeliveryRequestState {
    public void enterState(@NotNull MainController mainController) {

    }

    public void leaveState(@NotNull MainController mainController) {

    }

    public @NotNull MainControllerState onCumputePlanningButtonAction(@NotNull MainController mainController) {
        System.out.println("Computing Planning...");

        return this;
    }
}
