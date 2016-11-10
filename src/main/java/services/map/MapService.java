package services.map;

import components.application.MainController;
import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;
import models.Intersection;
import sun.applet.Main;

import java.util.concurrent.CompletableFuture;

public class MapService implements IMapService {
    private final MainController mainController;
	private final SimpleObjectProperty<AbstractWaypoint> activeWaypoint = new SimpleObjectProperty<>(this, "activeWaypoint", null);

    public MapService(MainController mainController) {
        this.mainController = mainController;
    }

    public final SimpleObjectProperty<AbstractWaypoint> activeWaypointProperty() {
        return this.activeWaypoint;
    }

    public final void setWaypoint(AbstractWaypoint value) {
        this.activeWaypointProperty().setValue(value);
    }

    public final AbstractWaypoint getWaypoint() {
        return this.activeWaypointProperty().getValue();
    }

    public CompletableFuture<Intersection> promptIntersection() {
        CompletableFuture<Intersection> future = new CompletableFuture<>();
        this.mainController.onPromptIntersection(future);
        return future;
    }
}
