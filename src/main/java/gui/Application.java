package gui;

import gui.controllers.RootController;
import gui.models.RootModel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private RootModel rootModel; // the root model of the JavaFX application

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new StackPane(), 300, 275);

        this.rootModel = new RootModel();
        RootController rootController = new RootController(scene);
        rootController.bindModel(this.rootModel);

        primaryStage.setTitle("AVEN.jar");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/gui/views/aven.css").toExternalForm());
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (this.rootModel != null) {
            this.rootModel = null;
        }
    }

    public static void open() {
        Application.launch();
    }
}
