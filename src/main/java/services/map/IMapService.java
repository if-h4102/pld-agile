package services.map;

import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;
import models.Intersection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * This class represents the service used by the JavaFX components to
 * manipulate the map.
 */
public interface IMapService {
    /**
     * Request the user to select an intersection.
     *
     * @return A future that will be completed once the user has selected
     * an intersection.
     */
    @NotNull
    CompletableFuture<Intersection> promptIntersection();

    /**
     * Returns the observable object for the active waypoint. This is the
     * last selected / focused waypoint.
     *
     * @return The observable object for the activeWaypoint
     */
    @NotNull
    SimpleObjectProperty<AbstractWaypoint> activeWaypointProperty();

    /**
     * Get the currently active / selected waypoint.
     * @return The currently active waypoint.
     */
    @Nullable
    AbstractWaypoint getActiveWaypoint();

    /**
     * Set the new value for the currently active waypoint.
     * @param value The new active waypoint.
     */
    void setActiveWaypoint(@Nullable AbstractWaypoint value);
}
