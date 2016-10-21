package services.xml;

import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;
import models.Intersection;
import models.StreetSection;
import models.Warehouse;
import services.xml.exception.ParserException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParserTest {

    @Test
    public void parseCityMapTestByEquals() throws URISyntaxException, IOException, ParserException {
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

        File testMapFile = getFile("/services/xml/test-city-map-2x2.xml");
        Parser parser = new Parser();
        CityMap actualCityMap = parser.getCityMap(testMapFile);

        assertEquals(expectedCityMap, actualCityMap);
    }

    @Test
    public void parseDeliveryRequestTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();

        File testMapFile = getFile("/services/xml/test-city-map-2x2.xml");
        CityMap cityMap = parser.getCityMap(testMapFile);

        File testDeliveryRequestFile = getFile("/services/xml/test-deliveryRequest.xml");
        DeliveryRequest actualDeliveryRequest = parser.getDeliveryRequest(testDeliveryRequestFile, cityMap);
        
        Warehouse expectedW = new Warehouse(new Intersection(2, 140, 420));
        List<DeliveryAddress> expectedDA = new ArrayList<DeliveryAddress>();
        expectedDA.add(new DeliveryAddress(new Intersection(0, 134, 193), 100));
        expectedDA.add(new DeliveryAddress(new Intersection(3, 132, 470), 250));
        DeliveryRequest expectedDeliveryRequest = new DeliveryRequest(expectedW, expectedDA, 28_800);
        
        assertEquals(actualDeliveryRequest, expectedDeliveryRequest);
    }

    private File getFile(String location) throws URISyntaxException {
        URL testMapPath = getClass().getResource(location);
        assertNotNull(testMapPath);

        File file = null;
        file = new File(testMapPath.toURI());
        return file;
    }
}
