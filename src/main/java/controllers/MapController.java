package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Intersection;
import views.ResizableCanvas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MapController implements Initializable {
    @FXML
    protected AnchorPane root;

    @FXML
    protected ResizableCanvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("mouse pressed");
    }
}
