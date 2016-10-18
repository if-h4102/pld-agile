package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Intersection;
import java.util.Locale;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("views.Locale", new Locale("en", "US")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 960);

        primaryStage.setTitle("Delivery App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
