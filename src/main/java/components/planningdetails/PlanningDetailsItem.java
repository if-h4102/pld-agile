package components.planningdetails;

import components.events.AddWaypointAction;
import components.events.RemoveWaypointAction;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import models.AbstractWaypoint;
import models.Planning;
import models.Route;
import services.map.WaypointPlanning;

import java.io.IOException;

public class PlanningDetailsItem extends AnchorPane {
    private final SimpleObjectProperty<Route> item = new SimpleObjectProperty<>(this, "item", null);
    private final SimpleObjectProperty<Planning> planning = new SimpleObjectProperty<>(this, "planning", null);
    private SimpleObjectProperty<AbstractWaypoint> waypoint = new SimpleObjectProperty<>(this,"waypoint", null);
    private final SimpleIntegerProperty index = new SimpleIntegerProperty(this, "index", 0);
    private WaypointPlanning activeWaypoint;

    public PlanningDetailsItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/planningdetails/PlanningDetailsItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.activeWaypoint.waypointProperty().addListener(new ChangeListener(){

			@Override
			public void changed(activeWaypoint.waypointProperty() observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				
			});
        
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
}
