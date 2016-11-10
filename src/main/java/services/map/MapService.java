package services.map;

import components.application.MainController;
import javafx.beans.property.SimpleObjectProperty;
import models.AbstractWaypoint;
import models.Intersection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * This class is a simple implementation of the IMapService interface.
 * <p>
 * The purpose of this service is to let the components interact with the map.
 */
public class MapService implements IMapService {
    /**
     * A reference to the mainController (root component)
     */
    private final MainController mainController;

    /**
     * The currently active waypoint is the last waypoint selected by the user.
     * It can be selected either via the PlanningDeails component or by
     * clicking on the map.
     */
    private final SimpleObjectProperty<AbstractWaypoint> activeWaypoint = new SimpleObjectProperty<>(this, "activeWaypoint", null);

    public MapService(@NotNull MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Request the user to select an intersection.
     *
     * @return A future that will be completed once the user has selected
     * an intersection.
     */
    @NotNull
    public CompletableFuture<Intersection> promptIntersection() {
        CompletableFuture<Intersection> future = new CompletableFuture<>();
        this.mainController.onPromptIntersection(future);
        return future;
    }

    /**
     * Returns the observable object for the active waypoint. This is the
     * last selected / focused waypoint.
     *
     * @return The observable object for the activeWaypoint
     */
    @NotNull
    public final SimpleObjectProperty<AbstractWaypoint> activeWaypointProperty() {
        return this.activeWaypoint;
    }

    /**
     * Get the currently active / selected waypoint.
     * @return The currently active waypoint.
     */
    @Nullable
    public final AbstractWaypoint getActiveWaypoint() {
        return this.activeWaypointProperty().getValue();
    }

    /**
     * Set the new value for the currently active waypoint.
     * @param value The new active waypoint.
     */
    public final void setActiveWaypoint(@Nullable AbstractWaypoint value) {
        this.activeWaypointProperty().setValue(value);
    }
}
