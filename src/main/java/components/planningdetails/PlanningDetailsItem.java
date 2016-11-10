package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.AbstractWaypoint;
import models.Planning;
import models.Route;
import services.map.IMapService;
import services.map.MapService;

import java.io.IOException;

public class PlanningDetailsItem extends AnchorPane {
    private final SimpleObjectProperty<Route> item = new SimpleObjectProperty<>(this, "item", null);
    private final SimpleObjectProperty<Planning> planning = new SimpleObjectProperty<>(this, "planning", null);
    private SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this,"mapService", null);
    private final SimpleIntegerProperty index = new SimpleIntegerProperty(this, "index", 0);

    public PlanningDetailsItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetailsItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mapServiceProperty().addListener(this::onMapServiceChange);
    }

    /**
     * @return The observable property for the route displayed by this item.
     */
    public final SimpleObjectProperty<Route> itemProperty() {
        return this.item;
    }

    public final void setItem(Route value) {
        this.itemProperty().setValue(value);
    }

    public final Route getItem() {
        return this.itemProperty().getValue();
    }

    /**
     * @return The observable property for the planning containing the currently
     * displayed item.
     */
    public final SimpleObjectProperty<Planning> planningProperty() {
        return this.planning;
    }

    public final void setPlanning(Planning value) {
        this.planningProperty().setValue(value);
    }

    public final Planning getPlanning() {
        return this.planningProperty().getValue();
    }


    // Index
    public final SimpleIntegerProperty indexProperty() {
        return this.index;
    }

    public final void setIndex(int value) {
        this.indexProperty().setValue(value);
    }

    public final int getIndex() {
        return this.indexProperty().getValue();
    }

    public void onRemoveButtonAction(ActionEvent actionEvent) {
        Route item = this.getItem();
        if (item == null) {
            return;
        }
        AbstractWaypoint startWaypoint = item.getStartWaypoint();
        if (startWaypoint == null) {
            return;
        }
        fireEvent(new RemoveWaypointAction(startWaypoint));
    }

    public void onAddButtonAction(ActionEvent actionEvent) {
        fireEvent(new AddWaypointAction(this.getIndex() + 1));
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

    protected void onMapServiceChange(ObservableValue<? extends IMapService> observable, IMapService oldValue, IMapService newValue) {
        if (oldValue == newValue) {
            return;
        }
        System.out.println("Item: Service changed");
        if (oldValue != null) {
            oldValue.activeWaypointProperty().removeListener(this::onActiveWaypointChange);
        }
        if (newValue != null) {
            newValue.activeWaypointProperty().addListener(this::onActiveWaypointChange);
        }
    }

    protected void onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue) {
        System.out.println("Item: ActiveWaypoint change");
    }

    public void onWaypointCardClickedAction(MouseEvent event) {
        System.out.println("Clicked on waypoint");
    }
}
