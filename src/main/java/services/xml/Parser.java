package services.xml;

import models.*;
import services.xml.exception.ParserBoundedNodesNumberException;
import services.xml.exception.ParserDuplicateObjectException;
import services.xml.exception.ParserException;
import services.xml.exception.ParserIntegerValueException;
import services.xml.exception.ParserInvalidIdException;
import services.xml.exception.ParserLowerBoundedNodesNumberException;
import services.xml.exception.ParserMalformedXmlException;
import services.xml.exception.ParserShouldBeIntegerValueException;
import services.xml.exception.ParserTimeSyntaxException;
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
            throw new ParserMalformedXmlException(e);
        } catch (IOException e) {
            throw e;
        }

        NodeList intersectionList = cityMapDocument.getElementsByTagName(INTERSECTION_NAME);
        if (intersectionList.getLength() <= 1)
            throw new ParserLowerBoundedNodesNumberException("There must be at least 2 intersections in a cityMap", 2,
                    intersectionList.getLength(), INTERSECTION_NAME);

        for (int i = 0; i < intersectionList.getLength(); i++) {
            addIntersection((Element) intersectionList.item(i), intersections);
        }

        NodeList streetSectionList = cityMapDocument.getElementsByTagName(STREET_SECTION_NAME);
        if (streetSectionList.getLength() == 0)
            throw new ParserLowerBoundedNodesNumberException("There must be at least 1 street section in a cityMap", 1, 0,
                    STREET_SECTION_NAME);

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
            throw new ParserIntegerValueException("The x value of an intersection must be positive", x);
        if (y < 0)
            throw new ParserIntegerValueException("The y value of an intersection must be positive", y);
        if (id < 0)
            throw new ParserIntegerValueException("The id of an intersection must be positive", id);

        Intersection intersection = new Intersection(id, x, y);
        if (intersections.containsKey(id))
            throw new ParserDuplicateObjectException("Two intersections with the id " + id + " exist", intersection);

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
            throw new ParserInvalidIdException("The start of a street section must exist");
        if (!intersections.containsKey(idIntersectionEnd))
            throw new ParserInvalidIdException("The end of a street section must exist");
        if (idIntersectionStart == idIntersectionEnd)
            throw new ParserInvalidIdException("A street section can not begin and end at the same intersection");
        if (length < 0)
            throw new ParserIntegerValueException("The length of a street section must be positive", length);
        if (speed < 0)
            throw new ParserIntegerValueException("The speed of a street section must be positive", speed);
        // TODO test if time == 0?

        Intersection intersectionStart = intersections.get(idIntersectionStart);
        Intersection intersectionEnd = intersections.get(idIntersectionEnd);

        StreetSection streetSection = new StreetSection(length, speed, streetName, intersectionStart, intersectionEnd);
        if (streetSections.contains(streetSection))
            throw new ParserDuplicateObjectException("Two street sections begin at the intersection " + idIntersectionStart
                    + " and end at the intersection " + idIntersectionEnd, streetSection);

        return streetSection;
    }

    public DeliveryRequest getDeliveryRequest(File xmlFile, CityMap cityMap) throws IOException, ParserException {
        Warehouse warehouse = null;
        Collection<DeliveryAddress> deliveryAddresses = new ArrayList<DeliveryAddress>();
        int startPlanningTimestamp;

        Document deliveryRequestDocument = null;
        try {
            deliveryRequestDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        } catch (SAXException | ParserConfigurationException e) {
            throw new ParserMalformedXmlException(e);
        } catch (IOException e) {
            throw e;
        }

        NodeList warehouseNode = deliveryRequestDocument.getElementsByTagName(WAREHOUSE_NAME);
        if (warehouseNode.getLength() != 1)
            throw new ParserBoundedNodesNumberException("There must be exactly 1 warehouse in a delivery request", 1, 1,
                    warehouseNode.getLength(), WAREHOUSE_NAME);

        Element warehouseElement = (Element) (warehouseNode.item(0));
        warehouse = getWarehouse(warehouseElement, cityMap);
        startPlanningTimestamp = getStartPlanningTimestamp(warehouseElement);

        NodeList deliveryAddressesNodes = deliveryRequestDocument.getElementsByTagName(DELIVERY_ADDRESS_NAME);
        if (deliveryAddressesNodes.getLength() < 1)
            throw new ParserLowerBoundedNodesNumberException("There must be at least 1 delivery address in a delivery request", 1, 0,
                    DELIVERY_ADDRESS_NAME);

        for (int i = 0; i < deliveryAddressesNodes.getLength(); i++) {
            deliveryAddresses.add(getDeliveryAddress((Element) deliveryAddressesNodes.item(i), cityMap, deliveryAddresses));
        }

        return new DeliveryRequest(warehouse, deliveryAddresses, startPlanningTimestamp);
    }

    private Warehouse getWarehouse(Element warehouseElement, CityMap cityMap) throws ParserException {
        int idIntersection;
        try {
            idIntersection = Integer.parseInt(warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_ID));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }

        if (!cityMap.isIntersectionInCityMap(idIntersection))
            throw new ParserInvalidIdException("The address of a warehouse must exist in the city map");

        return new Warehouse(cityMap.getIntersection(idIntersection));
    }

    private int getStartPlanningTimestamp(Element warehouseElement) throws ParserException {
        String stringStartTimestamp = warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_START_TIME);
        String[] stringHoursMinutesSeconds = stringStartTimestamp.split(":");

        if (stringHoursMinutesSeconds.length != 3)
            throw new ParserTimeSyntaxException("The start delivery time must be on the format hh:mm:ss");

        int hours;
        int minutes;
        int seconds;
        try {
            hours = Integer.parseInt(stringHoursMinutesSeconds[0]);
            minutes = Integer.parseInt(stringHoursMinutesSeconds[1]);
            seconds = Integer.parseInt(stringHoursMinutesSeconds[2]);
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }

        if (hours < 0 || hours >= 24 || minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60)
            throw new ParserTimeSyntaxException("The start delivery time must be on the format hh:mm:ss");

        return hours * 3600 + minutes * 60 + seconds;
    }

    private DeliveryAddress getDeliveryAddress(Element deliveryAddressElement, CityMap cityMap,
            Collection<DeliveryAddress> deliveryAddresses) throws ParserException {
        int idIntersection;
        int deliveryDuration;
        try {
            idIntersection = Integer.parseInt(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_ID));
            deliveryDuration = Integer.parseInt(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_DURATION));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }

        if (!cityMap.isIntersectionInCityMap(idIntersection))
            throw new ParserInvalidIdException("The address of a delivery request must exist in the city map");

        // TODO construct with time constraints
        DeliveryAddress deliveryAddress = new DeliveryAddress(cityMap.getIntersection(idIntersection), deliveryDuration);
        if (deliveryAddresses.contains(deliveryAddress))
            throw new ParserDuplicateObjectException("There are two delivery addresses with the address " + idIntersection,
                    deliveryAddress);

        return deliveryAddress;
    }
}
