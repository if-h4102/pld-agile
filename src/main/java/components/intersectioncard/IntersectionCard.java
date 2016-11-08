package components.intersectioncard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.Intersection;

import java.io.IOException;

public class IntersectionCard<E extends Intersection> extends AnchorPane {
    @FXML
    protected Label content;

    private SimpleObjectProperty<E> intersection;

    public IntersectionCard() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/intersectioncard/IntersectionCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Item
    public final SimpleObjectProperty<E> intersectionProperty() {
        if (intersection == null) {
            intersection = new SimpleObjectProperty<>(this, "intersection", null);
        }
        return intersection;
    }

    public final void setIntersection(E value) {
        intersectionProperty().setValue(value);
    }

    public final E getIntersection() {
        return intersection == null ? null : intersectionProperty().getValue();
    }

    public void onRemoveButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove intersection ...");
    }

    public void onEditButtonAction(ActionEvent actionEvent) {
        System.out.println("Edit intersection ...");
    }
}
