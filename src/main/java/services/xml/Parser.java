package services.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import models.Intersection;
import models.StreetSection;
import models.Warehouse;
import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;

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

	public CityMap getCityMap(File xmlFile) {
		TreeMap<Integer, Intersection> intersections = new TreeMap<Integer, Intersection>();
		ArrayList<StreetSection> streetSections = new ArrayList<StreetSection>();

		try {
			Document cityMapDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

			NodeList intersectionList = cityMapDocument.getElementsByTagName(INTERSECTION_NAME);
			for (int i = 0; i < intersectionList.getLength(); i++) {
				addIntersection((Element) intersectionList.item(i), intersections);
			}

			NodeList streetSectionList = cityMapDocument.getElementsByTagName(STREET_SECTION_NAME);
			for (int i = 0; i < streetSectionList.getLength(); i++) {
				streetSections.add(getStreetSection((Element) streetSectionList.item(i), intersections));
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		return new CityMap(intersections.values(), streetSections);
	}

	private void addIntersection(Element intersectionNode, Map<Integer, Intersection> intersections) {
		int id = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_ID));
		int x = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_X));
		int y = Integer.parseInt(intersectionNode.getAttribute(NAME_ATTRIBUTE_INTERSECTION_Y));
		Intersection intersection = new Intersection(id, x, y);
		intersections.put(id, intersection);
	}

	private StreetSection getStreetSection(Element streetSectionNode, Map<Integer, Intersection> intersections) {
		int idIntersectionStart = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_START));
		int idIntersectionEnd = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_END));
		int length = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_LENGTH));
		String streetName = streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_STREET_NAME);
		int speed = Integer.parseInt(streetSectionNode.getAttribute(NAME_ATTRIBUTE_STREET_SECTION_VELOCITY));

		Intersection intersectionStart = intersections.get(idIntersectionStart);
		Intersection intersectionEnd = intersections.get(idIntersectionEnd);

		return new StreetSection(length, speed, streetName, intersectionStart, intersectionEnd);
	}

	public DeliveryRequest getDeliveryRequest(File xmlFile, CityMap cityMap) {
		Warehouse warehouse = null;
		Collection<DeliveryAddress> deliveryAddresses = new ArrayList<DeliveryAddress>();
		int startPlanningTimestamp = -1;

		try {
			Document deliveryRequestDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

			NodeList warehouseNode = deliveryRequestDocument.getElementsByTagName(WAREHOUSE_NAME);
			if (warehouseNode.getLength() == 1) {
				Element warehouseElement = (Element) (warehouseNode.item(0));
				warehouse = getWarehouse(warehouseElement, cityMap);
				startPlanningTimestamp = getStartPlanningTimestamp(warehouseElement);
			} else {
				// TODO throw exception
			}

			NodeList deliveryAddressesNode = deliveryRequestDocument.getElementsByTagName(DELIVERY_ADDRESS_NAME);
			for (int i = 0; i < deliveryAddressesNode.getLength(); i++){
				deliveryAddresses.add(getDeliveryAddress((Element) deliveryAddressesNode.item(i), cityMap));	
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
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

		return new DeliveryAddress(cityMap.getIntersection(idIntersection), deliveryDuration);
	}
}
