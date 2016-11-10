package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class AddWaypointCommand extends AbstractCommand {

    /**
     * The way point to add.
     */
    protected AbstractWaypoint waypointToAdd;
    
    /**
     * The planning on which the way point has to be added.
     */
    protected Planning planning;
    
    /**
     * The city map affected by the planning.
     */
    protected CityMap map;

    
    /**
     * Create a command to add a way point to a planning.
     * @param waypointToAdd The way point to add.
     * @param planning The planning on which the way point
     * has to be added.
     * @param map The city map affected by the planning.
     */
    public AddWaypointCommand(AbstractWaypoint waypointToAdd, Planning planning, CityMap map) {
        this.waypointToAdd = waypointToAdd;
        this.planning = planning;
        this.map = map;
    }

    /**
     * Execute the command to add a new way point to the 
     * planning.
     */
    @Override
    public void execute() {
        this.planning.addWaypoint(this.waypointToAdd);
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
     * Execute a command to remove the way point to the 
     * planning in order to cancel the current adding command.
     * @return The newly created remove command.    
     */
    @Override
    public AbstractCommand getReversed() {
        return new RemoveWaypointCommand(this.waypointToAdd, this.planning, this.map);
    }
}
