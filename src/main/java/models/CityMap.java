package models;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import java.util.*;

public class CityMap {

    /**
     * All known intersections.
     * Key: `intersectionId` Value: The associated Intersection
     */
    private Map<Integer, Intersection> intersections;

    /**
     * All known street sections.
     * Key: `startIntersectionId` Value: Map: - Key: `endIntersectionId` - Value: `StreetSection`, the StreetSection from the intersection
     * with the id `startIntersectionId` to the intersection with the id `endIntersectionId`
     */
    private Map<Integer, Map<Integer, StreetSection>> streetSections;

    /**
     * Create a city map which contain the given intersections and street sections.
     * @param intersections The intersections contained in the new city map.
     * @param streetSections The street sections contained in the city map.
     */
    public CityMap(Iterable<Intersection> intersections, Iterable<StreetSection> streetSections) {
        this.intersections = new TreeMap<Integer, Intersection>();
        this.streetSections = new TreeMap<Integer, Map<Integer, StreetSection>>();

        for (Intersection intersection : intersections) {
            this.addIntersection(intersection);
        }
        for (StreetSection streetSection : streetSections) {
            this.addStreetSection(streetSection);
        }
    }

    /**
     * Add the given intersection to the current city map.
     * @param intersection the intersection to add.
     */
    @Requires({"intersection != null", "!intersections.containsKey(intersection.getId())"})
    @Ensures({"intersections.containsKey(intersection.getId())", "intersections.containsValue(intersection)",
        "intersections.get(intersection.getId()) == intersection"})
    private void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.getId(), intersection);
    }

    /**
     * Add the given street section to the city map.
     * @param streetSection The street section to add to the city map.
     */
    @Requires({"streetSection != null", "intersections.containsKey(streetSection.getStartIntersection().getId())",
        "intersections.containsKey(streetSection.getEndIntersection().getId())"})
    private void addStreetSection(StreetSection streetSection) {
        Map<Integer, StreetSection> streetSectionsFromStartIntersection = streetSections.get(streetSection.getStartIntersection().getId());
        if (streetSectionsFromStartIntersection == null) {
            streetSectionsFromStartIntersection = new TreeMap<Integer, StreetSection>();
        }

        streetSectionsFromStartIntersection.put(streetSection.getEndIntersection().getId(), streetSection);
        this.streetSections.put(streetSection.getStartIntersection().getId(), streetSectionsFromStartIntersection);
    }

    /**
     * Find the shortest path between the start and the end way points
     * given as parameters.
     * @param startWaypoint Start point from which the shortest path begin.
     * @param endWaypoint End point on which the shortest path end.
     * @return A Route instance which contains a list of street sections
     * which corresponds to the shortest path between the start and the end
     * point given as parameters.
     */
    public Route shortestPath(AbstractWaypoint startWaypoint, AbstractWaypoint endWaypoint) {
        return this.shortestPath(startWaypoint, Collections.singletonList(endWaypoint)).get(0);
    }

    /**
     * Find the shortest paths between the start way point and each end way points
     * given as parameters.
     * @param startWayPoint The start point from which the shortest paths begin.
     * @param endWatPoints The end points on which the shortest paths end.
     * return A list of Route each composed of a list of street sections
     * which correspond to the shortests path between the start and each end points
     * given as parameters.
     */
    // TODO Complexity to improve by using a min-heap for the greys intersections
    @Requires({"startWaypoint != null", "endWaypoints != null",
        "intersections.containsValue(startWaypoint.getIntersection())"})
    protected List<Route> shortestPath(AbstractWaypoint startWaypoint, List<AbstractWaypoint> endWaypoints) {
        /* Key : the id of an intersection.
         * Value : an int which will be used as an array index. */
        Map<Integer, Integer> index = new TreeMap<Integer, Integer>();
        int counter = 0;
        for (Map.Entry<Integer, Intersection> entry : intersections.entrySet()) {
            index.put(entry.getKey(), counter++);
        }

        Intersection[] predecessors = new Intersection[intersections.size()];
        int[] durations = new int[intersections.size()];
        // Intersections which have not been visit yet.
        Map<Integer, Intersection> blacks = new TreeMap<Integer, Intersection>();
        /* Intersections which have been visited but whose neighbors have not all
         * been visited yet. */
        List<Intersection> greys = new LinkedList<Intersection>();
        /* Intersections which have been visited and whose all neighbors have
         * been visited too. */
        Map<Integer, Intersection> whites = new TreeMap<Integer, Intersection>(intersections);

        /* Remove the start way point to the whites intersections map and
         * put it in the grey intersections list. */
        whites.remove(startWaypoint.getId());
        greys.add(startWaypoint.getIntersection());
        for (int i = 0; i < durations.length; i++) {
            durations[i] = Integer.MAX_VALUE;
        }
        durations[index.get(startWaypoint.getId())] = 0;

        // While all intersections have not been visited...
        while (greys.size() != 0) {
            // Get the grey intersection with the smallest duration.
            Intersection minimalGreyIntersection = getMinimalGreyIntersection(index, greys, durations);
            // For each successor of this grey intersection...
            for (Intersection successor : getNeighbourIntersection(minimalGreyIntersection)) {
                /* If this successor is white or grey, then the street section between
                 * the intersection which is being visited and its successor is released. */
                if (!blacks.containsKey(successor.getId())) {
                    StreetSection streetSection = getStreetSection(minimalGreyIntersection, successor);
                    release(index, streetSection, predecessors, durations);
                    // If this successor is white, he becomes grey.
                    if (whites.containsKey(successor.getId())) {
                        whites.remove(successor.getId());
                        greys.add(successor);
                    }
                }
            }
            // The point which was being visited become black.
            greys.remove(minimalGreyIntersection);
            blacks.put(minimalGreyIntersection.getId(), minimalGreyIntersection);
        }
        /* Compute and return the list of Route which contains for each end way points
         * a list of street sections corresponding to the shortest path between the start
         * way point and the end way point of the route. */
        return computeReturn(index, predecessors, startWaypoint, endWaypoints);
    }

    /**
     * Get the grey intersection with the lowest duration value.
     * @param index The map used to get a array index from the intersection id.
     * @param greys The List of grey intersections.
     * @param durations An array which contains the duration of each intersection.
     * @return The grey intersection with the lowest duration value.
     */
    // TODO Improve complexity
    @Requires({"greys != null", "durations != null", "greys.size() <= durations.length"})
    @Ensures({"greys.contains(result)"})
    private Intersection getMinimalGreyIntersection(Map<Integer, Integer> index, List<Intersection> greys, int[] durations) {
        int minDuration = Integer.MAX_VALUE;
        Intersection minimalGreyIntersection = null;

        for (Intersection greyIntersection : greys) {
            if (durations[index.get(greyIntersection.getId())] < minDuration) {
                minDuration = durations[index.get(greyIntersection.getId())];
                minimalGreyIntersection = greyIntersection;
            }
        }
        return minimalGreyIntersection;
    }

    /**
     * Release the street section given as parameter.
     * In other words update the predecessor and the duration value of the intersection
     * at the end of the given street section.
     * @param index The map used to get a array index from the intersection id.
     * @param streetSection The street section to released.
     * @param predecessors An array which contains the predecessor of each intersection.
     * @param durations An array which contains the duration of each intersection.
     */
    @Requires({
        "streetSection != null",
        "predecessors != null",
        "durations != null",
        "predecessors.length == durations.length"})
    @Ensures({"durations[index.get(streetSection.getEndIntersection().getId())] <= "
        + "durations[index.get(streetSection.getStartIntersection().getId())] + streetSection.getDuration()"})
    private void release(Map<Integer, Integer> index, StreetSection streetSection, /* IN/OUT */ Intersection[] predecessors, int[] durations) {
        int idStartIntersection = streetSection.getStartIntersection().getId();
        int idEndIntersection = streetSection.getEndIntersection().getId();

        if (durations[index.get(idEndIntersection)] > durations[index.get(idStartIntersection)] + streetSection.getDuration()) {
            durations[index.get(idEndIntersection)] = durations[index.get(idStartIntersection)] + streetSection.getDuration();
            predecessors[index.get(idEndIntersection)] = streetSection.getStartIntersection();
        }
    }

    /**
     * Create a list of Route which contains for each one the shortest path between
     * its start and it end way point using the predecessors array given as parameter.
     * @param index The map used to get a array index from the intersection id.
     * @param predecessors An array which contains the predecessor of each intersection.
     * @param startWaypoint The way point from which the shortest paths begin.
     * @param endWaypoints The way points on which the shortest paths end.
     * @return Return the list of Route which contains the shortest paths.
     */
    @Requires({"endWaypoints != null", "startWaypoint != null"})
    private List<Route> computeReturn(Map<Integer, Integer> index, Intersection[] predecessors, AbstractWaypoint startWaypoint, List<AbstractWaypoint> endWaypoints) {
        List<Route> result = new ArrayList<Route>();
        for (AbstractWaypoint endWaypoint : endWaypoints) {
            List<StreetSection> streetSectionsInCurrentRoute = new LinkedList<StreetSection>();

            Intersection currentIntersection = endWaypoint.getIntersection();
            Intersection precedentIntersection = predecessors[index.get(currentIntersection.getId())];

            /* For a given end way point, use the predecessor array to fill the list of way points
             * whereby the shortest path go through. */
            while (precedentIntersection != null) {
                streetSectionsInCurrentRoute.add(0, getStreetSection(precedentIntersection, currentIntersection));
                currentIntersection = precedentIntersection;
                precedentIntersection = predecessors[index.get(currentIntersection.getId())];
            }

            result.add(new Route(startWaypoint, endWaypoint, streetSectionsInCurrentRoute));
        }
        return result;
    }

    /**
     * Get a collection of intersections which are the neighbors of the given intersection.
     * @param intersection The intersection for which the neighbors have to be found.
     * @return A collection of intersections which are the neighbors of the given intersection.
     */
    @Requires({"intersections.containsValue(intersection)"})
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

    /**
     * Compute a delivery graph by using a delivery request.
     * @param request The delivery request for which the delivery graph has to be computed.
     * @return A delivery graph which contains the way points contained in the given
     * delivery request.
     */
    @Requires({"request != null", "request.getWarehouse() != null", "request.getDeliveryAddresses() != null"})
    public DeliveryGraph computeDeliveryGraph(DeliveryRequest request) {
        /* Create a list of way points which contains the warehouse of the request
         * and all its delivery adresses. */
        List<AbstractWaypoint> pointsContainedInRequest = new LinkedList<AbstractWaypoint>();
        pointsContainedInRequest.add(request.getWarehouse());

        Iterable<DeliveryAddress> adressContainedInRequest = request.getDeliveryAddresses();
        for (DeliveryAddress adress : adressContainedInRequest) {
            pointsContainedInRequest.add(adress);
        }

        Map<AbstractWaypoint, Map<AbstractWaypoint, Route>> mappedRoutes = new TreeMap<AbstractWaypoint, Map<AbstractWaypoint, Route>>();
        for (int i = 0; i < pointsContainedInRequest.size(); i++) {
            // Always get first element as it will be deleted and pushed back at the end of the list.
            AbstractWaypoint startPoint = pointsContainedInRequest.get(0);
            Map<AbstractWaypoint, Route> routesFromGivenStartPoint = new TreeMap<AbstractWaypoint, Route>();
            // Remove start point from list to prevent zero point route.
            pointsContainedInRequest.remove(0); //Remove startPoint from the list.
            List<Route> shortestPathRoutes = shortestPath(startPoint, pointsContainedInRequest);
            pointsContainedInRequest.add(startPoint);// Add startPoint back to the list (at the end).

            for (Route route : shortestPathRoutes) {
                routesFromGivenStartPoint.put(route.getEndWaypoint(), route);
            }
            mappedRoutes.put(startPoint, routesFromGivenStartPoint);
        }
        return new DeliveryGraph(request.getCityMap(), mappedRoutes);
    }

    /**
     * Create a new list which contains all the intersections of the city map.
     * @return the new list which contains all the intersections of the
     * city map.
     */
    public List<Intersection> getIntersections() {
        List<Intersection> listIntersection = new ArrayList<Intersection>();
        listIntersection.addAll(this.intersections.values());
        return listIntersection;
    }

    /**
     * Create a new list which contains all the street sections of the city map.
     * @return the new list which contains all the street sections of the
     * city map.
     */
    public List<StreetSection> getStreetSections() {
        List<StreetSection> listStreetSection = new ArrayList<StreetSection>();
        for (Map<Integer, StreetSection> value : this.streetSections.values()) {
            listStreetSection.addAll(value.values());
        }
        return listStreetSection;
    }

    /**
     * Get the intersection with the supplied id.
     * @param idIntersection the id of intersection to retrieve. The id must be in the map.
     * @return the intersection with the supplied id.
     */
    @Requires({"intersections.containsKey(idIntersection)"})
    public Intersection getIntersection(int idIntersection) {
        return this.intersections.get(idIntersection);
    }

    /**
     * Check if the city map contains an intersection with the given id.
     * @param idIntersection The id of the intersection to search among the
     * intersections of the city map.
     * @return True if the city map contains an intersection with the given id,
     * false otherwise.
     */
    public boolean isIntersectionInCityMap(int idIntersection) {
        return this.intersections.containsKey(idIntersection);
    }

    /**
     * Get the street section contained in the city map which begins with the given start
     * intersection and which ends with the given end intersection.
     * @param startIntersection The intersection by which have to begin the searched
     * street section.
     * @param endIntersection The intersection by which have to end the searched
     * street section.
     * @return a street section contained in the city map which begins with the given start
     * intersection and which ends with the given end intersection.
     * Return null if the city map does not contain such a street section.
     */
    @Requires({"intersections.containsValue(startIntersection)", "intersections.containsValue(endIntersection)"})
    @Ensures({"result.getStartIntersection().equals(startIntersection)", "result.getEndIntersection().equals(endIntersection)"})
    private StreetSection getStreetSection(Intersection startIntersection, Intersection endIntersection) {
        Map<Integer, StreetSection> outStreetSections = this.streetSections.get(startIntersection.getId());
        if (outStreetSections != null) {
            return outStreetSections.get(endIntersection.getId());
        }
        return null;
    }

    /**
     * Check if the given object is equal to the current one.
     * @return true if the given object is equal to the current one,
     * false otherwise.
     */
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
