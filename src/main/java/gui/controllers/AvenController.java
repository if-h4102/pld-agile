package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvenController implements Initializable {
    @FXML
    public TextArea routeDescription; // The text input in the login screen

    // Model logic
    private Object model; // The model linked with the view
    private boolean initialized = false;

    /**
     * Initialization of the controller. Makes the link between the View and the Model
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.model != null && !this.initialized) {
            this.bindModel();
        }
        this.initialized = true;
    }

    /**
     * Links the model to the view
     *
     * @param model the model linked
     */
    public void bindModel(Object model) {}

    /**
     * Submethod used to bind the model to the view<
     */
    private void bindModel() {}
}
