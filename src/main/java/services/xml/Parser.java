package services.xml;

import models.*;
import services.xml.exception.ParserDuplicateObjectException;
import services.xml.exception.ParserException;
import services.xml.exception.ParserIntegerValueException;
import services.xml.exception.ParserNodesNumberException;
import services.xml.exception.ParserShouldBeIntegerValueException;
import services.xml.exception.ParserSyntaxException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Parser {

    private static final String INTERSECTION_NAME = "noeud";
    private static final String STREET_SECTION_NAME = "troncon";

    private static final String NAME_ATTRIBUTE_INTERSECTION_ID = "id";
    private static final String NAME_ATTRIBUTE_INTERSECTION_X = "x";
    private static final String NAME_ATTRIBUTE_INTERSECTION_Y = "y";

    private static final String NAME_ATTRIBUTE_STREET_SECTION_START = "origine";
    private static final String NAME_ATTRIBUTE_STREET_SECTION_END = "destination";
    private static final String NAME_ATTRIBUTE_STREET_SECTION_LENGTH = "longueur";
    private static final String NAME_ATTRIBUTE_STREET_SECTION_STREET_NAME = "nomRue";
    private static final String NAME_ATTRIBUTE_STREET_SECTION_VELOCITY = "vitesse";

    private static final String WAREHOUSE_NAME = "entrepot";
    private static final String DELIVERY_ADDRESS_NAME = "livraison";

    private static final String NAME_ATTRIBUTE_WAREHOUSE_ID = "adresse";
    private static final String NAME_ATTRIBUTE_WAREHOUSE_START_TIME = "heureDepart";

    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_ID = "adresse";
    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_DURATION = "duree";

    public CityMap getCityMap(File xmlFile) throws IOException, ParserException {
        Map<Integer, Intersection> intersections = new TreeMap<Integer, Intersection>();
        ArrayList<StreetSection> streetSections = new ArrayList<StreetSection>();

        Document cityMapDocument = null;
        try {
            cityMapDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        } catch (SAXException | ParserConfigurationException e) {
            throw new ParserSyntaxException();
        } catch (IOException e) {
            throw e;
        }

        NodeList intersectionList = cityMapDocument.getElementsByTagName(INTERSECTION_NAME);
        if (intersectionList.getLength() == 0)
            throw new ParserNodesNumberException(1, -1, 0, INTERSECTION_NAME);

        for (int i = 0; i < intersectionList.getLength(); i++) {
            addIntersection((Element) intersectionList.item(i), intersections);
        }

        NodeList streetSectionList = cityMapDocument.getElementsByTagName(STREET_SECTION_NAME);
        if (streetSectionList.getLength() == 0)
            throw new ParserNodesNumberException(1, -1, 0, STREET_SECTION_NAME);

        for (int i = 0; i < streetSectionList.getLength(); i++) {
            streetSections.add(getStreetSection((Element) streetSectionList.item(i), intersections, streetSections));
        }

        return new CityMap(intersections.values(), streetSections);
    }

    private void addIntersection(Element intersectionNode, Map<Integer, Intersection> intersections) throws ParserException {
        int id;
        int x;
        int y;
        try {
            id = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_ID));
            x = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_X));
            y = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_Y));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }

        if (x < 0)
            throw new ParserIntegerValueException(x);
        if (y < 0)
            throw new ParserIntegerValueException(y);
        if (id < 0)
            throw new ParserIntegerValueException(id);

        Intersection intersection = new Intersection(id, x, y);
        if (intersections.containsKey(id))
            throw new ParserDuplicateObjectException(intersection);

        intersections.put(id, intersection);
    }

    private StreetSection getStreetSection(Element streetSectionNode, Map<Integer, Intersection> intersections,
            Collection<StreetSection> streetSections) throws ParserException {
        int idIntersectionStart;
        int idIntersectionEnd;
        int length;
        int speed;
        try {
            idIntersectionStart = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_START));
            idIntersectionEnd = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_END));
            length = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_LENGTH));
            speed = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_VELOCITY));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }
        String streetName = streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_STREET_NAME);

        if (!intersections.containsKey(idIntersectionStart))
            throw new ParserIntegerValueException(idIntersectionStart);
        if (!intersections.containsKey(idIntersectionEnd))
            throw new ParserIntegerValueException(idIntersectionEnd);
        if (length < 0)
            throw new ParserIntegerValueException(length);
        if (speed < 0)
            throw new ParserIntegerValueException(speed);
        // TODO test if time == 0?

        Intersection intersectionStart = intersections.get(idIntersectionStart);
        Intersection intersectionEnd = intersections.get(idIntersectionEnd);

        StreetSection streetSection =  new StreetSection(length, speed, streetName, intersectionStart, intersectionEnd);
        if (streetSections.contains(streetSection))
            throw new ParserDuplicateObjectException(streetSection);
        
        return streetSection;
    }

    public DeliveryRequest getDeliveryRequest(File xmlFile, CityMap cityMap) throws IOException, ParserException {
        Warehouse warehouse = null;
        Collection<DeliveryAddress> deliveryAddresses = new ArrayList<DeliveryAddress>();
        int startPlanningTimestamp = -1;

        Document deliveryRequestDocument = null;
        try {
            deliveryRequestDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        } catch (SAXException | ParserConfigurationException e) {
            throw new ParserSyntaxException(e);
        } catch (IOException e) {
            throw e;
        }

        NodeList warehouseNode = deliveryRequestDocument.getElementsByTagName(WAREHOUSE_NAME);
        if (warehouseNode.getLength() == 1) {
            Element warehouseElement = (Element) (warehouseNode.item(0));
            warehouse = getWarehouse(warehouseElement, cityMap);
            startPlanningTimestamp = getStartPlanningTimestamp(warehouseElement);
        } else {
            // TODO throw exception
        }

        NodeList deliveryAddressesNode = deliveryRequestDocument.getElementsByTagName(DELIVERY_ADDRESS_NAME);
        for (int i = 0; i < deliveryAddressesNode.getLength(); i++) {
            deliveryAddresses.add(getDeliveryAddress((Element) deliveryAddressesNode.item(i), cityMap));
        }

        return new DeliveryRequest(warehouse, deliveryAddresses, startPlanningTimestamp);
    }

    private Warehouse getWarehouse(Element warehouseElement, CityMap cityMap) {
        int idIntersection = Integer.parseInt(warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_ID));
        return new Warehouse(cityMap.getIntersection(idIntersection));
    }

    private int getStartPlanningTimestamp(Element warehouseElement) {
        String stringStartTimestamp = warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_START_TIME);
        String[] stringHoursMinutesSeconds = stringStartTimestamp.split(":");

        if (stringHoursMinutesSeconds.length == 3) {
            int[] hoursMinuesSeconds = new int[3];
            hoursMinuesSeconds[0] = Integer.parseInt(stringHoursMinutesSeconds[0]);
            hoursMinuesSeconds[1] = Integer.parseInt(stringHoursMinutesSeconds[1]);
            hoursMinuesSeconds[2] = Integer.parseInt(stringHoursMinutesSeconds[2]);

            return hoursMinuesSeconds[0] * 3600 + hoursMinuesSeconds[1] * 60 + hoursMinuesSeconds[2];
        } // else TODO throw exception
        return -1;
    }

    private DeliveryAddress getDeliveryAddress(Element deliveryAddressElement, CityMap cityMap) {
        int idIntersection = Integer.parseInt(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_ID));
        int deliveryDuration = Integer.parseInt(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_DURATION));
        // TODO construct with time constraints
        return new DeliveryAddress(cityMap.getIntersection(idIntersection), deliveryDuration);
    }
}
