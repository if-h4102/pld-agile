package controllers;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.CityMap;
import models.DeliveryRequest;
import models.Intersection;
import services.xml.Parser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private Button openCityMapButton;
    @FXML
    private Button openDeliveryRequestButton;
    @FXML
    private Button computePlanningButton;

    final private ReadOnlyObjectWrapper<MainControllerState> state = new ReadOnlyObjectWrapper<>();
    final private SimpleObjectProperty<CityMap> currentCityMap = new SimpleObjectProperty<>();
    final private SimpleObjectProperty<DeliveryRequest> currentDeliveryRequest = new SimpleObjectProperty<>();
    final private Parser parserService = new Parser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setState(new WaitOpenCityMapState());
        this.openDeliveryRequestButton.disableProperty().bind(this.currentCityMap.isNull());
        this.computePlanningButton.disableProperty().bind(this.currentDeliveryRequest.isNull());
    }

    protected Parent getRoot() {
        return this.root;
    }

    protected Parser getParserService() {
        return this.parserService;
    }

    protected SimpleObjectProperty<CityMap> currentCityMapProperty() {
        return this.currentCityMap;
    }

    protected CityMap getCurrentCityMap () {
        return this.currentCityMap.getValue();
    }

    protected void setCurrentCityMap (CityMap currentCityMap) {
        this.currentCityMap.setValue(currentCityMap);
    }

    protected ObservableObjectValue<DeliveryRequest> currentDeliveryRequestProperty() {
        return this.currentDeliveryRequest;
    }

    protected DeliveryRequest getCurrentDeliveryRequest () {
        return this.currentDeliveryRequest.getValue();
    }

    protected void setCurrentDeliveryRequest (DeliveryRequest currentDeliveryRequest) {
        this.currentDeliveryRequest.setValue(currentDeliveryRequest);
    }

    protected ObservableObjectValue<MainControllerState> stateProperty() {
        return this.state.getReadOnlyProperty();
    }

    protected MainControllerState getState () {
        return this.state.getValue();
    }

    private void setState (MainControllerState state) {
        this.state.setValue(state);
    }

    private void applyState(@NotNull MainControllerState nextState) {
        MainControllerState currentState = this.getState();
        if (currentState == nextState) {
            return;
        }
        currentState.leaveState(this);
        nextState.enterState(this);
        this.setState(nextState);
    }

    public void onOpenCityMapButtonAction(ActionEvent actionEvent) {
        this.applyState(this.getState().onOpenCityMapButtonAction(this));
    }

    public void onOpenDeliveryRequestButtonAction(ActionEvent actionEvent) {
        this.applyState(this.getState().onOpenDeliveryRequestButtonAction(this));
    }

    public void onComputePlanningButtonAction(ActionEvent actionEvent) {
        this.applyState(this.getState().onOpenDeliveryRequestButtonAction(this));
    }
}
