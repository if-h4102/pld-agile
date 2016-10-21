package services.command;

import models.AbstractWayPoint;

public class AddWayPointCommand extends AbstractCommand {

    private AbstractWayPoint wayPointToAdd;

    public AddWayPointCommand(AbstractWayPoint wayPointToAdd) {
        this.wayPointToAdd = wayPointToAdd;
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
        return new RemoveWayPointCommand(this.wayPointToAdd);
    }
}
