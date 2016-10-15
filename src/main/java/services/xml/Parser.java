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
import org.w3c.dom.Node;
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
				if (intersectionList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					addIntersection((Element) intersectionList.item(i), intersections);
				}
			}

			NodeList streetSectionList = cityMapDocument.getElementsByTagName(STREET_SECTION_NAME);
			for (int i = 0; i < streetSectionList.getLength(); i++) {
				if (streetSectionList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					streetSections.add(getStreetSection((Element) streetSectionList.item(i), intersections));
				}
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

	public DeliveryRequest getDeliveryRequest(File xmlFile) {
		Warehouse warehouse = null;
		Collection<DeliveryAddress> deliveryAddresses = new ArrayList<DeliveryAddress>();
		
		try {
			Document deliveryRequestDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
			NodeList warehouseNode = deliveryRequestDocument.getElementsByTagName(WAREHOUSE_NAME);
			if (warehouseNode.getLength() == 1) {
				warehouse = getWarehouse((Element) (warehouseNode.item(0)));
			} else {
				// TODO throw exception
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return new DeliveryRequest(warehouse, deliveryAddresses);
	}
	
	private Warehouse getWarehouse(Element WarehouseElement) {
		return null;
	}
}
