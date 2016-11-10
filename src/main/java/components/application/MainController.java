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
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.command.AddWaypointAfterCommand;
import services.command.CommandManager;
import services.command.RemoveWaypointAfterCommand;
import services.map.IMapService;
import services.map.MapService;
import services.pdf.PlanningPrinter;
import services.xml.Parser;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * This class is the main controller of the application. It handles the global
 * events according to its state.
 */
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
    private CommandManager commandManager;

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
        this.generateRoadmapButton.disableProperty().bind(this.planning.isNull());

        this.root.addEventHandler(SaveDeliveryAddress.TYPE, event -> {
            AbstractWaypoint abstractWaypoint = event.getDeliveryAddress();
            int index = event.getIndex();
            AddWaypointAfterCommand addWaypointAfterCommand = new AddWaypointAfterCommand(abstractWaypoint, index, this.planning.getValue(),
                    this.cityMap.getValue());
            this.commandManager.execute(addWaypointAfterCommand);
            modifyComputePlanningButtonDisabledProperty(true);
        });

        this.root.addEventHandler(RemoveWaypointAction.TYPE, removeWaypointAction -> {
            AbstractWaypoint abstractWaypoint = removeWaypointAction.getWaypoint();
            RemoveWaypointAfterCommand removeWaypointAfterCommand = new RemoveWaypointAfterCommand(abstractWaypoint,
                    this.planning.getValue(), this.cityMap.getValue());
            this.commandManager.execute(removeWaypointAfterCommand);
            modifyComputePlanningButtonDisabledProperty(true);
        });

        this.addEventHandler(IntersectionSelectionEvent.INTERSECTION_SELECTION, this::onIntersectionSelection);

        IMapService newMapService = new MapService(this);
        this.setMapService(newMapService);
    }

    /**
     * Returns the root pane of the application.
     *
     * @return The root pane of the application.
     */
    @NotNull
    protected Parent getRoot() {
        return this.root;
    }

    /**
     * Returns the parser uses to read xml file.
     *
     * @return The parser uses to read xml file.
     */
    @NotNull
    protected Parser getParserService() {
        return this.parserService;
    }

    /**
     * @return The observable property for the currently loaded cityMap
     */
    @NotNull
    public SimpleObjectProperty<CityMap> cityMapProperty() {
        return this.cityMap;
    }

    /**
     * Returns the current loaded cityMap, or null if no cityMap has been loaded.
     *
     * @return The current loaded cityMap, or null if no cityMap has been loaded.
     */
    @Nullable
    public CityMap getCityMap() {
        return cityMap.getValue();
    }

    /**
     * Set the cityMap to the provided value.
     *
     * @param cityMap
     *            The new value of the cityMap.
     */
    public void setCityMap(CityMap cityMap) {
        this.cityMap.setValue(cityMap);
    }

    /**
     * @return The observable property for the currently loaded Planning
     */
    @NotNull
    public SimpleObjectProperty<Planning> planningProperty() {
        return this.planning;
    }

    /**
     * Returns the current planning, or null if no planning has been computed.
     *
     * @return The current planning, or null if no planning has been computed.
     */
    @Nullable
    public Planning getPlanning() {
        return planningProperty().getValue();
    }

    /**
     * Set the planning to the provided value.
     *
     * @param planning
     *            The new value of the planning.
     */
    public void setPlanning(Planning planning) {
        Platform.runLater(() -> this.planning.setValue(planning));
    }

    // TODO to comment, no idea what this method does
    public SimpleDoubleProperty mapZoomProperty() {
        return this.mapZoom;
    }

    // TODO to comment, no idea what this method does
    public double getMapZoom() {
        return mapZoomProperty().getValue();
    }

    // TODO to comment, no idea what this method does
    public void setMapZoom(double mapZoom) {
        mapZoomProperty().setValue(mapZoom);
    }

    // TODO to comment, no idea what this method does
    public ObservableObjectValue<DeliveryRequest> deliveryRequestProperty() {
        return this.deliveryRequest;
    }

    /**
     * Returns the current delivery request, or null if no delivery request has been loaded.
     *
     * @return The current delivery request, or null if no delivery request has been loaded.
     */
    public DeliveryRequest getDeliveryRequest() {
        return this.deliveryRequest.getValue();
    }

    /**
     * Set delivery request to the provided value.
     *
     * @param deliveryRequest
     *            The new value of delivery request.
     */
    public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        this.deliveryRequest.setValue(deliveryRequest);
    }

    @FXML // TODO to comment, no idea what this method does
    protected ObservableObjectValue<MainControllerState> stateProperty() {
        return this.state.getReadOnlyProperty();
    }

    /**
     * Returns a reference to this object.
     *
     * @return A reference to this object.
     */
    protected MainControllerState getState() {
        return this.state.getValue();
    }

    /**
     * Set the current state to the provided value.
     *
     * @param state
     *            The new state of the controller.
     */
    private void setState(MainControllerState state) {
        this.state.setValue(state);
    }

    /**
     * Returns the observable object for the MapService. This service is
     * used to set the active waypoint (it's used between the PlanningDetails
     * and the MapScreen).
     *
     * @return The observable object for the MapService to use.
     */
    @NotNull
    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    /**
     * @return The current value of the MapService
     */
    @NotNull
    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    /**
     * Sets a new value for the MapService.
     *
     * @param value The new value for the mapService.
     */
    protected void setMapService(@NotNull IMapService value) {
        this.mapServiceProperty().setValue(value);
    }

    /**
     * Change state to the provided state, and call the method enterState and leaveState if needed.
     *
     * @param nextState
     *            The next state of the controller.
     */
    protected void applyState(@NotNull MainControllerState nextState) {
        MainControllerState currentState = this.getState();
        if (currentState == nextState) {
            return;
        }
        currentState.leaveState();
        this.setState(nextState);
        nextState.enterState();
    }

    /**
     * Changes the text of the compute button to the provided value.
     *
     * @param text
     *            The new value of the text of the compute button.
     */
    protected void setTextToComputePlanningButton(@NotNull String text) {
        computePlanningButton.setText(text);
    }

    // ============================================= HANDLERS =========================================================

    /**
     * This method is called when the user click on the "load city map" button.
     */
    public void onOpenCityMapButtonAction() {
        this.applyState(this.getState().onOpenCityMapButtonAction());
    }

    /**
     * This method is called when the user click on the "load delivery request" button
     */
    public void onOpenDeliveryRequestButtonAction() {
        this.applyState(this.getState().onOpenDeliveryRequestButtonAction());
    }

    /**
     * This method is called when the user click on the "compute" button.
     */
    public void onComputePlanningButtonAction() {
        this.applyState(this.getState().onComputePlanningButtonAction());
    }

    /**
     * Called when a component requires the user to choose an intersection.
     *
     * @param future An empty completable future that will be resolved with
     *               the value of intersection chosen by the user.
     */
    public void onPromptIntersection(@NotNull CompletableFuture<Intersection> future) {
        this.applyState(this.getState().onPromptIntersection(future));
    }

    /**
     * Handles a click by the user on an intersection.
     *
     * @param event An object representing the event. It mainly contains the
     *              selected intersection.
     */
    public void onIntersectionSelection(@NotNull IntersectionSelectionEvent event) {
        this.applyState(this.getState().onIntersectionSelection(event));
    }

    /**
     * This method is called when the user click on the "undo" button
     */
    public void onUndoButtonAction() {
        commandManager.undo();
    }

    /**
     * This method is called when the user click on the "redo" button
     */
    public void onRedoButtonAction() {
        commandManager.redo();
    }

    /**
     * Asks the user for a path to the file, then generate the delivery itinerary in a pdf saved to the path provided by the user.
     */
    public void onGenerateRoadmapButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose planning directory");
        fileChooser.setInitialFileName("Planning.pdf");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            PlanningPrinter.generatePdfFromPlanning(this.planning.getValue(), file.getPath());
        }
    }

    /**
     * Enable or disable compute button according to the provided value.
     * @param disable The wanted state of the compute button.
     */
    public void modifyComputePlanningButtonDisabledProperty(boolean disable) {
        if (this.computePlanningButton.isDisable() != disable) {
            this.computePlanningButton.setDisable(disable);
        }
    }

    /**
     * Clear all commands undoable or redoable.
     */
    public void changeCommandManager() {
        this.commandManager = new CommandManager();
        this.undoButton.disableProperty().bind(this.commandManager.undoableProperty().not());
        this.redoButton.disableProperty().bind(this.commandManager.redoableProperty().not());
    }
}
