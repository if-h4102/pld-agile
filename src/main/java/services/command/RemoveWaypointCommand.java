package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class RemoveWaypointCommand extends AbstractCommand {

    private AbstractWaypoint waypointToRemove;
    private Planning planning;
    private CityMap map;

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
