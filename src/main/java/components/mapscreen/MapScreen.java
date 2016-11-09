package components.mapscreen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;
import models.Intersection;
import models.Planning;
import models.Warehouse;
import components.mapcanvas.DeliverySelectionEvent;
import components.mapcanvas.IntersectionSelectionEvent;

import java.awt.Point;
import java.io.IOException;

import components.waypointcard.DeliveryAddressCard;
import components.waypointcard.WarehouseCard;
import components.intersectioncard.IntersectionCard;
import components.mapcanvas.MapCanvas;
import components.mapcanvas.WarehouseSelectionEvent;

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
    

    @SuppressWarnings("restriction")
    public MapScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/mapscreen/MapScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        canvas.addEventHandler(WarehouseSelectionEvent.WAREHOUSE_SELECTION, event -> {
            System.out.println("handler intersection");
            updateWarehouseTooltip(event);
        });
        
        canvas.addEventHandler(IntersectionSelectionEvent.INTERSECTION_SELECTION, event -> {
            System.out.println("handler intersection");
            updateIntersectionTooltip(event);
        });

        canvas.addEventHandler(DeliverySelectionEvent.DELIVERY_SELECTION, event -> {
            System.out.println("handler delivery address");
            updateDeliveryTooltip(event);
        });
        
        
        tooltipwarehouse.visibleProperty().bind(activeWarehouse.isNotNull());
        tooltipDelivery.visibleProperty().bind(activeDelivery.isNotNull());
        tooltip.visibleProperty().bind(activeIntersection.isNotNull());
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

    public final CityMap getCityMap() {
        return cityMap == null ? DEFAULT_CITY_MAP : cityMap.getValue();
    }

    /**
     * The deliveryRequest with waypoints to display
     *
     * @return The cityMap property
     */
    public final SimpleObjectProperty<DeliveryRequest> deliveryRequestProperty() {
        if (deliveryRequest == null) {
            deliveryRequest = new SimpleObjectProperty<>(this, "deliveryRequest", DEFAULT_DELIVERY_REQUEST);
        }
        return deliveryRequest;
    }

    /**
     * Set the delivery request
     *
     * @param value
     */
    public final void setDeliveryRequest(DeliveryRequest value) {
        deliveryRequestProperty().setValue(value);
    }

    public final DeliveryRequest getDeliveryRequest() {
        return deliveryRequest == null ? DEFAULT_DELIVERY_REQUEST : deliveryRequest.getValue();
    }

    /**
     * The cityMap to display
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
     * Set the planning
     *
     * @param value
     */
    public final void setPlanning(Planning value) {
        planningProperty().setValue(value);
    }

    public final Planning getPlanning() {
        return planning == null ? DEFAULT_PLANNING : planning.getValue();
    }

    /**
     * The active interserction
     *      *
     * @return The cityMap property
     */
    public final SimpleObjectProperty<Intersection> activeIntersectionProperty() {
        if (activeIntersection == null) {
            activeIntersection = new SimpleObjectProperty<>(this, "activeIntersection", null);
        }
        return activeIntersection;
    }

    /**
     * Set the active intersection
     *
     * @param value
     */
    public final void setActiveIntersection(Intersection value) {
        activeIntersectionProperty().setValue(value);
    }

    public final Intersection getActiveIntersection() {
        return activeIntersection == null ? null : activeIntersectionProperty().getValue();
    }

    /**
     * Update the position of the tooltip when clicked on intersection
     *
     */
    public void updateIntersectionTooltip (IntersectionSelectionEvent event) {
        tooltip = tooltipOptimalPosition(tooltip, event.getX(),event.getY());
        setActiveIntersection(event.getIntersection());
    }

    /**
     * The active delivery address
     *      *
     * @return The cityMap property
     */
    public final SimpleObjectProperty<DeliveryAddress> activeDeliveryProperty() {
        if (activeDelivery == null) {
            activeDelivery = new SimpleObjectProperty<>(this, "activeDelivery", null);
        }
        return activeDelivery;
    }

    /**
     * set the active delivery address
     *
     * @param value
     */
    public final void setActiveDelivery(DeliveryAddress value) {
        activeDeliveryProperty().setValue(value);
    }

    public final DeliveryAddress getActiveDelivery() {
        return activeDelivery == null ? null : activeDeliveryProperty().getValue();
    }
    /**
     * Update the position of the delivery tooltip
     *
     */
    public void updateDeliveryTooltip (DeliverySelectionEvent event) {
        tooltipDelivery = tooltipDeliveryOptimalPosition(tooltipDelivery, event.getX(),event.getY());
        setActiveDelivery(event.getDeliveryAddress());
    }
    
    /**
     * The active delivery address
     *      *
     * @return The cityMap property
     */
    public final SimpleObjectProperty<Warehouse> activeWarehouseProperty() {
        if (activeWarehouse == null) {
            activeWarehouse = new SimpleObjectProperty<>(this, "activeWarehouse", null);
        }
        return activeWarehouse;
    }

    /**
     * set the active warehouse
     *
     * @param value
     */
    public final void setActiveWarehouse(Warehouse value) {
        activeWarehouseProperty().setValue(value);
    }

    public final Warehouse getActiveWarehouse() {
        return activeWarehouse == null ? null : activeWarehouseProperty().getValue();
    }
    
    /**
     * Update the position of the warehouse tooltip
     *
     */
    public void updateWarehouseTooltip (WarehouseSelectionEvent event) {
        tooltipwarehouse = tooltipWarehouseOptimalPosition(tooltipwarehouse, event.getX(),event.getY());
        setActiveWarehouse(event.getWarehouse());
    }
    
    /**
     * Find the optimal origin for the tooltip
     *
     * @return The tooltip
     */
    public WarehouseCard tooltipWarehouseOptimalPosition(WarehouseCard tooltipWarehouse, double x, double y){
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        double htool = tooltipWarehouse.getHeight();
        double wtool = tooltipWarehouse.getWidth();
        if(x+wtool > w){
        	tooltipWarehouse.setLayoutX(x-wtool-5);
        }
        else {
        	tooltipWarehouse.setLayoutX(x+5);
        }
        if(y+htool > h){
        	tooltipWarehouse.setLayoutY(y-htool-5);
        }
        else{
        	tooltipWarehouse.setLayoutY(y+5);
        }

        return tooltipWarehouse;
    }


    /**
     * Find the optimal origin for the tooltip
     *
     * @return The best point
     */
    public IntersectionCard tooltipOptimalPosition(IntersectionCard tooltip, double x, double y){
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        double htool = tooltip.getHeight();
        double wtool = tooltip.getWidth();
        if(x+wtool > w){
            tooltip.setLayoutX(x-wtool-5);
        }
        else {
            tooltip.setLayoutX(x+5);
        }
        if(y+htool > h){
            tooltip.setLayoutY(y-htool-5);
        }
        else{
            tooltip.setLayoutY(y+5);
        }

        return tooltip;
    }

    /**
     * Find the optimal origin for the tooltip
     *
     * @return The best point
     */
    public DeliveryAddressCard tooltipDeliveryOptimalPosition(DeliveryAddressCard tooltipDelivery, double x, double y){
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        double htool = tooltipDelivery.getHeight();
        double wtool = tooltipDelivery.getWidth();
        if(x+wtool > w){
            tooltipDelivery.setLayoutX(x-wtool-5);
        }
        else {
            tooltipDelivery.setLayoutX(x+5);
        }
        if(y+htool > h){
            tooltipDelivery.setLayoutY(y-htool-5);
        }
        else{
            tooltipDelivery.setLayoutY(y+5);
        }

        return tooltipDelivery;
    }

    /**
     * The zoom factor to use.
     *
     * @return The zoom property
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
     * @param value
     */
    public final void setZoom(double value) {
        zoomProperty().setValue(value);
    }

    public final double getZoom() {
        return zoom == null ? DEFAULT_ZOOM : zoom.getValue();
    }

    /**
     * The offsetX of the map
     *
     * @return offsetX property
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
     * @param value offsetX value
     */
    public final void setOffsetX(double value) {
        offsetXProperty().setValue(value);
    }

    public final double getOffsetX() {
        return offsetX == null ? DEFAULT_OFFSET_X : offsetX.getValue();
    }

    /**
     * The offset y of the map
     *
     * @return offsetY property
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

    public final double getOffsetY() {
        return offsetY == null ? DEFAULT_OFFSET_Y : offsetY.getValue();
    }

    public void onIntersection() {
        //this.getChildren().add()
    }
}
