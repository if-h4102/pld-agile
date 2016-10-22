package services.command;

import models.AbstractWayPoint;
import models.Planning;

public class RemoveWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToRemove;
    private Planning planning;

    public RemoveWayPointCommand(AbstractWayPoint wayPointToAdd, Planning planning) {
        this.wayPointToRemove = wayPointToAdd;
        this.planning = planning;
    }

    @Override
    public void execute() {
        this.planning.removeWayPoint(this.wayPointToRemove);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new AddWayPointCommand(this.wayPointToRemove, this.planning);
    }
}
