package components.exceptionwindow;

import java.awt.Dialog.ModalityType;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ExceptionWindow {

    public ExceptionWindow (String exceptionMessage) {
        // Reshape the exception message to be sure it fits in the error window.
        String[] splitedMessage = exceptionMessage.split(" ");
        String displayedMessage = "";
        int size = 0;
        for (int i=0; i<splitedMessage.length; i++) {
            displayedMessage += splitedMessage[i];
            size += splitedMessage[i].length();
            if (i != splitedMessage.length - 1) {
                if (size < 30) {
                    displayedMessage += " ";
                    size++;
                } else {
                    displayedMessage += "\n";
                    size = 0;
                }
            }
        }
        
        Alert alert = new Alert(AlertType.ERROR, displayedMessage, ButtonType.OK);
        alert.setTitle("Error window");
        alert.showAndWait();
    }
}