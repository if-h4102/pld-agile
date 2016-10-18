package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CityMapTest {

    @Test
    public void deepEqualsTest() {
        ArrayList<Intersection> baseI = new ArrayList<Intersection>();
        baseI.add(new Intersection(0, 1, 5));
        baseI.add(new Intersection(1, 4, 3));
        baseI.add(new Intersection(2, 4, 7));
        baseI.add(new Intersection(3, 1, 9));
        ArrayList<StreetSection> baseSS = new ArrayList<StreetSection>();
        baseSS.add(new StreetSection(156, 23, "h0", baseI.get(0), baseI.get(1)));
        baseSS.add(new StreetSection(156, 21, "h0", baseI.get(1), baseI.get(0)));
        baseSS.add(new StreetSection(125, 42, "h0", baseI.get(1), baseI.get(2)));
        baseSS.add(new StreetSection(125, 35, "h0", baseI.get(2), baseI.get(1)));
        baseSS.add(new StreetSection(431, 24, "h1", baseI.get(1), baseI.get(3)));
        baseSS.add(new StreetSection(431, 34, "h1", baseI.get(3), baseI.get(1)));
        CityMap base = new CityMap(baseI, baseSS);

        ArrayList<Intersection> sameAsBaseI = new ArrayList<Intersection>();
        sameAsBaseI.add(new Intersection(0, 1, 5));
        sameAsBaseI.add(new Intersection(1, 4, 3));
        sameAsBaseI.add(new Intersection(2, 4, 7));
        sameAsBaseI.add(new Intersection(3, 1, 9));
        ArrayList<StreetSection> sameAsBaseSS = new ArrayList<StreetSection>();
        sameAsBaseSS.add(new StreetSection(156, 23, "h0", sameAsBaseI.get(0), sameAsBaseI.get(1)));
        sameAsBaseSS.add(new StreetSection(156, 21, "h0", sameAsBaseI.get(1), sameAsBaseI.get(0)));
        sameAsBaseSS.add(new StreetSection(125, 42, "h0", sameAsBaseI.get(1), sameAsBaseI.get(2)));
        sameAsBaseSS.add(new StreetSection(125, 35, "h0", sameAsBaseI.get(2), sameAsBaseI.get(1)));
        sameAsBaseSS.add(new StreetSection(431, 24, "h1", sameAsBaseI.get(1), sameAsBaseI.get(3)));
        sameAsBaseSS.add(new StreetSection(431, 34, "h1", sameAsBaseI.get(3), sameAsBaseI.get(1)));
        CityMap sameAsBase = new CityMap(sameAsBaseI, sameAsBaseSS);

        ArrayList<Intersection> modifiedIntersectionI = new ArrayList<Intersection>();
        modifiedIntersectionI.add(new Intersection(0, 1, 5));
        modifiedIntersectionI.add(new Intersection(1, 4, 3));
        modifiedIntersectionI.add(new Intersection(2, 4, 7));
        modifiedIntersectionI.add(new Intersection(4, 1, 9)); // The id of the intersection have changed
        ArrayList<StreetSection> modifiedIntersectionSS = new ArrayList<StreetSection>();
        modifiedIntersectionSS.add(new StreetSection(156, 23, "h0", modifiedIntersectionI.get(0), modifiedIntersectionI.get(1)));
        modifiedIntersectionSS.add(new StreetSection(156, 21, "h0", modifiedIntersectionI.get(1), modifiedIntersectionI.get(0)));
        modifiedIntersectionSS.add(new StreetSection(125, 42, "h0", modifiedIntersectionI.get(1), modifiedIntersectionI.get(2)));
        modifiedIntersectionSS.add(new StreetSection(125, 35, "h0", modifiedIntersectionI.get(2), modifiedIntersectionI.get(1)));
        modifiedIntersectionSS.add(new StreetSection(431, 24, "h1", modifiedIntersectionI.get(1), modifiedIntersectionI.get(3)));
        modifiedIntersectionSS.add(new StreetSection(431, 34, "h1", modifiedIntersectionI.get(3), modifiedIntersectionI.get(1)));
        CityMap modifiedIntersection = new CityMap(modifiedIntersectionI, modifiedIntersectionSS);

        ArrayList<Intersection> moreIntersectionI = new ArrayList<Intersection>();
        moreIntersectionI.add(new Intersection(0, 1, 5));
        moreIntersectionI.add(new Intersection(1, 4, 3));
        moreIntersectionI.add(new Intersection(2, 4, 7));
        moreIntersectionI.add(new Intersection(3, 1, 9));
        moreIntersectionI.add(new Intersection(4, 0, 0)); // This intersection has been added
        ArrayList<StreetSection> moreIntersectionSS = new ArrayList<StreetSection>();
        moreIntersectionSS.add(new StreetSection(156, 23, "h0", moreIntersectionI.get(0), moreIntersectionI.get(1)));
        moreIntersectionSS.add(new StreetSection(156, 21, "h0", moreIntersectionI.get(1), moreIntersectionI.get(0)));
        moreIntersectionSS.add(new StreetSection(125, 42, "h0", moreIntersectionI.get(1), moreIntersectionI.get(2)));
        moreIntersectionSS.add(new StreetSection(125, 35, "h0", moreIntersectionI.get(2), moreIntersectionI.get(1)));
        moreIntersectionSS.add(new StreetSection(431, 24, "h1", moreIntersectionI.get(1), moreIntersectionI.get(3)));
        moreIntersectionSS.add(new StreetSection(431, 34, "h1", moreIntersectionI.get(3), moreIntersectionI.get(1)));
        CityMap moreIntersection = new CityMap(moreIntersectionI, moreIntersectionSS);

        ArrayList<Intersection> modifiedStreetSectionI = new ArrayList<Intersection>();
        modifiedStreetSectionI.add(new Intersection(0, 1, 5));
        modifiedStreetSectionI.add(new Intersection(1, 4, 3));
        modifiedStreetSectionI.add(new Intersection(2, 4, 7));
        modifiedStreetSectionI.add(new Intersection(3, 1, 9));
        ArrayList<StreetSection> modifiedStreetSectionSS = new ArrayList<StreetSection>();
        // The first two streetSection have been modified
        modifiedStreetSectionSS.add(new StreetSection(255, 23, "h2", modifiedStreetSectionI.get(0), modifiedStreetSectionI.get(1)));
        modifiedStreetSectionSS.add(new StreetSection(255, 21, "h2", modifiedStreetSectionI.get(1), modifiedStreetSectionI.get(0)));
        modifiedStreetSectionSS.add(new StreetSection(125, 42, "h0", modifiedStreetSectionI.get(1), modifiedStreetSectionI.get(2)));
        modifiedStreetSectionSS.add(new StreetSection(125, 35, "h0", modifiedStreetSectionI.get(2), modifiedStreetSectionI.get(1)));
        modifiedStreetSectionSS.add(new StreetSection(431, 24, "h1", modifiedStreetSectionI.get(1), modifiedStreetSectionI.get(3)));
        modifiedStreetSectionSS.add(new StreetSection(431, 34, "h1", modifiedStreetSectionI.get(3), modifiedStreetSectionI.get(1)));
        CityMap modifiedStreetSection = new CityMap(modifiedStreetSectionI, modifiedStreetSectionSS);
        
        ArrayList<Intersection> moreStreetSectionI = new ArrayList<Intersection>();
        moreStreetSectionI.add(new Intersection(0, 1, 5));
        moreStreetSectionI.add(new Intersection(1, 4, 3));
        moreStreetSectionI.add(new Intersection(2, 4, 7));
        moreStreetSectionI.add(new Intersection(3, 1, 9));
        ArrayList<StreetSection> moreStreetSectionSS = new ArrayList<StreetSection>();
        moreStreetSectionSS.add(new StreetSection(156, 23, "h0", moreStreetSectionI.get(0), moreStreetSectionI.get(1)));
        moreStreetSectionSS.add(new StreetSection(156, 21, "h0", moreStreetSectionI.get(1), moreStreetSectionI.get(0)));
        moreStreetSectionSS.add(new StreetSection(125, 42, "h0", moreStreetSectionI.get(1), moreStreetSectionI.get(2)));
        moreStreetSectionSS.add(new StreetSection(125, 35, "h0", moreStreetSectionI.get(2), moreStreetSectionI.get(1)));
        moreStreetSectionSS.add(new StreetSection(431, 24, "h1", moreStreetSectionI.get(1), moreStreetSectionI.get(3)));
        moreStreetSectionSS.add(new StreetSection(431, 34, "h1", moreStreetSectionI.get(3), moreStreetSectionI.get(1)));
        // The two following streetSections have been added
        moreStreetSectionSS.add(new StreetSection(255, 23, "h2", moreStreetSectionI.get(2), moreStreetSectionI.get(3)));
        moreStreetSectionSS.add(new StreetSection(255, 31, "h2", moreStreetSectionI.get(3), moreStreetSectionI.get(2)));
        CityMap moreStreetSection = new CityMap(moreStreetSectionI, moreStreetSectionSS);

        assertTrue(base.equals(base));
        assertTrue(base.equals(sameAsBase));
        assertTrue(!base.equals(modifiedIntersection));
        assertTrue(!base.equals(moreIntersection));
        assertTrue(!base.equals(modifiedStreetSection));
        assertTrue(!base.equals(moreStreetSection));
    }
    
    @Test
    public void computeDeliveryGraphTest() {
        List<Intersection> intersections = new ArrayList<Intersection>();
        Intersection intersection0 = new Intersection(52,1,2);
        Intersection intersection1 = new Intersection(133,2,3);
        Intersection intersection2 = new Intersection(259,4,3);
        Intersection intersection3 = new Intersection(123,4,1);
        Intersection intersection4 = new Intersection(925,2,1);
        intersections.add(intersection0);
        intersections.add(intersection1);
        intersections.add(intersection2);
        intersections.add(intersection3);
        intersections.add(intersection4);
        
        List<StreetSection> streetSections = new ArrayList<StreetSection>();
        StreetSection streetSection01 = new StreetSection(3,1,"h0",intersection0, intersection1);
        StreetSection streetSection04 = new StreetSection(5,1,"h1",intersection0, intersection4);
        StreetSection streetSection12 = new StreetSection(6,1,"h2",intersection1, intersection2);
        StreetSection streetSection14 = new StreetSection(1,1,"h3",intersection1, intersection4);
        StreetSection streetSection23 = new StreetSection(2,1,"h4",intersection2, intersection3);
        StreetSection streetSection30 = new StreetSection(3,1,"v0",intersection3, intersection0);
        StreetSection streetSection32 = new StreetSection(7,1,"v1",intersection3, intersection2);
        StreetSection streetSection41 = new StreetSection(1,1,"v2",intersection4, intersection1);
        StreetSection streetSection42 = new StreetSection(3,1,"v3",intersection4, intersection2);
        StreetSection streetSection43 = new StreetSection(6,1,"v4",intersection4, intersection3);
        streetSections.add(streetSection01);
        streetSections.add(streetSection04);
        streetSections.add(streetSection12);
        streetSections.add(streetSection14);
        streetSections.add(streetSection23);
        streetSections.add(streetSection30);
        streetSections.add(streetSection32);
        streetSections.add(streetSection41);
        streetSections.add(streetSection42);
        streetSections.add(streetSection43);
        
        CityMap cityMap = new CityMap(intersections,streetSections);
        
        Warehouse warehouse = new Warehouse(intersection0);

        Set<DeliveryAddress> deliveryAdress = new TreeSet<DeliveryAddress>();
        DeliveryAddress deliveryAdress0 = new DeliveryAddress(intersection1,5);
        DeliveryAddress deliveryAdress1 = new DeliveryAddress(intersection2,5);
        DeliveryAddress deliveryAdress2 = new DeliveryAddress(intersection4,5);
        deliveryAdress.add(deliveryAdress0);
        deliveryAdress.add(deliveryAdress1);
        deliveryAdress.add(deliveryAdress2);
        
        DeliveryRequest request = new DeliveryRequest(warehouse, deliveryAdress, 8);
        
        DeliveryGraph deliveryGraph = cityMap.computeDeliveryGraph(request);
        
        Route deliveryGraphRoute;
        Route expectedRoute;
        List<StreetSection> expectedRouteStreetSections = new ArrayList<StreetSection>();
        
        deliveryGraphRoute = deliveryGraph.getRoute(warehouse, deliveryAdress0);
        expectedRouteStreetSections.add(streetSection01);
        expectedRoute = new Route(warehouse,deliveryAdress0,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(warehouse, deliveryAdress1);
        expectedRouteStreetSections.add(streetSection01);
        expectedRouteStreetSections.add(streetSection14);
        expectedRouteStreetSections.add(streetSection42);
        expectedRoute = new Route(warehouse,deliveryAdress1,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(warehouse, deliveryAdress2);
        expectedRouteStreetSections.add(streetSection01);
        expectedRouteStreetSections.add(streetSection14);
        expectedRoute = new Route(warehouse,deliveryAdress2,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress0, warehouse);
        expectedRouteStreetSections.add(streetSection14);
        expectedRouteStreetSections.add(streetSection42);
        expectedRouteStreetSections.add(streetSection23);
        expectedRouteStreetSections.add(streetSection30);
        expectedRoute = new Route(deliveryAdress0,warehouse,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress0, deliveryAdress1);
        expectedRouteStreetSections.add(streetSection14);
        expectedRouteStreetSections.add(streetSection42);
        expectedRoute = new Route(deliveryAdress0,deliveryAdress1,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress0, deliveryAdress2);
        expectedRouteStreetSections.add(streetSection14);
        expectedRoute = new Route(deliveryAdress0,deliveryAdress2,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress1, warehouse);
        expectedRouteStreetSections.add(streetSection23);
        expectedRouteStreetSections.add(streetSection30);
        expectedRoute = new Route(deliveryAdress1,warehouse,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress1, deliveryAdress0);
        expectedRouteStreetSections.add(streetSection23);
        expectedRouteStreetSections.add(streetSection30);
        expectedRouteStreetSections.add(streetSection01);
        expectedRoute = new Route(deliveryAdress1,deliveryAdress0,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress1, deliveryAdress2);
        expectedRouteStreetSections.add(streetSection23);
        expectedRouteStreetSections.add(streetSection30);
        expectedRouteStreetSections.add(streetSection01);
        expectedRouteStreetSections.add(streetSection14);
        expectedRoute = new Route(deliveryAdress1,deliveryAdress2,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress2, warehouse);
        expectedRouteStreetSections.add(streetSection42);
        expectedRouteStreetSections.add(streetSection23);
        expectedRouteStreetSections.add(streetSection30);
        expectedRoute = new Route(deliveryAdress2,warehouse,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress2, deliveryAdress0);
        expectedRouteStreetSections.add(streetSection41);
        expectedRoute = new Route(deliveryAdress2,deliveryAdress0,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
        expectedRouteStreetSections.clear();
        deliveryGraphRoute = deliveryGraph.getRoute(deliveryAdress2, deliveryAdress1);
        expectedRouteStreetSections.add(streetSection42);
        expectedRoute = new Route(deliveryAdress2,deliveryAdress1,expectedRouteStreetSections);
        assertTrue(deliveryGraphRoute.equals(expectedRoute));
        
    }
}
