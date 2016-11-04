package components.citymapdetails;

import components.intersectioncard.IntersectionCard;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.CityMap;
import models.Intersection;

import java.io.IOException;

public class CityMapDetails extends ScrollPane {
    @FXML
    protected VBox vBox;
    private SimpleObjectProperty<CityMap> cityMap;

    public CityMapDetails() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/citymapdetails/CityMapDetails.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        observeCityMap();
    }

    public void observeCityMap () {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();

        cityMapProperty().addListener(event -> refreshAll());
    }

    private void refreshAll () {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();
        itemNodes.clear();
        final CityMap cityMap = getCityMap();
        if (cityMap == null) {
            return;
        }

        for (Intersection i: cityMap.getIntersections()) {
            final IntersectionCard node = new IntersectionCard();
            node.setIntersection(i);
//            node.setIndex(index++);
//            node.setItem(item);
            itemNodes.add(node);
        }
    }

    public final SimpleObjectProperty<CityMap> cityMapProperty() {
        if (cityMap == null) {
            cityMap = new SimpleObjectProperty<>(this, "cityMap", null);
        }
        return cityMap;
    }

    public final void setCityMap(CityMap cityMap) {
        cityMapProperty().setValue(cityMap);
    }

    public final CityMap getCityMap() {
        return cityMapProperty().getValue();
    }
}
