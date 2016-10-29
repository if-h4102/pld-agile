package services.command;

import models.AbstractWayPoint;
import models.CityMap;
import models.Planning;

public class AddWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToAdd;
    private Planning planning;
    private CityMap map;

    public AddWayPointCommand(AbstractWayPoint wayPointToAdd, Planning planning, CityMap map) {
        this.wayPointToAdd = wayPointToAdd;
        this.planning = planning;
        this.map = map;
    }

    @Override
    public void execute() {
        this.planning.addWayPoint(this.wayPointToAdd, this.map);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new RemoveWayPointCommand(this.wayPointToAdd, this.planning, this.map);
    }
}
