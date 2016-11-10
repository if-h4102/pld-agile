package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class RemoveWaypointCommand extends AbstractCommand {

    /**
     * The way point to remove.
     */
    protected AbstractWaypoint waypointToRemove;
    
    /**
     * The planning on which the way point has to be removed.
     */
    protected Planning planning;
    
    /**
     * The city map affected by the planning.
     */
    protected CityMap map;

    /**
     * Create a command to remove a way point to a planning.
     * @param waypointToAdd The way point to remove.
     * @param planning The planning on which the way point is removed.
     * @param map The map affected by the planning.
     */
    public RemoveWaypointCommand(AbstractWaypoint waypointToAdd, Planning planning, CityMap map) {
        this.waypointToRemove = waypointToAdd;
        this.planning = planning;
        this.map = map;
    }

    /**
     * Execute the command to remove a way point to the planning.
     */
    @Override
    public void execute() {
        this.planning.removeWaypoint(this.waypointToRemove);
    }

    /**
     * Whether or not the current command is reversible.
     * @return true only if the current command is reversible.
     * Return false otherwise.
     */
    @Override
    public boolean isReversible() {
        return true;
    }

    /**
     * Execute a command to add the way point to the 
     * planning in order to cancel the current removing command.
     * @return The newly created add command.    
     */
    @Override
    public AbstractCommand getReversed() {
        return new AddWaypointCommand(this.waypointToRemove, this.planning, this.map);
    }
}
