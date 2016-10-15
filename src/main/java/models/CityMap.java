package models;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CityMap {

	private Map<Integer, Intersection> intersections; // Map<idIntersection, intersection>
	private Map<Integer, Map<Integer, StreetSection>> streetSections; // Map<idIntersectionStart, Map<idIntersectionEnd, streetSection>>

	public CityMap(Collection<Intersection> intersectionsList, Collection<StreetSection> streetSectionsList) {
		intersections = new TreeMap<Integer, Intersection>();
		streetSections = new TreeMap<Integer, Map<Integer, StreetSection>>();
		
		for (Intersection intersection : intersectionsList) {
			addIntersections(intersection);
		}
		for (StreetSection streetSection : streetSectionsList) {
			addStreetSections(streetSection);
		}
	}

	private void addIntersections(Intersection intersection) {
		intersections.put(intersection.getId(), intersection);
	}

	private void addStreetSections(StreetSection streetSection) {
		Map<Integer, StreetSection> streetSectionsFromIntersectionStart = streetSections.get(streetSection.getStartIntersection().getId());
		if (streetSectionsFromIntersectionStart == null)
			streetSectionsFromIntersectionStart = new TreeMap<Integer, StreetSection>();

		streetSectionsFromIntersectionStart.put(streetSection.getEndIntersection().getId(), streetSection);
		streetSections.put(streetSection.getStartIntersection().getId(), streetSectionsFromIntersectionStart);
	}

	// TODO
	private Route shortestPath(Intersection startIntersection, Intersection endIntersection) {
		return null;
	}

	// TODO
	public DeliveryGraph computeDeliveryGraph(DeliveryRequest request) {
		return null;
	}

	// TODO
	public List<Intersection> getIntersections() {
		return null;
	}

	// TODO
	public List<StreetSection> getStreetSections() {
		return null;
	}
	
	public Intersection getIntersection(int idIntersection) {
		return intersections.get(idIntersection);
	}
}
