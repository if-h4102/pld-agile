package components.mapscreen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import models.AbstractWaypoint;
import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import models.Warehouse;
import services.map.IMapService;
import services.map.MapService;
import components.mapcanvas.DeliverySelectionEvent;
import components.mapcanvas.IntersectionSelectionEvent;

import java.awt.Point;
import java.io.IOException;

import components.waypointcard.DeliveryAddressCard;
import components.waypointcard.WarehouseCard;
import components.waypointcard.WaypointCard;
import components.intersectioncard.IntersectionCard;
import components.mapcanvas.MapCanvas;
import components.mapcanvas.WarehouseSelectionEvent;

/**
 * This class wraps the canvas and the tooltips and show the dynamic view of the cityMap (intersection when click on it, 
 * delivery address when click on it, warehouse when click on it)
 *
 */
public class MapScreen extends AnchorPane {
    private static final CityMap DEFAULT_CITY_MAP = null;
    private static final DeliveryRequest DEFAULT_DELIVERY_REQUEST = null;
    private static final Planning DEFAULT_PLANNING = null;
    private static final double DEFAULT_ZOOM = 1.0;
    private static final double DEFAULT_OFFSET_X = 0.0;
    private static final double DEFAULT_OFFSET_Y = 0.0;

    @FXML
    protected IntersectionCard tooltip;
    @FXML
    protected DeliveryAddressCard tooltipDelivery;
    @FXML
    protected WarehouseCard tooltipwarehouse;
    @FXML
    protected MapCanvas canvas;
    private SimpleDoubleProperty zoom;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private SimpleObjectProperty<CityMap> cityMap;
    private SimpleObjectProperty<DeliveryRequest> deliveryRequest;
    private SimpleObjectProperty<Planning> planning;
    private SimpleObjectProperty<Intersection> activeIntersection;
    private SimpleObjectProperty<DeliveryAddress> activeDelivery;
    private SimpleObjectProperty<Warehouse> activeWarehouse;
    private final SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this, "mapService", null);

    @SuppressWarnings("restriction")
    /**Constructor of the class MapScreen, load the associate fxml file and add the handlers 
     * to the different types of event that can be triggered by the canvas. Also bind the tooltip to the 
     * Single Objects property active[warehouse/delivery/intersection] in order to hide them when launching 
     * the MapScreeen for the first time
     * 
     */
    public MapScreen() {
    	//load the fxml associate files
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/mapscreen/MapScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //Add handler to WarehouseSelectionEvent
        canvas.addEventHandler(WarehouseSelectionEvent.WAREHOUSE_SELECTION, event -> {
            updateWarehouseTooltip(event);
        });
        
        //Add handler to IntersectionSelectionEvent
        canvas.addEventHandler(IntersectionSelectionEvent.INTERSECTION_SELECTION, event -> {
            updateIntersectionTooltip(event);
        });
        
        //Add handler to DeliverySelectionEvent
        canvas.addEventHandler(DeliverySelectionEvent.DELIVERY_SELECTION, event -> {
            updateDeliveryTooltip(event);
        });

        //bind the properties to the corresponding tooltips.
        tooltipwarehouse.visibleProperty().bind(activeWarehouse.isNotNull());
        tooltipDelivery.visibleProperty().bind(activeDelivery.isNotNull());
        tooltip.visibleProperty().bind(activeIntersection.isNotNull());
        this.mapServiceProperty().addListener(this::onMapServiceChange);
    }

    /**
     * Create the accessible cityMap for the mapScreen
     *
     * @return The cityMap property
     */
    public final SimpleObjectProperty<CityMap> cityMapProperty() {
        if (cityMap == null) {
            cityMap = new SimpleObjectProperty<>(this, "cityMap", DEFAULT_CITY_MAP);
        }
        return cityMap;
    }

    /**
     * Set the city map to a new value
     *
     * @param value - The new cityMap to display
     */
    public final void setCityMap(CityMap value) {
        cityMapProperty().setValue(value);
    }

    /**Get the value of the current CityMap
     * 
     * @return cityMap - The current cityMap
     */
    public final CityMap getCityMap() {
        return cityMap == null ? DEFAULT_CITY_MAP : cityMap.getValue();
    }

    /**
     * Create the accessible DeliveryRequest for the mapScreen
     *
     * @return The deliveryrequest property	
     */
    public final SimpleObjectProperty<DeliveryRequest> deliveryRequestProperty() {
        if (deliveryRequest == null) {
            deliveryRequest = new SimpleObjectProperty<>(this, "deliveryRequest", DEFAULT_DELIVERY_REQUEST);
        }
        return deliveryRequest;
    }

    /**
     * Set the delivery request to a new value.
     *
     * @param value - The new delivery request
     */
    public final void setDeliveryRequest(DeliveryRequest value) {
        deliveryRequestProperty().setValue(value);
    }

    /** Get the value of the current delivery Request .
     * 
     * @return deliveryrequest - The delivery request
     */
    public final DeliveryRequest getDeliveryRequest() {
        return deliveryRequest == null ? DEFAULT_DELIVERY_REQUEST : deliveryRequest.getValue();
    }

    /**
     * Create the accessible Planning for the mapScreen.
     *
     * @return The planning property
     */
    public final SimpleObjectProperty<Planning> planningProperty() {
        if (planning == null) {
            planning = new SimpleObjectProperty<>(this, "planning", DEFAULT_PLANNING);
        }
        return planning;
    }

    /**
     * Set the planning to a new value.
     *
     * @param value - The newvalue of the planning.
     */
    public final void setPlanning(Planning value) {
        planningProperty().setValue(value);
    }

    /** Get the value of the current planning. 
     * 
     * @return planning - The planning.
     */
    public final Planning getPlanning() {
        return planning == null ? DEFAULT_PLANNING : planning.getValue();
    }

    /**
     * Create the accessible active interserctionfor the mapScreen
     *      *
     * @return The intersection property
     */
    public final SimpleObjectProperty<Intersection> activeIntersectionProperty() {
        if (activeIntersection == null) {
            activeIntersection = new SimpleObjectProperty<>(this, "activeIntersection", null);
        }
        return activeIntersection;
    }

    /**
     * Set the accessible intersection to a new value.
     *
     * @param value - The newvalue of the intersection.
     */
    public final void setActiveIntersection(Intersection value) {
        activeIntersectionProperty().setValue(value);
    }

    /** Get the value of the current active intersection.. 
     * 
     * @return intersection - The active intersection.
     */
    public final Intersection getActiveIntersection() {
        return activeIntersection == null ? null : activeIntersectionProperty().getValue();
    }

    /**
     * Update the position of the Intersection tooltip when clicked on intersection.
     * If the event contains a intersection which is null, make the tooltip disappear.
     */
    public void updateIntersectionTooltip (IntersectionSelectionEvent event) {
        tooltip = tooltipOptimalPosition(tooltip, event.getX(),event.getY());
        setActiveIntersection(event.getIntersection());
    }

    /**
     * Create the accessible active Delivery address for the mapScreen.
     *      *
     * @return The Delivery address property.
     */
    public final SimpleObjectProperty<DeliveryAddress> activeDeliveryProperty() {
        if (activeDelivery == null) {
            activeDelivery = new SimpleObjectProperty<>(this, "activeDelivery", null);
        }
        return activeDelivery;
    }

    /**
     * Set the accessible delivery address to a new value.
     *
     * @param value - The newvalue of the accessible delivery address.
     */
    public final void setActiveDelivery(DeliveryAddress value) {
        activeDeliveryProperty().setValue(value);
    }

    /**
     * Get the value of the current active delivery address.
     *
     * @return value - The accessible delivery address.
     */
    public final DeliveryAddress getActiveDelivery() {
        return activeDelivery == null ? null : activeDeliveryProperty().getValue();
    }
    
    /**
     * Update the position of the Delivery tooltip when clicked on a delivery address.
     * If the event contains a delivery which is null, make the tooltip disappear.
     */
    public void updateDeliveryTooltip (DeliverySelectionEvent event) {
        tooltipDelivery = tooltipDeliveryOptimalPosition(tooltipDelivery, event.getX(),event.getY());
        setActiveDelivery(event.getDeliveryAddress());
    }

    /**
     * Create the accessible active Warehouse for the mapScreen.
     *      *
     * @return The Warehouse property.
     */
    public final SimpleObjectProperty<Warehouse> activeWarehouseProperty() {
        if (activeWarehouse == null) {
            activeWarehouse = new SimpleObjectProperty<>(this, "activeWarehouse", null);
        }
        return activeWarehouse;
    }

    /**
     * Set the accessible warehouse to a new value.
     *
     * @param value - The newvalue of the accessible delivery address.
     */
    public final void setActiveWarehouse(Warehouse value) {
        activeWarehouseProperty().setValue(value);
    }

    /**
     * Get the value of the current active warehouse.
     *
     * @return value - The accessible warehouse.
     */
    public final Warehouse getActiveWarehouse() {
        return activeWarehouse == null ? null : activeWarehouseProperty().getValue();
    }

    /**
     * Update the position of the Warehouse tooltip when clicked on the warehouse.
     * If the event contains a warehouse which is null, make the tooltip disappear.
     */
    public void updateWarehouseTooltip (WarehouseSelectionEvent event) {
        tooltipwarehouse = tooltipWarehouseOptimalPosition(tooltipwarehouse, event.getX(),event.getY());
        setActiveWarehouse(event.getWarehouse());
    }

    // mapService
    /**Create the accessible MapService interface for the mapScreen.
     * 
     * @return mapServiceProperty
     */
    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    /**
     * Get the value of the mapServiceInterface.
     *
     * @return value - The Interface of the map service.
     */
    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    /**
     * Set the value of the mapServiceInterface.
     * 
     * @param value - The new Interface of the map service.
     */
    public void setMapService(IMapService value) {
        this.mapServiceProperty().setValue(value);
    }

    /**
     * Find the optimal origin for the warehouse tooltip in order to display him in the canvas zone.
     *
     * @return The tooltip
     */
    public WarehouseCard tooltipWarehouseOptimalPosition(WarehouseCard tooltipWarehouse, double x, double y){
        Point optimal = optimalPosition(tooltipWarehouse.getHeight(),tooltipWarehouse.getWidth(),x,y );
    	tooltipWarehouse.setLayoutX(optimal.getX());
    	tooltipWarehouse.setLayoutY(optimal.getY());
        return tooltipWarehouse;
    }

    /**Find the optimal positionof the tooltip according to the borders of the canvas zone.
     * 
     * @param height - The height of the tooltip
     * @param width - The widthof the tooltip
     * @param x - the position along the X axis of the event (either can be the deliveryAddress/Warehouse x coordinate or the MouseEvent x coordinate)
     * @param y - the position along the Y axis of the event (either can be the deliveryAddress/Warehouse y coordinate or the MouseEvent y coordinate)
     * @return the optimal position to display the tooltip
     */
    public Point optimalPosition(double height, double width, double x, double y){
    	double h = canvas.getHeight();
        double w = canvas.getWidth();
        if(x+width > w){
        	x = x - width - 5;
        }
        else {
        	x = x + 5;
        }
        if(y+height > h){
        	y = y - height - 5;
        }
        else{
        	y = y + 5;
        }
        Point position = new Point();
        position.setLocation(x, y);
        return position;
    }


    /**
     * Find the optimal origin for the Intersection tooltip in order to display him in the canvas zone
     *
     * @return The tooltip
     */
    public IntersectionCard tooltipOptimalPosition(IntersectionCard tooltip, double x, double y){
    	Point optimal = optimalPosition(tooltip.getHeight(),tooltip.getWidth(),x,y );
     	tooltip.setLayoutX(optimal.getX());
     	tooltip.setLayoutY(optimal.getY());
        return tooltip;
    }

    /**
     * Find the optimal origin for the deliveryaddress tooltip in order to display him in the canvas zone
     *
     * @return The tooltip
     */
    public DeliveryAddressCard tooltipDeliveryOptimalPosition(DeliveryAddressCard tooltipDelivery, double x, double y){
    	 Point optimal = optimalPosition(tooltipDelivery.getHeight(),tooltipDelivery.getWidth(),x,y );
     	tooltipDelivery.setLayoutX(optimal.getX());
     	tooltipDelivery.setLayoutY(optimal.getY());
         return tooltipDelivery;
    }

    /**
     * The zoom factor to use.
     *
     * @return zoomProperty - The zoom property
     */
    public final DoubleProperty zoomProperty() {
        if (zoom == null) {
            zoom = new SimpleDoubleProperty(this, "zoom", DEFAULT_ZOOM);
        }
        return zoom;
    }

    /**
     * Set the zoom factor of the map
     *
     * @param value the value of the zoom.
     */
    public final void setZoom(double value) {
        zoomProperty().setValue(value);
    }

    /**Return the zoom factor of the map.
     * 
     * @return Value of the zoom.
     */
    public final double getZoom() {
        return zoom == null ? DEFAULT_ZOOM : zoom.getValue();
    }

    /**
     * Return the offsetX property of the map.
     *
     * @return offsetXProperty which enable dynamic modifications.
     */
    public final DoubleProperty offsetXProperty() {
        if (offsetX == null) {
            offsetX = new SimpleDoubleProperty(this, "offsetX", DEFAULT_OFFSET_X);
        }
        return offsetX;
    }

    /**
     * Set the offset x of the map
     *
     * @param offsetX : the translation along the x-axis the map undergo on the screen.
     */
    public final void setOffsetX(double value) {
        offsetXProperty().setValue(value);
    }

    /**Return the offset x of the map
 	* 
 	* @return offsetX : the translation along the x-axis the map undergo on the screen.
 	*/
    public final double getOffsetX() {
        return offsetX == null ? DEFAULT_OFFSET_X : offsetX.getValue();
    }

    /**
     * The offset y of the map
     *
     * @return offsetY : the translation along the x-axis the map undergo on the screen.
     */
    public final DoubleProperty offsetYProperty() {
        if (offsetY == null) {
            offsetY = new SimpleDoubleProperty(this, "offsetY", DEFAULT_OFFSET_Y);
        }
        return offsetY;
    }

    /**
     * Set the offset y of the map
     *
     * @param value offsetY value
     */
    public final void setOffsetY(double value) {
        offsetYProperty().setValue(value);
    }

    /**Return the offset x of the map
 	* 
 	* @return offset X
 	*/
    public final double getOffsetY() {
        return offsetY == null ? DEFAULT_OFFSET_Y : offsetY.getValue();
    }

    /** Receive the announce of the MapService changes and add a listener to the new value of the mapService Interface.
     * 
     * @param observable
     * @param oldValue
     * @param newValue
     */
    protected void onMapServiceChange(ObservableValue<? extends IMapService> observable, IMapService oldValue, IMapService newValue) {
        if (oldValue == newValue) {
            return;
        }
        if (oldValue != null) {
            oldValue.activeWaypointProperty().removeListener(this::onActiveWaypointChange);
        }
        if (newValue != null) {
            newValue.activeWaypointProperty().addListener(this::onActiveWaypointChange);
        }
    }

    /** Receive the announce of the MapService changes and add a listener to the new value of the active Waypoint.
     * 
     * @param observable
     * @param oldValue
     * @param newValue
     */
    protected void onActiveWaypointChange(ObservableValue<? extends AbstractWaypoint> observable, AbstractWaypoint oldValue, AbstractWaypoint newValue) {
    		
    }


}
