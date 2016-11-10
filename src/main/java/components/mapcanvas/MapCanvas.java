package components.mapcanvas;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.*;
import services.map.IMapService;
import services.map.MapRenderer;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * The canvas in which all the map will be draw, the planning and the deliveries will also be show here.
 *
 */
public class MapCanvas extends Canvas {
    private static final CityMap DEFAULT_CITY_MAP = null;
    private static final DeliveryRequest DEFAULT_DELIVERY_REQUEST = null;
    private static final Planning DEFAULT_PLANNING = null;
    private static final double DEFAULT_ZOOM = 1.0;
    private static final double DEFAULT_OFFSET_X = 8.0;
    private static final double DEFAULT_OFFSET_Y = 8.0;
    private static final int MARGIN_ERROR = 0;
    private static final double DEFAULT_INTERSECTION_SIZE = 10;
    private static final double DEFAULT_DELIVERY_SIZE = 18;

    private SimpleDoubleProperty zoom;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private SimpleObjectProperty<CityMap> cityMap;
    private SimpleObjectProperty<DeliveryRequest> deliveryRequest;
    private SimpleObjectProperty<Planning> planning;
    private List<Intersection> intersections;
    private Iterable<DeliveryAddress> listDeliveryAddresses;
    private double calZoom;
    private final SimpleObjectProperty<IMapService> mapService = new SimpleObjectProperty<>(this, "mapService", null);
    private final ListChangeListener<PlanningWaypoint> planningChangeListener;

    /**
     * Constructor of the map canvas, the zone in which the map will be drawn.
     */
    @SuppressWarnings("restriction")
    public MapCanvas() {
        final MapCanvas self = this;

        //Accord eachproperty to the appropriate listener : the draw function of the map.
        this.planningChangeListener = change -> self.draw();
        widthProperty().addListener(event -> draw());
        heightProperty().addListener(event -> draw());
        zoomProperty().addListener(event -> draw());
        offsetXProperty().addListener(event -> draw());
        offsetYProperty().addListener(event -> draw());
        cityMapProperty().addListener(event -> draw());
        deliveryRequestProperty().addListener(event -> draw());
        this.mapServiceProperty().addListener(this::onMapServiceChange);

        /** Add a listener to the planning property to get any changes it might undergo.
         * 
         */
        planningProperty().addListener((observableValue, oldPlanning, newPlanning) -> {
            if (oldPlanning != null) {
                oldPlanning.planningWaypointsProperty().removeListener(self.planningChangeListener);
            }
            if (newPlanning != null) {
                newPlanning.planningWaypointsProperty().addListener(self.planningChangeListener);
            }
            self.draw();
        });

        /**Add an event handler class to the canvas in order to 
         * @param mouseEvent
         * @param 
         */
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            /**The handle method of the handler. Triggers event according to the type of the place the mouse has clicked.
             * 
             * @param e - The mouseEvent we handle : we get his coordinates and determines whether it is or not an intersection, 
             * a warehouse or a delivery address. We need first to transform its coordinates according to the current size of the map, 
             * the zoom factor and the translation she underwent.
             */
            public void handle(MouseEvent e) {
                double eventX = e.getX();
                double eventY = e.getY();
                eventX /= calZoom;
                eventY /= calZoom;
                eventX += DEFAULT_OFFSET_X;
                eventY += DEFAULT_OFFSET_Y;
                
                //First check if it is the deliveryRequest has been loaded
                if(getDeliveryRequest() != null){
                	//First check if the location is the warehouse, if it is, make other tooltips disappear
                	Warehouse warehouse = getDeliveryRequest().getWarehouse();
                	if (eventX < warehouse.getX() + DEFAULT_DELIVERY_SIZE / 2 && eventX > warehouse.getX() - DEFAULT_DELIVERY_SIZE / 2
                            && eventY < warehouse.getY() + DEFAULT_DELIVERY_SIZE / 2 && eventY > warehouse.getY() - DEFAULT_DELIVERY_SIZE / 2){
                		WarehouseSelectionEvent warehouseEvent = new WarehouseSelectionEvent(warehouse, e.getX(), e.getY());
                        fireEvent(warehouseEvent);
                        DeliverySelectionEvent nullDeliver = new DeliverySelectionEvent(null, 0, 0);
                        fireEvent(nullDeliver);
                        IntersectionSelectionEvent nullIntersect = new IntersectionSelectionEvent(null, 0, 0);
                        fireEvent(nullIntersect);
                        return;
                	}
                	//Then check if the location is a delivery Request, if it is, make other tooltips disappear
                    listDeliveryAddresses = getDeliveryRequest().getDeliveryAddresses();
                    for(DeliveryAddress delivery : listDeliveryAddresses){
                        if (eventX < delivery.getX() + DEFAULT_DELIVERY_SIZE / 2 && eventX > delivery.getX() - DEFAULT_DELIVERY_SIZE / 2
                            && eventY < delivery.getY() + DEFAULT_DELIVERY_SIZE / 2 && eventY > delivery.getY() - DEFAULT_DELIVERY_SIZE / 2) {
                            DeliverySelectionEvent deliver = new DeliverySelectionEvent(delivery, e.getX(), e.getY());
                            fireEvent(deliver);
                            IntersectionSelectionEvent nullIntersect = new IntersectionSelectionEvent(null, 0, 0);
                            fireEvent(nullIntersect);
                            WarehouseSelectionEvent nullWarehouse = new WarehouseSelectionEvent(null, 0, 0);
                            fireEvent(nullWarehouse);
                            return;
                        }
                    }
                }
                //Finally, check if the location is an Intersection, if it is, make other tooltips disappear
                for (Intersection inter : intersections) {
                    if (eventX < inter.getX() + DEFAULT_INTERSECTION_SIZE / 2 && eventX > inter.getX() - DEFAULT_INTERSECTION_SIZE / 2
                        && eventY < inter.getY() + DEFAULT_INTERSECTION_SIZE / 2 && eventY > inter.getY() - DEFAULT_INTERSECTION_SIZE / 2) {
                        IntersectionSelectionEvent intersect = new IntersectionSelectionEvent(inter, e.getX(), e.getY());
                        fireEvent(intersect);
                        DeliverySelectionEvent nullDeliver = new DeliverySelectionEvent(null, 0, 0);
                        fireEvent(nullDeliver);
                        WarehouseSelectionEvent nullWarehouse = new WarehouseSelectionEvent(null, 0, 0);
                        fireEvent(nullWarehouse);
                        return;
                    }
                }
                IntersectionSelectionEvent intersect = new IntersectionSelectionEvent(null, e.getX(), e.getY());
                fireEvent(intersect);
            }
        });
    }

    @SuppressWarnings("restriction")
    /**Clear the zone where the map is drawn.
     * 
     */
    private void clear() {
        double width = getWidth();
        double height = getHeight();
        GraphicsContext gc = getGraphicsContext2D();
        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.clearRect(0, 0, width, height);
    }

    @SuppressWarnings("restriction")
    /**Update the zoomfactor, the offset x and the offset y of the map, also resize the map in order 
     * to fit it in the zone seen by the user.
     * 
     */
    private void updateTransform() {
        double width = getWidth();
        double height = getHeight();
        GraphicsContext gc = getGraphicsContext2D();
        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.clearRect(0, 0, width, height);

        CityMap map = getCityMap();
        intersections = map.getIntersections();

        double xmax = 0;
        double ymax = 0;
        double xmin = 0;
        double ymin = 0;

        for (Intersection inter : intersections) {
            xmax = Math.max(xmax, inter.getX());
            ymax = Math.max(ymax, inter.getY());
            xmin = Math.min(xmin, inter.getX());
            ymin = Math.min(ymin, inter.getY());
        }

        double mapWidth = xmax - xmin;
        double mapHeight = ymax - ymin;

        double zoomX = width / mapWidth;
        double zoomY = height / mapHeight;

        double zoom = Math.min(zoomX, zoomY);

        gc.scale(zoom, zoom);
        calZoom = zoom;

        gc.translate(-xmin - DEFAULT_OFFSET_X, -ymin - DEFAULT_OFFSET_Y); //offset
    }


    @SuppressWarnings("restriction")
    /**Check if the deliveryRequest and the Planning are loaded and draw the in the canvas if so.
     * 
     */
    private void draw() {
        clear();

        if (getCityMap() == null) {
            return;
        }

        updateTransform();

        MapRenderer mapRenderer = new MapRenderer();
        GraphicsContext gc = this.getGraphicsContext2D();

        mapRenderer.drawCityMap(gc, this.getCityMap());
        if (this.getPlanning() != null) {
            mapRenderer.drawPlanning(gc, this.getPlanning());
        } else if (this.getDeliveryRequest() != null) {
            mapRenderer.drawDeliveryRequest(gc, this.getDeliveryRequest());
        }
    }

    /**return if the map is resizable or not.
     * @return isresizable a boolean (true if resizable and false if not).
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**Get the preferred width of the mapscreen.
     * 
     * @return width
     */
    @Override
    public double prefWidth(double width) {
        return width;
    }

    /**Get the preferred Height of the mapscreen.
     * 
     * @return height
     */
    @Override
    public double prefHeight(double height) {
        return height;
    }

    /**
     * The cityMap to display
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
     * Set the city map
     *
     * @param value
     */
    public final void setCityMap(CityMap value) {
        cityMapProperty().setValue(value);
    }

    /**
     * 
     * @return
     */
    public final CityMap getCityMap() {
        return cityMap == null ? DEFAULT_CITY_MAP : cityMap.getValue();
    }

    /**
     * Get the deliveryRequest property of the map;
     *
     * @return deliveryRequestproperty - The property of the current delivery request
     */
    public final SimpleObjectProperty<DeliveryRequest> deliveryRequestProperty() {
        if (deliveryRequest == null) {
            deliveryRequest = new SimpleObjectProperty<>(this, "deliveryRequest", DEFAULT_DELIVERY_REQUEST);
        }
        return deliveryRequest;
    }

    /**
     * Set the current value of the delivery request.
     *
     * @param deliveryRequest - The new value of the delivery request. 
     */
    public final void setDeliveryRequest(DeliveryRequest value) {
        deliveryRequestProperty().setValue(value);
    }

    /**Get the current deliveryRequest of the map. 
     * 
     * @return deliveryRequest - The delivery request currently loaded in the map
     */
    public final DeliveryRequest getDeliveryRequest() {
        return deliveryRequest == null ? DEFAULT_DELIVERY_REQUEST : deliveryRequest.getValue();
    }

    /**
     * Get the current planningProperty of the map. 
     *
     * @return planningProperty - The planning property
     */
    public final SimpleObjectProperty<Planning> planningProperty() {
        if (planning == null) {
            planning = new SimpleObjectProperty<>(this, "planning", DEFAULT_PLANNING);
        }
        return planning;
    }

    /**
     * Set the value of the current Planning
     *
     * @param value - The new value of the planning.
     */
    public final void setPlanning(Planning value) {
        planningProperty().bind(new SimpleObjectProperty<Planning>(value));
    }

    /**Get the currentPlanning contained by the map.
     * 
     * @return planning - The planning of the client's delivery.
     */
    public final Planning getPlanning() {
        return planning == null ? DEFAULT_PLANNING : planning.getValue();
    }

    /**
     * The zoom factor to use.
     *
     * @return zoomProperty - The zoom property.
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
        zoomProperty().bind(new SimpleDoubleProperty(value));
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
    
    /**Return the property of the interface MapService
     * 
     * @return offset Y
     */
    public SimpleObjectProperty<IMapService> mapServiceProperty() {
        return this.mapService;
    }

    /** Return the interface of the serviceMap 
     * 
     * @return
     */
    public IMapService getMapService() {
        return this.mapServiceProperty().getValue();
    }

    /** Set the interface of the serviceMap
     * 
     * @param value the interface of the serviceMap
     */
    public void setMapService(IMapService value) {
        this.mapServiceProperty().setValue(value);
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
    		double eventX = newValue.getX() - DEFAULT_OFFSET_X ;
            double eventY = newValue.getY() - DEFAULT_OFFSET_Y ;
            eventX *= calZoom;
            eventY *= calZoom;
    		if(getDeliveryRequest() != null){
            	Warehouse warehouse = getDeliveryRequest().getWarehouse();
            	if (warehouse.getId() == newValue.getId()){
            		WarehouseSelectionEvent warehouseEvent = new WarehouseSelectionEvent(warehouse, eventX, eventY);
                    fireEvent(warehouseEvent);
                    DeliverySelectionEvent nullDeliver = new DeliverySelectionEvent(null, newValue.getX(), newValue.getY());
                    fireEvent(nullDeliver);
                    IntersectionSelectionEvent nullIntersect = new IntersectionSelectionEvent(null, newValue.getX(), newValue.getY());
                    fireEvent(nullIntersect);
                    return;
            	}
                listDeliveryAddresses = getDeliveryRequest().getDeliveryAddresses();
                for(DeliveryAddress delivery : listDeliveryAddresses){
                    if (delivery.getId() == newValue.getId()) {
                        DeliverySelectionEvent deliver = new DeliverySelectionEvent(delivery, eventX, eventY);
                        fireEvent(deliver);
                        IntersectionSelectionEvent nullIntersect = new IntersectionSelectionEvent(null, newValue.getX(), newValue.getY());
                        fireEvent(nullIntersect);
                        WarehouseSelectionEvent nullWarehouse = new WarehouseSelectionEvent(null, newValue.getX(), newValue.getY());
                        fireEvent(nullWarehouse);
                        return;
                    }
                }
            }
    }
}
