package services.map;

import com.google.java.contract.Requires;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.*;

import java.util.HashMap;
import java.util.List;

public class MapRenderer {
    public static final int MARGIN_ERROR = 0;
    public static final double INTERSECTION_SIZE = 10;
    public static final double WAYPOINT_SIZE = 18;

    private CityMap cityMap;
    private DeliveryRequest deliveryRequest;
    private Planning planning;

    public static HashMap<StreetSection, Integer> getSectionMap(Iterable<Route> listRoutes) {
        HashMap<StreetSection, Integer> sectionMap = new HashMap<StreetSection, Integer>();

        for (Route route : listRoutes) {
            List<StreetSection> streetSections = route.getStreetSections();
            for (StreetSection section : streetSections) {
                if (!sectionMap.containsKey(section)) {
                    sectionMap.put(section, 1);
                } else {
                    sectionMap.put(section, sectionMap.get(section) + 1);
                }
            }
        }
        return sectionMap;
    }

    public static Color getColor(double step, double nbStep) {
        double red = 0;
        double green = 0;
        double blue = 0;

        if (3 * step < nbStep) {
            red = 1 - (3 * step / nbStep);
            green = 3 * step / nbStep;
        } else if (3 * step < 2 * nbStep) {
            green = 1 - (step / (2 * nbStep / 3));
            blue = step / (2 * nbStep / 3);
        } else {
            red = step / (nbStep);
            blue = 1 - (step / (nbStep));

        }
        return new Color(red, green, blue, 1);
    }

    public MapRenderer() {
    }

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
            gc.setFill(Color.GREEN);
            gc.fillOval(
                deliveryAddress.getIntersection().getX() - WAYPOINT_SIZE / 2,
                deliveryAddress.getIntersection().getY() - WAYPOINT_SIZE / 2,
                WAYPOINT_SIZE,
                WAYPOINT_SIZE
            );
        }
        gc.setFill(Color.BLUE);
        gc.fillOval(
            deliveryRequest.getWarehouse().getIntersection().getX() - WAYPOINT_SIZE / 2,
            deliveryRequest.getWarehouse().getIntersection().getY() - WAYPOINT_SIZE / 2,
            WAYPOINT_SIZE,
            WAYPOINT_SIZE
        );
    }

    @Requires("planning != null")
    public void drawPlanning(GraphicsContext gc, Planning planning) {
        Color colorStart = Color.BLUE;
        Color currentColor = colorStart;

        Iterable<Route> listRoutes = planning.getRoutes();

        //Map containing the number of each streetSection crossed
        HashMap<StreetSection, Integer> sectionMap = new HashMap<StreetSection, Integer>();

        //actual section
        int countSections = 1;
        //total number of sections
        int totalSections = 0;
        //Count the total numbers of section
        for (Route route : listRoutes) {
            totalSections += route.getStreetSections().size();
        }

        //Stroke the line and the arrow
        for (Route route : listRoutes) {
            gc.setStroke(currentColor);
            List<StreetSection> streetSections = route.getStreetSections();
            for (StreetSection section : streetSections) {
                gc.setLineWidth(4);
                currentColor = getColor(countSections++, totalSections);
                gc.setStroke(currentColor);
                if (!sectionMap.containsKey(section)) {
                    sectionMap.put(section, 1);
                    gc.strokeLine(section.getStartIntersection().getX(), section.getStartIntersection().getY(),
                        section.getEndIntersection().getX(), section.getEndIntersection().getY());
                } else {
                    int passings = sectionMap.get(section);
                    sectionMap.put(section, passings + 1);
                    System.out.println("passing" + passings);
                    if (Math.abs(section.getStartIntersection().getX() - section.getEndIntersection().getX()) > Math.abs(section.getStartIntersection().getY() - section.getEndIntersection().getY())) {
                        gc.strokeLine(section.getStartIntersection().getX() - 4 * passings, section.getStartIntersection().getY(),
                            section.getEndIntersection().getX() - 4 * passings, section.getEndIntersection().getY());
                    } else {
                        gc.strokeLine(section.getStartIntersection().getX(), section.getStartIntersection().getY() - 4 * passings,
                            section.getEndIntersection().getX(), section.getEndIntersection().getY() - 4 * passings);
                    }
                }
                drawArrowBetweenStreetSection(gc, section, currentColor);
            }
        }

        // Draw the active waypoints (above the roads):
        for (PlanningWaypoint planningWaypoint : planning.getPlanningWaypoints()) {
            this.drawPlanningWaypoint(gc, planningWaypoint);
        }

        // Draw order of delivery
        int number = 1;
        for (Route route : listRoutes) {
            gc.setLineWidth(3);
            gc.setStroke(Color.BLACK);
            gc.strokeText("" + number, route.getStartWaypoint().getIntersection().getX(), route.getStartWaypoint().getIntersection().getY());
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeText("" + number, route.getStartWaypoint().getIntersection().getX(), route.getStartWaypoint().getIntersection().getY());
            number++;
        }
    }

    public void drawArrowBetweenStreetSection(GraphicsContext gc, StreetSection street, Color color) {
        double xStart = street.getStartIntersection().getX();
        double xEnd = street.getEndIntersection().getX();
        double yStart = street.getStartIntersection().getY();
        double yEnd = street.getEndIntersection().getY();

        double alpha = Math.atan2(yStart - yEnd, xStart - xEnd);
//        System.out.println("alpha = " +alpha);

        double xthird = (xStart + 2 * xEnd) / 3;
        double ythird = (yStart + 2 * yEnd) / 3;

        double lengthCross = 10;
        double xCross1 = xthird + lengthCross * Math.cos(alpha + Math.PI / 6);
        double yCross1 = ythird + lengthCross * Math.sin(alpha + Math.PI / 6);
        double xCross2 = xthird + lengthCross * Math.cos(alpha - Math.PI / 6);
        double yCross2 = ythird + lengthCross * Math.sin(alpha - Math.PI / 6);

        gc.setStroke(color);
        gc.setLineWidth(2);
        gc.strokeLine(xthird, ythird, xCross1, yCross1);
        gc.strokeLine(xthird, ythird, xCross2, yCross2);
    }

    private void drawPlanningWaypoint(GraphicsContext gc, PlanningWaypoint planningWaypoint) {
        gc.setFill(Color.RED);
        if (planningWaypoint.getIsPossible()) {
            if (planningWaypoint.getTargetWaypoint() instanceof DeliveryAddress) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.BLUE);
            }
        }

        gc.fillOval(
            planningWaypoint.getTargetWaypoint().getIntersection().getX() - WAYPOINT_SIZE / 2,
            planningWaypoint.getTargetWaypoint().getIntersection().getY() - WAYPOINT_SIZE / 2,
            WAYPOINT_SIZE,
            WAYPOINT_SIZE
        );
    }
}
