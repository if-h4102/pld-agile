package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class RemoveWaypointAfterCommand extends RemoveWaypointCommand {

    /**
     * The index where the point has to be removed.
     */
    private int index;

    /**
     * Create a command to remove a way point to a planning.
     * Determine the index of the way point to remove and use it to 
     * update the index attribute value.
     * @param waypointToRemove The way point to remove.
     * @param planning The planning on which the way point is removed.
     * @param map The city map affected by the planning.
     */
    public RemoveWaypointAfterCommand(AbstractWaypoint waypointToRemove, Planning planning, CityMap map) {
        super(waypointToRemove, planning, map);
        this.index = planning.getWaypoints().indexOf(waypointToRemove);
    }
       
    /**
     * Create a command to remove a way point to a planning at the 
     * given index.
     * @param waypointToRemove The way point to remove.
     * @param index The new value of the index attribute.
     * @param planning The planning on which the way point is removed.
     * @param map The city map affected by the planning.
     */
    public RemoveWaypointAfterCommand(AbstractWaypoint waypointToRemove, int index, Planning planning, CityMap map) {
        super(waypointToRemove, planning, map);
        this.index = index;
    }

    /**
     * Execute the command to remove a way point to the planning.
     */
    @Override
    public void execute() {
        this.planning.removeWaypoint(this.waypointToRemove);
    }
    
    /**
     * Execute a command to add a way point to the planning using the index
     * attribute in order to cancel the current removing command.
     * @return The newly created add command.
     */
    @Override
    public AbstractCommand getReversed() {
        return new AddWaypointAfterCommand(this.waypointToRemove, this.index, this.planning, this.map);
    }
}
