package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import views.ResizableCanvas;

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
        System.out.println("Start drag");
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        System.out.println("dragged");
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("Released");
    }
}
