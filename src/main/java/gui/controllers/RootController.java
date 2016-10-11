package gui.controllers;

import gui.models.RootModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class RootController {

    // Graphic logic
    private Scene rootScene; // the root scene of the JavaFX application
    // Model logic
    private RootModel model; // the model linked with the view

    /**
     * Creates a root controller with a root scene.
     *
     * @param rootScene the root scene of the JavaFX application
     */
    public RootController(Scene rootScene) {
        this.rootScene = rootScene;
    }

    /**
     * Links the model to the view
     *
     * @param model the model linked
     */
    public void bindModel(RootModel model) {
        this.model = model;
        this.loadScreen();
    }

    public void loadScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/aven.fxml"));
            rootScene.setRoot(loader.load());
//            AvenController controller = loader.<AvenController>getController();
//            controller.bindModel(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
