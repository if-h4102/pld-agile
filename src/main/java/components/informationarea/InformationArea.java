package components.informationarea;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class InformationArea extends AnchorPane {
    private static final String DEFAULT_TEXT = "Click somewhere !";

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
}

