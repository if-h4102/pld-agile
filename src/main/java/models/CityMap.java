package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CityMap {
    /**
     * Key: `intersectionId`
     * Value: The associated Intersection
     */
    private Map<Integer, Intersection> intersections;

    /**
     * Key: `startIntersectionId`
     * Value: Map:
     * -  Key: `endIntersectionId`
     * -  Value: `StreetSection`, the StreetSection from the intersection with
     * the id `startIntersectionId` to the intersection with the id
     * `endIntersectionId`
     */
    private Map<Integer, Map<Integer, StreetSection>> streetSections;

    public CityMap(Iterable<Intersection> intersections, Iterable<StreetSection> streetSections) {
        this.intersections = new TreeMap<Integer, Intersection>();
        this.streetSections = new TreeMap<Integer, Map<Integer, StreetSection>>();

        for (Intersection intersection : intersections) {
            addIntersection(intersection);
        }
        for (StreetSection streetSection : streetSections) {
            addStreetSection(streetSection);
        }
    }

    @Requires({
        "!intersections.containsKey(intersection.getId())"
    })
    @Ensures({
        "intersections.containsKey(intersection.getId())",
        "intersections.containsValue(intersection)",
        "intersections.get(intersection.getId()) == intersection"
    })
    private void addIntersection(Intersection intersection) {
        intersections.put(intersection.getId(), intersection);
    }

    @Requires({
        "intersections.containsKey(streetSection.getStartIntersection().getId())",
        "intersections.containsKey(streetSection.getEndIntersection().getId())"
    })
    private void addStreetSection(StreetSection streetSection) {
        Map<Integer, StreetSection> streetSectionsFromStartIntersection = streetSections.get(streetSection.getStartIntersection().getId());
        if (streetSectionsFromStartIntersection == null) {
            streetSectionsFromStartIntersection = new TreeMap<Integer, StreetSection>();
        }

        streetSectionsFromStartIntersection.put(streetSection.getEndIntersection().getId(), streetSection);
        streetSections.put(streetSection.getStartIntersection().getId(), streetSectionsFromStartIntersection);
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

    /**
     * Returns the intersection with the supplied id.
     *
     * @param idIntersection The id of intersection to retrieve
     * @return The intersection with the supplied id
     */
    @Requires({
        "intersections.containsKey(idIntersection)"
    })
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof CityMap)) {
//            return false;
//        } else if (obj == this) {
//            return true;
//        }
//        final CityMap other = (CityMap) obj;
//        // TODO: deep equals ?
//        return this.intersections.equals(other.intersections) && this.streetSections.equals(other.streetSections);
//    }
}
