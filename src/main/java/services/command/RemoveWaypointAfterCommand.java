package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class RemoveWaypointAfterCommand extends RemoveWaypointCommand {

    private int index;

    public RemoveWaypointAfterCommand(AbstractWaypoint waypointToAdd, int index, Planning planning, CityMap map) {
        super(waypointToAdd, planning, map);
        this.index = index;
    }

    @Override
    public void execute() {
        this.planning.removeWaypoint(this.waypointToRemove);
    }

    @Override
    public AbstractCommand getReversed() {
        return new AddWaypointAfterCommand(this.waypointToRemove, this.index, this.planning, this.map);
    }
}
