package services.xml;

import models.CityMap;
import models.DeliveryRequest;
import models.Intersection;
import models.StreetSection;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ParserTest {

    @Test
    public void parseCityMapTest() {
        URL testMapPath = getClass().getResource("/services/xml/test-city-map-2x2.xml");
        assertNotNull(testMapPath);

        File testMapFile = null;
        try {
            testMapFile = new File(testMapPath.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            assert(false); // The test has failed
        }

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

        assertEquals(expectedCityMap, actualCityMap);
    }
    
    @Test
    public void parseDeliveryRequestTest() {
        URL testDeliveryRequestPath = getClass().getResource("/services/xml/test-deliveryRequest.xml");
        assertNotNull(testDeliveryRequestPath);

        File testDeliveryRequestFile = null;
        try {
            testDeliveryRequestFile = new File(testDeliveryRequestPath.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            assert(false);
        }

        Parser parser = new Parser();
        DeliveryRequest actualDeliveryRequest = parser.getDeliveryRequest(testDeliveryRequestFile, null);
    }
}
