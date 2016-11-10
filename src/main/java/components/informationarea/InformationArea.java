package components.informationarea;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class InformationArea extends AnchorPane {
    /**
     * The default text to display to the user.
     */
    private static final String DEFAULT_TEXT = "-";

    /**
     * The text field in which information will be displayed.
     */
    @FXML
    private TextField informationField;

    /**
     * The currently displayed information.
     */
    private SimpleStringProperty info = new SimpleStringProperty(this, "info", DEFAULT_TEXT);

    /**
     * instantiate an Information area object using the associated .fxml file.
     */
    public InformationArea() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/informationarea/InformationArea.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Update the information to display to the user.
     * @param information the new information to display
     */
    public void setInformation(String information) {
        this.informationField.setText(information);
    }

    /**
     * Get the information currently displayed to the user wrapped in a javafx SimpleStringProperty.
     * @return the information currently displayed to the user wrapped in a javafx SimpleStringProperty.
     */
    public SimpleStringProperty infoProperty() {
        return this.info;
    }

    /**
     * Get the information currently displayed to the user.
     * @return the information currently displayed to the user.
     */
    public String getInfo() {
        return this.infoProperty().getValue();
    }
}

