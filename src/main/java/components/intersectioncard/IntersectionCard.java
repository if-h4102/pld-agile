package components.intersectioncard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.Intersection;

import java.io.IOException;

/**
 * 
 * This class is an fxml object which represents the tooltip that appears when an Intersection is selected.
 *
 */
public class IntersectionCard<E extends Intersection> extends AnchorPane {
    @FXML
    protected Label content;

    private SimpleObjectProperty<E> intersection;

    /** Constructor of the class intersectionCard.
     * Loading the associate fxml file, which use the accessible intersection for the tooltip.
     * 
     */
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
    /** Create the accessible intersection for the IntersectionCard. 
     * Its informations will be printed on the tooltip.
     * 
     * @return The intersectionProperty.
     */
    public final SimpleObjectProperty<E> intersectionProperty() {
        if (intersection == null) {
            intersection = new SimpleObjectProperty<>(this, "intersection", null);
        }
        return intersection;
    }
    /**Set the value of the intersection of the intersectionProperty
     * 
     * @param value - The new Intersection
     */
    public final void setIntersection(E value) {
        intersectionProperty().setValue(value);
    }
    /**Get the value of the intersection contained by the intersectionProperty.
     * 
     * @return The intersection value.
     */
    public final E getIntersection() {
        return intersection == null ? null : intersectionProperty().getValue();
    }

}
