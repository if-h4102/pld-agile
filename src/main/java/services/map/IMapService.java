package services.map;

import components.application.MainController;
import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;
import models.Intersection;

import java.util.concurrent.CompletableFuture;

public interface IMapService {
    CompletableFuture<Intersection> promptIntersection();
    SimpleObjectProperty<AbstractWaypoint> activeWaypointProperty();
    void setWaypoint(AbstractWaypoint value);
    AbstractWaypoint getWaypoint();
}
