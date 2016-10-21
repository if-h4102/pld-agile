package services.command;

import models.AbstractWayPoint;

public class RemoveWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToRemove;

    public RemoveWayPointCommand(AbstractWayPoint wayPointToAdd) {
        this.wayPointToRemove = wayPointToAdd;
    }

    @Override
    public void execute() {
        // TODO
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    @Override
    public AbstractCommand getReversed() {
        return new AddWayPointCommand(this.wayPointToRemove);
    }
}
