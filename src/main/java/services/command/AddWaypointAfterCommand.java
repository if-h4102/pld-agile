package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class AddWaypointAfterCommand extends AddWaypointCommand {

    private int index;

    public AddWaypointAfterCommand(AbstractWaypoint waypointToAdd, int index, Planning planning, CityMap map) {
        super(waypointToAdd, planning, map);
        this.index = index;
    }

    @Override
    public void execute() {
        this.planning.addWaypoint(this.waypointToAdd, this.index);
    }

    @Override
    public AbstractCommand getReversed() {
        return new RemoveWaypointAfterCommand(this.waypointToAdd, this.index, this.planning, this.map);
    }
}
