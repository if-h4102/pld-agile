package services.xml;

import models.CityMap;
import models.Intersection;
import models.StreetSection;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ParserTest {
    @Test()
    public void parseCityMap2x2() throws Exception {
        URL testMapPath = getClass().getResource("/services/xml/test-city-map-2x2.xml");
        Assert.assertNotNull(testMapPath);

        File testMapFile = new File(testMapPath.toURI());

        Parser parser = new Parser();

        List<Intersection> intersections = new LinkedList<>();
        intersections.add(new Intersection(0, 134, 193));
        intersections.add(new Intersection(1, 195, 291));
        intersections.add(new Intersection(2, 140, 420));
        intersections.add(new Intersection(3, 132, 470));

        List<StreetSection> streetSections = new LinkedList<>();
        streetSections.add(new StreetSection(9234, 41, "h0", intersections.get(0), intersections.get(1)));
        streetSections.add(new StreetSection(13666, 47, "h0", intersections.get(1), intersections.get(0)));
        streetSections.add(new StreetSection(4076, 40, "v0", intersections.get(0), intersections.get(3)));
        streetSections.add(new StreetSection(11218, 38, "v0", intersections.get(3), intersections.get(0)));
        streetSections.add(new StreetSection(9234, 46, "v1", intersections.get(1), intersections.get(2)));
        streetSections.add(new StreetSection(11218, 39, "v1", intersections.get(2), intersections.get(1)));
        streetSections.add(new StreetSection(4050, 40, "h1", intersections.get(2), intersections.get(3)));
        streetSections.add(new StreetSection(10257, 38, "h1", intersections.get(3), intersections.get(2)));
        streetSections.add(new StreetSection(4050, 40, "diag", intersections.get(0), intersections.get(2)));
        streetSections.add(new StreetSection(15203, 46, "diag", intersections.get(2), intersections.get(0)));

        CityMap expectedCityMap = new CityMap(intersections, streetSections);
        CityMap actualCityMap = parser.getCityMap(testMapFile);

        // TODO: implement deepEquals for CityMap ?
        // Assert.assertEquals(expectedCityMap, actualCityMap);
    }
}
