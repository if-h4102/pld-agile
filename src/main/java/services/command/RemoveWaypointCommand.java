package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class RemoveWaypointCommand extends AbstractCommand {

    protected AbstractWaypoint waypointToRemove;
    protected Planning planning;
    protected CityMap map;

    public RemoveWaypointCommand(AbstractWaypoint waypointToAdd, Planning planning, CityMap map) {
        this.waypointToRemove = waypointToAdd;
        this.planning = planning;
        this.map = map;
    }

    @Override
    public void execute() {
        this.planning.removeWaypoint(this.waypointToRemove);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new AddWaypointCommand(this.waypointToRemove, this.planning, this.map);
    }
}
