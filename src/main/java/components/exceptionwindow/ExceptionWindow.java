package components.exceptionwindow;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ExceptionWindow {

    /**
     * Construct an exception window which display the given exception message. 
     * @param exceptionMessage The message to display.
     */
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