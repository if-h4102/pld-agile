package models;

import java.util.ArrayList;
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
}
