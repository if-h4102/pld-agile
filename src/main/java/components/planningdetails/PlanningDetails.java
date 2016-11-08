package components.planningdetails;

import components.events.AddWaypointAction;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.*;
import services.map.IMapService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PlanningDetails extends ScrollPane {
    @FXML
    protected VBox vBox;
    private final SimpleObjectProperty<Planning> planning = new SimpleObjectProperty<>(this, "planning", null);
    private final SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this, "mapService", null);

    final private ChangeListener<Planning> planningChangeListener;
    final private ListChangeListener<Route> planningRoutesChangeListener;

    public PlanningDetails() {
        super();

        this.planningChangeListener = (observable, oldValue, newValue) -> this.onPlanningChange(oldValue, newValue);
        this.planningRoutesChangeListener = (change) -> this.refreshAll();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetails.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        observeItems();
        this.addEventHandler(AddWaypointAction.TYPE, this::onAddWaypointAction);
    }

    public void observeItems() {
        this.planningProperty().addListener(this.planningChangeListener);
//        final ObservableList<Node> itemNodes = this.vBox.getChildren();
//
//        itemsProperty().addListener(new ListChangeListener<E>() {
//            @Override
//            public void onChanged(Change<? extends E> change) {
//                refreshAll();
////                while (change.next()) {
////                    if (change.wasAdded()) {
////                        itemNodes.add(new PlanningDetailsItem());
////                    }
////                }
//            }
//        });
    }

    private void refreshAll() {
        final ObservableList<Node> itemNodes = this.vBox.getChildren();
        itemNodes.clear();
        final Planning planning = this.getPlanning();
        if (planning == null) {
            return;
        }
        final ObservableList<Route> routes = planning.getRoutes();
        if (routes == null) {
            return;
        }

        int index = 0;
        for (Route item : routes) {
            final PlanningDetailsItem node = new PlanningDetailsItem();
            node.setIndex(index++);
            node.setItem(item);
            node.setPlanning(planning);
            itemNodes.add(node);
        }
    }

    public final SimpleObjectProperty<Planning> planningProperty() {
        return this.planning;
    }

    public final void setPlanning(Planning value) {
        this.planningProperty().setValue(value);
    }

    public final Planning getPlanning() {
        return this.planningProperty().getValue();
    }

    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    public void setMapService(IMapService value) {
        this.mapServiceProperty().setValue(value);
    }

    public void onPlanningChange(Planning oldValue, Planning newValue) {
        if (oldValue == newValue) {
            return;
        }
        if (oldValue != null) {
            oldValue.routesProperty().removeListener(this.planningRoutesChangeListener);
        }
        if (newValue != null) {
            newValue.routesProperty().addListener(this.planningRoutesChangeListener);
        }
        this.refreshAll();
    }

    public void onAddWaypointAction (AddWaypointAction action) {
        IMapService mapService = this.getMapService();
        if (mapService == null) {
            System.err.println("Missing map service");
        }
        this.getMapService()
            .promptIntersection()
            .thenAccept(intersection -> {
                System.out.println("Got intersection:");
                System.out.println(intersection);
                DeliveryAddress deliveryAddress = new DeliveryAddress(intersection, 5 * 60);
                Planning planning = this.getPlanning();
                int index = action.getIndex();
                AbstractWaypoint after = planning.getRoutes().get(index - 1).getStartWaypoint();
                planning.addWaypoint(deliveryAddress, after);
                System.out.println("foo");
            });
    }
}
