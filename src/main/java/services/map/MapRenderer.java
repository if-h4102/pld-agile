package services.map;

import com.google.java.contract.Requires;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.*;

import java.util.List;

public class MapRenderer {
    public static final double INTERSECTION_SIZE = 10;
    public static final double WAYPOINT_SIZE = 18;

    private CityMap cityMap;
    private DeliveryRequest deliveryRequest;
    private Planning planning;

    public MapRenderer () {}

    @Requires("cityMap != null")
    public void drawCityMap(GraphicsContext gc, CityMap cityMap) {
        gc.setFill(Color.BLACK);
        List<StreetSection> streetSections = cityMap.getStreetSections();
        for (StreetSection section : streetSections) {
            gc.setLineWidth(2);
            gc.setStroke(Color.GREY);
            gc.strokeLine(section.getStartIntersection().getX(), section.getStartIntersection().getY(),
                section.getEndIntersection().getX(), section.getEndIntersection().getY());
        }

        List<Intersection> intersections = cityMap.getIntersections();
        for (Intersection inter : intersections) {
            gc.fillOval(inter.getX() - INTERSECTION_SIZE / 2, inter.getY() - INTERSECTION_SIZE / 2,
                INTERSECTION_SIZE, INTERSECTION_SIZE);
        }
    }

    @Requires("deliveryRequest != null")
    public void drawDeliveryRequest(GraphicsContext gc, DeliveryRequest deliveryRequest) {
        for (DeliveryAddress deliveryAddress : deliveryRequest.getDeliveryAddresses()) {
            this.drawWaypoint(gc, deliveryAddress);
        }
        this.drawWaypoint(gc, deliveryRequest.getWarehouse());
    }

    @Requires("planning != null")
    public void drawPlanning(GraphicsContext gc, Planning planning) {
        Color colorStart = Color.BLUE;
        Color currentColor = colorStart;

        Iterable<Route> listRoutes = planning.getRoutes();

        // Draw routes
        for (Route route : listRoutes) {
            for (StreetSection section : route.getStreetSections()) {
                gc.setLineWidth(4);
                gc.setStroke(currentColor);
                gc.strokeLine(
                    section.getStartIntersection().getX(),
                    section.getStartIntersection().getY(),
                    section.getEndIntersection().getX(),
                    section.getEndIntersection().getY()
                );
                drawArrowBetweenStreetSection(gc, section, currentColor);
            }
        }

        // Draw the active waypoints (above the roads):
        for (AbstractWaypoint waypoint : planning.getWaypoints()) {
            this.drawWaypoint(gc, waypoint);
        }

        // Draw order of delivery
        int number = 1;
        for(Route route : listRoutes){
            gc.setLineWidth(3);
            gc.setStroke(Color.BLACK);
            gc.strokeText("" + number, route.getStartWaypoint().getIntersection().getX(), route.getStartWaypoint().getIntersection().getY());
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeText("" + number, route.getStartWaypoint().getIntersection().getX(), route.getStartWaypoint().getIntersection().getY());
            number++;
        }
    }

    public void drawArrowBetweenStreetSection(GraphicsContext gc, StreetSection street, Color color){
        double xStart = street.getStartIntersection().getX();
        double xEnd = street.getEndIntersection().getX();
        double yStart = street.getStartIntersection().getY();
        double yEnd = street.getEndIntersection().getY();

        double alpha = Math.atan2(yStart-yEnd,xStart-xEnd);
//        System.out.println("alpha = " +alpha);

        double xthird = (xStart + 2*xEnd)/3;
        double ythird = (yStart + 2*yEnd)/3;

        double lengthCross = 10 ;
        double xCross1 = xthird + lengthCross * Math.cos(alpha+Math.PI/6);
        double yCross1 = ythird + lengthCross * Math.sin(alpha+Math.PI/6);
        double xCross2 = xthird + lengthCross * Math.cos(alpha-Math.PI/6);
        double yCross2 = ythird + lengthCross * Math.sin(alpha-Math.PI/6);

        gc.setStroke(color);
        gc.setLineWidth(2);
        gc.strokeLine(xthird, ythird, xCross1, yCross1);
        gc.strokeLine(xthird, ythird, xCross2, yCross2);
    }

    private void drawWaypoint(GraphicsContext gc, DeliveryAddress deliveryAddress) {
        gc.setFill(Color.BLUE);
        gc.fillOval(
            deliveryAddress.getIntersection().getX() - WAYPOINT_SIZE / 2,
            deliveryAddress.getIntersection().getY() - WAYPOINT_SIZE / 2,
            WAYPOINT_SIZE,
            WAYPOINT_SIZE
        );
    }

    private void drawWaypoint(GraphicsContext gc, Warehouse warehouse) {
        gc.setFill(Color.RED);
        gc.fillOval(
            warehouse.getIntersection().getX() - WAYPOINT_SIZE / 2,
            warehouse.getIntersection().getY() - WAYPOINT_SIZE / 2,
            WAYPOINT_SIZE,
            WAYPOINT_SIZE
        );
    }

    private void drawWaypoint(GraphicsContext gc, AbstractWaypoint waypoint) {
        if (waypoint instanceof DeliveryAddress) {
            this.drawWaypoint(gc, (DeliveryAddress) waypoint);
        } else if (waypoint instanceof Warehouse) {
            this.drawWaypoint(gc, (Warehouse) waypoint);
        } else {
            System.err.println("Trying to draw unknown waypoint:");
            System.err.println(waypoint);
        }
    }
}
