package components.application;

import models.Planning;

public interface TspCompletedListener {

    public void notifyOfTspComplete(Planning bestPlanning);
}
