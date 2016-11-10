package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class AddWaypointAfterCommand extends AddWaypointCommand {

    /**
     * The index where the way point has to be added.
     */
    private int index;

    /**
     * Create a command to add a way point to a planning at the 
     * given index.
     * @param waypointToAdd The way point to add.
     * @param index The index where the way point has to be added.
     * @param planning The planning on which the way point has
     * to be added.
     * @param map The city map affected by the planning.
     */
    public AddWaypointAfterCommand(AbstractWaypoint waypointToAdd, int index, Planning planning, CityMap map) {
        super(waypointToAdd, planning, map);
        this.index = index;
    }

    /**
     * Execute the command to add a new way point to the 
     * planning at the given index.
     */
    @Override
    public void execute() {
        this.planning.addWaypoint(this.waypointToAdd, this.index);
    }

    /**
     * Execute a command to remove the way point to the 
     * planning at the given index in order to cancel the 
     * current adding command.
     * @return The newly created remove command.    
     */
    @Override
    public AbstractCommand getReversed() {
        return new RemoveWaypointAfterCommand(this.waypointToAdd, this.index, this.planning, this.map);
    }
}
