package models;

public class PlanningWaypoint {
    private final int startMovingTime;
    private final int startWaitingTime;
    private final AbstractWaypoint targetWaypoint;
    private final int startUnloadingTime;
    private final int endUnloadingTime;
    private final boolean isPossible;

    public PlanningWaypoint(int startMovingTime, int startWaitingTime, AbstractWaypoint targetWaypoint, int startUnloadingTime, int endUnloadingTime, boolean isPossible) {
        this.startMovingTime = startMovingTime;
        this.startWaitingTime = startWaitingTime;
        this.targetWaypoint = targetWaypoint;
        this.startUnloadingTime = startUnloadingTime;
        this.endUnloadingTime = endUnloadingTime;
        this.isPossible = isPossible;
    }

    public int getStartMovingTime() {
        return this.startMovingTime;
    }

    public int getStartWaitingTime() {
        return this.startWaitingTime;
    }

    public AbstractWaypoint getTargetWaypoint() {
        return this.targetWaypoint;
    }

    public int getStartUnloadingTime() {
        return this.startUnloadingTime;
    }

    public int getEndUnloadingTime() {
        return this.endUnloadingTime;
    }

    public boolean getIsPossible() {
        return this.isPossible;
    }
}
