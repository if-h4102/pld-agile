package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        System.out.println("Resources root:");
//        System.out.println(getClass().getResource("/").getPath());

        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Scene scene = new Scene(root, 300, 275);

        primaryStage.setTitle("Delivery App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
