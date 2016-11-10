package components.application;

import models.Planning;

/**
 * This interface is used to allow the TspSolver to call it when it has finished to compute.
 */
public interface TspCompletedListener {

    /**
     * Call by the TspSolver when it has finished to compute.
     * @param bestPlanning The best planning find by the TspSolver.
     */
    public void notifyOfTspComplete(Planning bestPlanning);
}
