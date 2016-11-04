package components.planningdetails;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.Route;

import java.io.IOException;

public class PlanningDetails<E extends Route> extends ScrollPane {
    @FXML
    protected VBox vBox;
    private SimpleListProperty<E> items;

    public PlanningDetails() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetails.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        observeItems();
    }

    public void observeItems() {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();

        itemsProperty().addListener(new ListChangeListener<E>() {
            @Override
            public void onChanged(Change<? extends E> change) {
                refreshAll();
//                while (change.next()) {
//                    if (change.wasAdded()) {
//                        itemNodes.add(new PlanningDetailsItem());
//                    }
//                }
            }
        });
    }

    private void refreshAll() {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();
        itemNodes.clear();
        final ObservableList<E> items = getItems();
        if (getItems() == null) {
            return;
        }

        int index = 0;
        for (E item : items) {
            final PlanningDetailsItem node = new PlanningDetailsItem();
            node.setIndex(index++);
            node.setItem(item);
            itemNodes.add(node);
        }
    }

    public final SimpleListProperty<E> itemsProperty() {
        if (items == null) {
            items = new SimpleListProperty<>(this, "items", null);
        }
        return items;
    }

    public final void setItems(ObservableList<E> items) {
        itemsProperty().setValue(items);
    }

    public final ObservableList<E> getItems() {
        return itemsProperty().getValue();
    }
}
