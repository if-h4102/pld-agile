package services.command;

import models.AbstractWayPoint;
import models.CityMap;
import models.Planning;

public class RemoveWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToRemove;
    private Planning planning;
    private CityMap map;

    public RemoveWayPointCommand(AbstractWayPoint wayPointToAdd, Planning planning, CityMap map) {
        this.wayPointToRemove = wayPointToAdd;
        this.planning = planning;
        this.map = map;
    }

    @Override
    public void execute() {
        this.planning.removeWayPoint(this.wayPointToRemove, this.map);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new AddWayPointCommand(this.wayPointToRemove, this.planning, this.map);
    }
}
