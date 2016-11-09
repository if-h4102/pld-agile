package services.xml;

import models.*;
import services.xml.exception.ParserBoundedNodesNumberException;
import services.xml.exception.ParserDuplicateObjectException;
import services.xml.exception.ParserException;
import services.xml.exception.ParserIntegerValueException;
import services.xml.exception.ParserInvalidIdException;
import services.xml.exception.ParserLowerBoundedNodesNumberException;
import services.xml.exception.ParserMalformedXmlException;
import services.xml.exception.ParserMissingAttributeException;
import services.xml.exception.ParserShouldBeIntegerValueException;
import services.xml.exception.ParserTimeConstraintsException;
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
    private static final String NAME_ATTRIBUTE_WAREHOUSE_START_PLANNING_TIME = "heureDepart";

    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_ID = "adresse";
    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_DURATION = "duree";
    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_START_DELIVERY_TIME = "debutPlage";
    private static final String NAME_ATTRIBUTE_DELIVERY_REQUEST_END_DELIVERY_TIME = "finPlage";

    /**
     * Returns the cityMap described by the file given in parameter. This file must be at the xml format, and follow the correct syntax. If
     * not, an exception will be thrown.
     * 
     * @param xmlFile
     *            The file containing the xml description of the city map.
     * @return The city map described by the xml file.
     * @throws IOException
     *             If the file is not reachable by the application.
     * @throws ParserException
     *             If the file is not syntactically of meaningfully correct
     */
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

    /**
     * Add an intersection in the parameter intersections. The intersection added is the intersection described by the intersectionElement
     * 
     * @param intersectionElement
     *            The xml element describing the intersection to add.
     * @param intersections
     *            The Collection of intersections where the new intersection will be added
     * @throws ParserException
     *             If the element describing the intersection is not correct.
     */
    private void addIntersection(Element intersectionElement, Map<Integer, Intersection> intersections) throws ParserException {
        if (!attributeExist(intersectionElement, NAME_ATTRIBUTE_INTERSECTION_ID, NAME_ATTRIBUTE_INTERSECTION_X,
                NAME_ATTRIBUTE_INTERSECTION_Y))
            throw new ParserMissingAttributeException("An attribute is missing to construct the intersection");

        int id;
        int x;
        int y;
        try {
            id = Integer.parseInt(intersectionElement.getAttribute(NAME_ATTRIBUTE_INTERSECTION_ID));
            x = Integer.parseInt(intersectionElement.getAttribute(NAME_ATTRIBUTE_INTERSECTION_X));
            y = Integer.parseInt(intersectionElement.getAttribute(NAME_ATTRIBUTE_INTERSECTION_Y));
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

    /**
     * Returns the street section described by the streetSectionElement.
     * 
     * @param streetSectionElement
     *            The xml element describing the street section.
     * @param intersections
     *            The map of intersections of the city map where the street section is. This map must contains all the intersection of the
     *            city map.
     * @param streetSections
     *            The street sections already parsed. An error will be thrown if the current street section is already in this collection.
     * @return The street section described by the streetSectionElement.
     * @throws ParserException
     *             If the element describing the street section is not correct, or the street section has already been added.
     */
    private StreetSection getStreetSection(Element streetSectionElement, Map<Integer, Intersection> intersections,
            Collection<StreetSection> streetSections) throws ParserException {
        if (!attributeExist(streetSectionElement, NAME_ATTRIBUTE_STREET_SECTION_START, NAME_ATTRIBUTE_STREET_SECTION_END,
                NAME_ATTRIBUTE_STREET_SECTION_LENGTH, NAME_ATTRIBUTE_STREET_SECTION_VELOCITY, NAME_ATTRIBUTE_STREET_SECTION_STREET_NAME))
            throw new ParserMissingAttributeException("An attribute is missing to construct the street section");

        int idIntersectionStart;
        int idIntersectionEnd;
        int length;
        int speed;
        try {
            idIntersectionStart = Integer.parseInt(streetSectionElement.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_START));
            idIntersectionEnd = Integer.parseInt(streetSectionElement.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_END));
            length = Integer.parseInt(streetSectionElement.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_LENGTH));
            speed = Integer.parseInt(streetSectionElement.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_VELOCITY));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        }
        String streetName = streetSectionElement.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_STREET_NAME);

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

        Intersection intersectionStart = intersections.get(idIntersectionStart);
        Intersection intersectionEnd = intersections.get(idIntersectionEnd);

        StreetSection streetSection = new StreetSection(length, speed, streetName, intersectionStart, intersectionEnd);
        if (streetSections.contains(streetSection))
            throw new ParserDuplicateObjectException("Two street sections begin at the intersection " + idIntersectionStart
                    + " and end at the intersection " + idIntersectionEnd, streetSection);

        return streetSection;
    }

    /**
     * Returns the delivery request described by the xml file provided in parameter. This file must be at the format xml and respect the
     * right syntax.
     * 
     * @param xmlFile
     *            The file describing the returned delivery request.
     * @param cityMap
     *            The city map on which the delivery request will be applied.
     * @return The delivery request described by the xmlFile.
     * @throws IOException
     *             If the file is not reachable by the application.
     * @throws ParserException
     *             If the xmlFile does not respect the right syntax.
     */
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
        startPlanningTimestamp = warehouse.getTimeStart();

        NodeList deliveryAddressesNodes = deliveryRequestDocument.getElementsByTagName(DELIVERY_ADDRESS_NAME);
        if (deliveryAddressesNodes.getLength() < 1)
            throw new ParserLowerBoundedNodesNumberException("There must be at least 1 delivery address in a delivery request", 1, 0,
                    DELIVERY_ADDRESS_NAME);

        for (int i = 0; i < deliveryAddressesNodes.getLength(); i++) {
            deliveryAddresses.add(getDeliveryAddress((Element) deliveryAddressesNodes.item(i), cityMap, deliveryAddresses));
        }

        return new DeliveryRequest(cityMap, warehouse, deliveryAddresses, startPlanningTimestamp);
    }

    /**
     * Returns the warehouse described by the warehouseElement.
     * 
     * @param warehouseElement
     *            The xml element describing the warehouse.
     * @param cityMap
     *            The city map where the warehouse is.
     * @return The warehouse described by the warehouseElement.
     * @throws ParserException
     *             If the warehouse element is not correct.
     */
    private Warehouse getWarehouse(Element warehouseElement, CityMap cityMap) throws ParserException {
        if (!attributeExist(warehouseElement, NAME_ATTRIBUTE_WAREHOUSE_ID, NAME_ATTRIBUTE_WAREHOUSE_START_PLANNING_TIME))
            throw new ParserMissingAttributeException("An attribute is missing to construct the warehouse");

        int startPlanningTime;
        int idIntersection;
        try {
            idIntersection = Integer.parseInt(warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_ID));
            startPlanningTime = getTime(warehouseElement.getAttribute(NAME_ATTRIBUTE_WAREHOUSE_START_PLANNING_TIME));
        } catch (NumberFormatException e) {
            throw new ParserShouldBeIntegerValueException(e);
        } catch (ParserTimeSyntaxException e) {
            throw new ParserTimeSyntaxException("The start planning time must be on the format hh:mm:ss", e);
        }

        if (!cityMap.isIntersectionInCityMap(idIntersection))
            throw new ParserInvalidIdException("The address of a warehouse must exist in the city map");

        return new Warehouse(cityMap.getIntersection(idIntersection), startPlanningTime);
    }

    /**
     * Returns the deliveryAddress described by the deliveryAddressElement.
     * 
     * @param deliveryAddressElement
     *            The element describing the delivery address.
     * @param cityMap
     *            The city map where the delivery address is.
     * @param deliveryAddresses
     *            The collection of already parsed delivery address. An error is thrown if the current delivery address is already in this
     *            collection.
     * @return The delivery address described by the deliveryAddressElement.
     * @throws ParserException
     *             If the element described the delivery address is not correct, or the current delivery address is already in the
     *             collection of delivery addresses.
     */
    private DeliveryAddress getDeliveryAddress(Element deliveryAddressElement, CityMap cityMap,
            Collection<DeliveryAddress> deliveryAddresses) throws ParserException {
        if (!attributeExist(deliveryAddressElement, NAME_ATTRIBUTE_DELIVERY_REQUEST_DURATION, NAME_ATTRIBUTE_DELIVERY_REQUEST_ID))
            throw new ParserMissingAttributeException("An attribute is missing to construct the delivery addresss");

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

        int[] timeConstraints;
        try {
            timeConstraints = getTimeConstraints(deliveryAddressElement, deliveryDuration);
        } catch (ParserException e) {
            throw e;
        }

        DeliveryAddress deliveryAddress = new DeliveryAddress(cityMap.getIntersection(idIntersection), deliveryDuration, timeConstraints[0],
                timeConstraints[1]);
        if (deliveryAddresses.contains(deliveryAddress))
            throw new ParserDuplicateObjectException("There are two delivery addresses with the address " + idIntersection,
                    deliveryAddress);

        return deliveryAddress;
    }

    /**
     * Returns the time constraints of the delivery address described by the delivery address element. If no time constraints are available,
     * standard time constraints are returned.
     * 
     * @param deliveryAddressElement
     *            The xml element containing the time constraints.
     * @param deliveryDuration
     *            The delivery duration associated to the delivery address.
     * @return The time constraints of the delivery address. This is formatted as an array: the first cell is the start delivery time and
     *         the second cell is the end delivery time.
     * @throws ParserException
     *             If the time constraints are not correct.
     */
    private int[] getTimeConstraints(Element deliveryAddressElement, int deliveryDuration) throws ParserException {
        boolean isStartDeliveryTime = deliveryAddressElement.hasAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_START_DELIVERY_TIME);
        boolean isEndDeliveryTime = deliveryAddressElement.hasAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_END_DELIVERY_TIME);
        int[] result = { 0, 86400 };

        if (!isStartDeliveryTime && !isEndDeliveryTime)
            return result;
        if (!isStartDeliveryTime)
            throw new ParserMissingAttributeException("The delivery time start must be present if the delivery time end is present");
        if (!isEndDeliveryTime)
            throw new ParserMissingAttributeException("The delivery time end must be present if the delivery time start is present");

        try {
            result[0] = getTime(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_START_DELIVERY_TIME));
            result[1] = getTime(deliveryAddressElement.getAttribute(NAME_ATTRIBUTE_DELIVERY_REQUEST_END_DELIVERY_TIME));
        } catch (ParserTimeSyntaxException e) {
            throw new ParserTimeSyntaxException("The delivery time must be at the format hh:mm:ss", e);
        }

        if (result[0] > result[1])
            throw new ParserTimeConstraintsException("The delivery time start must be before the delivery time end");
        if (result[0] + deliveryDuration > result[1])
            throw new ParserTimeConstraintsException(
                    "The difference between delivery time start and delivery time end must be greater than the delivery duration");

        return result;
    }

    // String... is as a String[] in this method, but is called with syntax :
    // (string1, string2, string3)
    /**
     * Returns true or false whereas the provided string in parameters are attributes of the provided element.
     * 
     * @param element
     *            The element which should have the string provided as attributes.
     * @param attributes
     *            The list of attributes that the element should have.
     * @return true if the element have all the attributes provided, false if at least one attribute is missing. The method could return
     *         true if the element has more attributes than the list, as long as all the attributes in the list are in the element.
     */
    private boolean attributeExist(Element element, String... attributes) {
        for (String attribute : attributes) {
            if (!element.hasAttribute(attribute))
                return false;
        }
        return true;
    }

    /**
     * Returns the timestamp representing by the provided stringTime.
     * 
     * @param stringTime
     *            The string that will be convert to timestamp. This string must be at the format hh:mm:ss
     * @return The timestamp representing by the provided stringTime.
     * @throws ParserTimeSyntaxException
     *             If the stringTime is not at the right format.
     */
    private int getTime(String stringTime) throws ParserTimeSyntaxException {
        String errorMessage = "The format of a time must be hh:mm:ss";

        String[] stringHoursMinutesSeconds = stringTime.split(":");
        if (stringHoursMinutesSeconds.length != 3)
            throw new ParserTimeSyntaxException(errorMessage);

        int hours;
        int minutes;
        int seconds;
        try {
            hours = Integer.parseInt(stringHoursMinutesSeconds[0]);
            minutes = Integer.parseInt(stringHoursMinutesSeconds[1]);
            seconds = Integer.parseInt(stringHoursMinutesSeconds[2]);
        } catch (NumberFormatException e) {
            throw new ParserTimeSyntaxException(errorMessage, e);
        }

        if (hours < 0 || hours >= 24 || minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60)
            throw new ParserTimeSyntaxException(errorMessage);

        return hours * 3600 + minutes * 60 + seconds;
    }
}
