package components.informationarea;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class InformationArea extends AnchorPane {
    private static final String DEFAULT_TEXT = "-";

    @FXML
    private TextField informationField;

    private SimpleStringProperty info = new SimpleStringProperty(this, "info", DEFAULT_TEXT);

    /**
     * Create an Information area object using the associated .fxml file.
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

    public SimpleStringProperty infoProperty() {
        return this.info;
    }

    public String getInfo() {
        return this.infoProperty().getValue();
    }
}

