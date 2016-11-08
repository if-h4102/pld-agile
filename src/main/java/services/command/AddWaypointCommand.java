package services.command;

import models.AbstractWaypoint;
import models.CityMap;
import models.Planning;

public class AddWaypointCommand extends AbstractCommand {

    protected AbstractWaypoint waypointToAdd;
    protected Planning planning;
    protected CityMap map;

    public AddWaypointCommand(AbstractWaypoint waypointToAdd, Planning planning, CityMap map) {
        this.waypointToAdd = waypointToAdd;
        this.planning = planning;
        this.map = map;
    }

    @Override
    public void execute() {
        this.planning.addWaypoint(this.waypointToAdd);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new RemoveWaypointCommand(this.waypointToAdd, this.planning, this.map);
    }
}
