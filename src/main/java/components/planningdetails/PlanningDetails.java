package components.planningdetails;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.Route;

//http://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml
public class PlanningDetails extends ListView<Route> {
    public PlanningDetails() {
        super();
        setCellFactory(view -> new ListCell<Route>() {
            @Override
            public void updateItem(Route newValue, boolean empty) {
                super.updateItem(newValue, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PlanningDetailsItem item = new PlanningDetailsItem();
                    setGraphic(item);
                    item.setRoute(newValue);
                }
            }
        });
    }
}
