package services.command;

import models.AbstractWayPoint;
import models.Planning;

public class AddWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToAdd;
    private Planning planning;

    public AddWayPointCommand(AbstractWayPoint wayPointToAdd, Planning planning) {
        this.wayPointToAdd = wayPointToAdd;
        this.planning = planning;
    }

    @Override
    public void execute() {
        this.planning.addWayPoint(this.wayPointToAdd);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new RemoveWayPointCommand(this.wayPointToAdd, this.planning);
    }
}
