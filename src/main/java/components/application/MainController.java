package components.application;

import javafx.application.Platform;
import components.events.RemoveWaypointAction;
import components.events.SaveDeliveryAddress;
import components.mapcanvas.IntersectionSelectionEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AbstractWaypoint;
import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import services.command.AddWaypointAfterCommand;
import services.command.CommandManager;
import services.command.RemoveWaypointAfterCommand;
import services.command.RemoveWaypointCommand;
import services.map.IMapService;
import services.pdf.planningPrinter;
import services.xml.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;


public class MainController extends BorderPane {
    @FXML
    private BorderPane root;
    @FXML
    private Button openCityMapButton;
    @FXML
    private Button openDeliveryRequestButton;
    @FXML
    private Button computePlanningButton;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;
    @FXML
    private Button generateRoadmapButton;
    final private ReadOnlyObjectWrapper<MainControllerState> state = new ReadOnlyObjectWrapper<>();
    final private SimpleObjectProperty<CityMap> cityMap = new SimpleObjectProperty<>();
    final private SimpleObjectProperty<DeliveryRequest> deliveryRequest = new SimpleObjectProperty<>();
    final private SimpleObjectProperty<Planning> planning = new SimpleObjectProperty<>();
    final private SimpleDoubleProperty mapZoom = new SimpleDoubleProperty(1.0);

    final private Parser parserService = new Parser();
    final private CommandManager commandManager = new CommandManager();
    final private SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this, "mapService", null);

    public MainController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/application/main.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("locales.Locale", new Locale("en", "US")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setState(new WaitOpenCityMapState(this));
        this.openDeliveryRequestButton.disableProperty().bind(this.cityMap.isNull());
        this.computePlanningButton.setDisable(true);
        this.undoButton.disableProperty().bind(this.commandManager.undoableProperty().not());
        this.redoButton.disableProperty().bind(this.commandManager.redoableProperty().not());
        this.generateRoadmapButton.disableProperty().bind(this.planning.isNull());
        
        this.root.addEventHandler(SaveDeliveryAddress.TYPE, event-> {
           AbstractWaypoint abstractWaypoint = event.getDeliveryAddress();
           int index = event.getIndex();
           AddWaypointAfterCommand addWaypointAfterCommand = new AddWaypointAfterCommand(abstractWaypoint,index,this.planning.getValue(),this.cityMap.getValue());
           this.commandManager.execute(addWaypointAfterCommand);
           modifyComputePlanningButtonDisabledProperty(true);
        });
        
        this.root.addEventHandler(RemoveWaypointAction.TYPE, removeWaypointAction -> {
            AbstractWaypoint abstractWaypoint = removeWaypointAction.getWaypoint();
            RemoveWaypointAfterCommand removeWaypointAfterCommand = new RemoveWaypointAfterCommand(abstractWaypoint,this.planning.getValue(),this.cityMap.getValue());
            this.commandManager.execute(removeWaypointAfterCommand);
            modifyComputePlanningButtonDisabledProperty(true);
        });

        this.addEventHandler(IntersectionSelectionEvent.INTERSECTION_SELECTION, this::onIntersectionSelection);

        IMapService newMapService = () -> {
            CompletableFuture<Intersection> future = new CompletableFuture<>();
            this.onPromptIntersection(future); 
            return future;
        };
        this.setMapService(newMapService);
    }

    protected Parent getRoot() {
        return this.root;
    }

    protected Parser getParserService() {
        return this.parserService;
    }

    // CityMap
    public SimpleObjectProperty<CityMap> cityMapProperty() {
        return this.cityMap;
    }

    public CityMap getCityMap() {
        return cityMapProperty().getValue();
    }

    public void setCityMap(CityMap cityMap) {
        cityMapProperty().setValue(cityMap);
    }

    // Planning
    public SimpleObjectProperty<Planning> planningProperty() {
        return this.planning;
    }

    public Planning getPlanning() {
        return planningProperty().getValue();
    }

    public void setPlanning(Planning planning) {
        //planningProperty().setValue(planning);
        Platform.runLater(() -> this.planning.setValue(planning));
    }

    // mapZoom
    public SimpleDoubleProperty mapZoomProperty() {
        return this.mapZoom;
    }

    public double getMapZoom() {
        return mapZoomProperty().getValue();
    }

    public void setMapZoom(double mapZoom) {
        mapZoomProperty().setValue(mapZoom);
    }

    public ObservableObjectValue<DeliveryRequest> deliveryRequestProperty() {
        return this.deliveryRequest;
    }

    public DeliveryRequest getDeliveryRequest() {
        return this.deliveryRequest.getValue();
    }

    public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        this.deliveryRequest.setValue(deliveryRequest);
    }

    @FXML
    protected ObservableObjectValue<MainControllerState> stateProperty() {
        return this.state.getReadOnlyProperty();
    }

    protected MainControllerState getState() {
        return this.state.getValue();
    }

    private void setState(MainControllerState state) {
        this.state.setValue(state);
    }

    // MapService
    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    public void setMapService(IMapService value) {
        this.mapServiceProperty().setValue(value);
    }

    protected void applyState(MainControllerState nextState) {
        MainControllerState currentState = this.getState();
        if (currentState == nextState) {
            return;
        }
        currentState.leaveState();
        this.setState(nextState);
        nextState.enterState();
    }

    // handlers
    public void onOpenCityMapButtonAction() {
        this.applyState(this.getState().onOpenCityMapButtonAction());
    }

    public void onOpenDeliveryRequestButtonAction() {
        this.applyState(this.getState().onOpenDeliveryRequestButtonAction());
    }

    public void onComputePlanningButtonAction() {
        this.applyState(this.getState().onComputePlanningButtonAction());
    }

    public void onPromptIntersection(CompletableFuture<Intersection> future) {
        this.applyState(this.getState().onPromptIntersection(future));
    }

    public void onIntersectionSelection(IntersectionSelectionEvent event) {
        this.applyState(this.getState().onIntersectionSelection(event));
    }

    public void onUndoButtonAction() {
        commandManager.undo();
    }

    public void onRedoButtonAction() {
        commandManager.redo();
    }
    
    public void onGenerateRoadmapButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose planning directory");
        fileChooser.setInitialFileName("Planning.pdf");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            planningPrinter.generatePdfFromPlanning(this.planning.getValue(), file.getPath());
        }
    }
    
    // buttons properties modifier
    public void modifyComputePlanningButtonDisabledProperty(boolean disable) {
        if (this.computePlanningButton.isDisable() != disable) {
            this.computePlanningButton.setDisable(disable);
        }
    }
}
