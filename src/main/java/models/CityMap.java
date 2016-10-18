package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CityMap {

    /**
     * Key: `intersectionId` Value: The associated Intersection
     */
    private Map<Integer, Intersection> intersections;

    /**
     * Key: `startIntersectionId` Value: Map: - Key: `endIntersectionId` - Value: `StreetSection`, the StreetSection from the intersection
     * with the id `startIntersectionId` to the intersection with the id `endIntersectionId`
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

    @Requires({ "intersection != null", "!intersections.containsKey(intersection.getId())" })
    @Ensures({ "intersections.containsKey(intersection.getId())", "intersections.containsValue(intersection)",
            "intersections.get(intersection.getId()) == intersection" })
    private void addIntersection(Intersection intersection) {
        intersections.put(intersection.getId(), intersection);
    }

    @Requires({ "streetSection != null", "intersections.containsKey(streetSection.getStartIntersection().getId())",
            "intersections.containsKey(streetSection.getEndIntersection().getId())" })
    private void addStreetSection(StreetSection streetSection) {
        Map<Integer, StreetSection> streetSectionsFromStartIntersection = streetSections.get(streetSection.getStartIntersection().getId());
        if (streetSectionsFromStartIntersection == null) {
            streetSectionsFromStartIntersection = new TreeMap<Integer, StreetSection>();
        }

        streetSectionsFromStartIntersection.put(streetSection.getEndIntersection().getId(), streetSection);
        streetSections.put(streetSection.getStartIntersection().getId(), streetSectionsFromStartIntersection);
    }

    // TODO Complexity to improve by using a tas-min for the greys intersections
    @Requires({ "startWayPoint != null", "endWayPoints != null", "!endWayPoints.contains(startWayPoint)",
            "intersections.containsValue(startWayPoint.getIntersection())" })
    private List<Route> shortestPath(AbstractWayPoint startWayPoint, List<AbstractWayPoint> endWayPoints) {
        Intersection[] predecessors = new Intersection[intersections.size()];
        int[] durations = new int[intersections.size()];
        Map<Integer, Intersection> blacks = new TreeMap<Integer, Intersection>();
        List<Intersection> greys = new LinkedList<Intersection>();
        Map<Integer, Intersection> whites = new TreeMap<Integer, Intersection>(intersections);

        whites.remove(startWayPoint.getId());
        greys.add(startWayPoint.getIntersection());
        for (int i = 0; i < durations.length; i++) {
            durations[i] = Integer.MAX_VALUE;
        }
        durations[startWayPoint.getId()] = 0;

        while (greys.size() != 0) {
            Intersection minimalGreyIntersection = getMinimalGreyIntersection(greys, durations);
            for (Intersection successor : getNeighbourIntersection(minimalGreyIntersection)) {
                if (!blacks.containsKey(successor.getId())) {
                    StreetSection streetSection = getStreetSection(minimalGreyIntersection, successor);
                    release(streetSection, predecessors, durations);
                    if (whites.containsKey(successor.getId())) {
                        whites.remove(successor.getId());
                        greys.add(successor);
                    }
                }
            }
            greys.remove(minimalGreyIntersection);
            blacks.put(minimalGreyIntersection.getId(), minimalGreyIntersection);
        }

        return computeReturn(predecessors, startWayPoint, endWayPoints);
    }

    // TODO Improve complexity
    @Requires({ "greys != null", "durations != null", "greys.size() <= durations.length" })
    @Ensures({ "greys.contains(result)" })
    private Intersection getMinimalGreyIntersection(List<Intersection> greys, int[] durations) {
        int minDuration = Integer.MAX_VALUE;
        Intersection minimalGreyIntersection = null;

        for (Intersection greyIntersection : greys) {
            if (durations[greyIntersection.getId()] < minDuration) {
                minDuration = durations[greyIntersection.getId()];
                minimalGreyIntersection = greyIntersection;
            }
        }
        return minimalGreyIntersection;
    }

    @Requires({ "streetSection != null", "predecessors != null", "durations != null", "predecessors.length == durations.length",
            "streetSection.getStartIntersection().getId() < durations.length",
            "streetSection.getEndIntersection().getId() < durations.length" })
    @Ensures({ "durations[streetSection.getEndIntersection().getId()] <= "
            + "durations[streetSection.getStartIntersection().getId()] + streetSection.getDuration()" })
    private void release(StreetSection streetSection, /* IN/OUT */ Intersection[] predecessors, int[] durations) {
        int idStartIntersection = streetSection.getStartIntersection().getId();
        int idEndIntersection = streetSection.getEndIntersection().getId();

        if (durations[idEndIntersection] > durations[idStartIntersection] + streetSection.getDuration()) {
            durations[idEndIntersection] = durations[idStartIntersection] + streetSection.getDuration();
            predecessors[idEndIntersection] = streetSection.getStartIntersection();
        }
    }

    @Requires({ "endWayPoints != null", "startWayPoint != null", "!endWayPoints.contains(startWayPoint)" })
    private List<Route> computeReturn(Intersection[] predecessors, AbstractWayPoint startWayPoint, List<AbstractWayPoint> endWayPoints) {
        List<Route> result = new ArrayList<Route>();
        for (AbstractWayPoint endWayPoint : endWayPoints) {
            List<StreetSection> streetSections = new LinkedList<StreetSection>();

            Intersection currentIntersection = endWayPoint.getIntersection();
            Intersection precedentIntersection = predecessors[currentIntersection.getId()];
            do {
                streetSections.add(0, getStreetSection(precedentIntersection, currentIntersection));
                currentIntersection = precedentIntersection;
                precedentIntersection = predecessors[currentIntersection.getId()];
            } while (!precedentIntersection.equals(startWayPoint.getIntersection()));

            result.add(new Route(startWayPoint, endWayPoint, streetSections));
        }

        return result;
    }

    @Requires({ "intersections.containsValue(intersection)" })
    private Collection<Intersection> getNeighbourIntersection(Intersection intersection) {
        Map<Integer, StreetSection> outStreetSections = streetSections.get(intersection.getId());
        List<Intersection> neighbourIntersections = new ArrayList<Intersection>();

        if (outStreetSections != null) {
            for (Map.Entry<Integer, StreetSection> entry : outStreetSections.entrySet()) {
                neighbourIntersections.add(getIntersection(entry.getKey()));
            }
        }
        return neighbourIntersections;
    }

    @Requires({ "request != null", "request.getWareHouse() != null", "request.getDeliveryAddresses() != null" })
    public DeliveryGraph computeDeliveryGraph(DeliveryRequest request) {
        List<AbstractWayPoint> pointsContainedInRequest = new LinkedList<AbstractWayPoint>();
        pointsContainedInRequest.add(request.getWareHouse());

        Iterable<DeliveryAddress> adressContainedInRequest = request.getDeliveryAddresses();
        for (DeliveryAddress adress : adressContainedInRequest) {
            pointsContainedInRequest.add(adress);
        }

        Map<AbstractWayPoint, Map<AbstractWayPoint, Route>> mappedRoutes = new TreeMap<AbstractWayPoint, Map<AbstractWayPoint, Route>>();
        for (int i = 0; i < pointsContainedInRequest.size(); i++) {
            AbstractWayPoint startPoint = pointsContainedInRequest.get(i);
            Map<AbstractWayPoint, Route> routesFromGivenStartPoint = new TreeMap<AbstractWayPoint, Route>();
            pointsContainedInRequest.remove(i);
            List<Route> shortestPathRoutes = shortestPath(startPoint, pointsContainedInRequest);
            for (Route route : shortestPathRoutes) {
                routesFromGivenStartPoint.put(route.getEndWaypoint(), route);
            }
            mappedRoutes.put(startPoint, routesFromGivenStartPoint);
            pointsContainedInRequest.add(i, startPoint);
        }
        return new DeliveryGraph(mappedRoutes);
    }

    // TODO
    public List<Intersection> getIntersections() {
        List<Intersection> listIntersection = new ArrayList<Intersection>();
        for (Intersection value : intersections.values()) {
            listIntersection.add(value);
        }
        return listIntersection;
    }

    // TODO
    public List<StreetSection> getStreetSections() {
        List<StreetSection> listStreetSection = new ArrayList<StreetSection>();
        for (Map<Integer, StreetSection> value : streetSections.values()) {
            for (StreetSection section : value.values())
                listStreetSection.add(section);
        }
        return listStreetSection;
    }

    /**
     * Returns the intersection with the supplied id.
     *
     * @param idIntersection
     *            The id of intersection to retrieve. The id must be in the map.
     * @return The intersection with the supplied id
     */
    @Requires({ "intersections.containsKey(idIntersection)" })
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }

    @Requires({ "intersections.containsValue(startIntersection)", "intersections.containsValue(endIntersection)" })
    @Ensures({ "result.getStartIntersection().equals(startIntersection)", "result.getEndIntersection().equals(endIntersection)" })
    private StreetSection getStreetSection(Intersection startIntersection, Intersection endIntersection) {
        Map<Integer, StreetSection> outStreetSections = streetSections.get(startIntersection.getId());
        if (outStreetSections != null) {
            return outStreetSections.get(endIntersection.getId());
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CityMap)) {
            return false;
        } else if (this == obj)
            return true;
        CityMap other = (CityMap) obj;

        if (intersections.size() != other.intersections.size())
            return false;
        for (Map.Entry<Integer, Intersection> entry : intersections.entrySet()) {
            if (!entry.getValue().equals(other.intersections.get(entry.getKey())))
                return false;
        }

        if (streetSections.size() != other.streetSections.size())
            return false;
        for (Map.Entry<Integer, Map<Integer, StreetSection>> entrySubMap : streetSections.entrySet()) {
            Map<Integer, StreetSection> otherSubMap = other.streetSections.get(entrySubMap.getKey());
            if (otherSubMap == null && entrySubMap.getValue() != null || otherSubMap != null && entrySubMap.getKey() == null)
                return false;
            if (otherSubMap == null)
                continue; // The two sub item are null, so they are equals
            if (otherSubMap.size() != entrySubMap.getValue().size())
                return false;

            for (Map.Entry<Integer, StreetSection> entry : entrySubMap.getValue().entrySet()) {
                if (!entry.getValue().equals(otherSubMap.get(entry.getKey())))
                    return false;
            }
        }

        return true;
    }
}
