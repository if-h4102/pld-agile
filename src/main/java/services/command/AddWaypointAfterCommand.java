package services.command;

import models.AbstractWayPoint;
import models.CityMap;
import models.Planning;

public class AddWaypointAfterCommand extends AbstractCommand {

    private AbstractWayPoint waypointToAdd;
    private AbstractWayPoint previousWaypoint;
    private Planning planning;
    private CityMap map;

    public AddWaypointAfterCommand(AbstractWayPoint waypointToAdd, AbstractWayPoint previousWaypoint, Planning planning, CityMap map) {
        this.waypointToAdd = waypointToAdd;
        this.previousWaypoint = previousWaypoint;
        this.planning = planning;
        this.map = map;
    }

    @Override
    public void execute() {
        this.planning.addWayPoint(this.waypointToAdd, this.previousWaypoint, this.map);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new RemoveWayPointCommand(this.waypointToAdd, this.planning, this.map);
    }
}
