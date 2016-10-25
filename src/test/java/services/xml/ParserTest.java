package services.xml;

import models.CityMap;
import models.DeliveryAddress;
import models.DeliveryRequest;
import models.Intersection;
import models.StreetSection;
import models.Warehouse;
import services.xml.exception.ParserDuplicateObjectException;
import services.xml.exception.ParserException;
import services.xml.exception.ParserIntegerValueException;
import services.xml.exception.ParserNodesNumberException;
import services.xml.exception.ParserShouldBeIntegerValueException;
import services.xml.exception.ParserSyntaxException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        File cityMapXmlFile = getFile("/services/xml/cityMap2x2.xml");
        Parser parser = new Parser();
        CityMap actualCityMap = parser.getCityMap(cityMapXmlFile);

        assertEquals(expectedCityMap, actualCityMap);
    }

    @Test
    public void parseCityMapMalformedXmlFileTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapMalformedXml.xml");

        thrown.expect(ParserSyntaxException.class);
        thrown.expectMessage("Les structures de document XML doivent commencer et se terminer dans la même entité.");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithoutStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithoutStreetSection.xml");

        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be at least 1 street section in a cityMap");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadIdStartStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadIdStartStreetSection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The start of a street section must exist");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadIdEndStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadIdEndStreetSection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The end of a street section must exist");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadLengthStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadLengthStreetSection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The length of a street section must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadSpeedStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadSpeedStreetSection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The speed of a street section must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithAttributeStreetSectionNotANumberTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithAttributeStreetSectionNotANumber.xml");

        thrown.expect(ParserShouldBeIntegerValueException.class);
        thrown.expectMessage("lengthStreetSection");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadDuplicateStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithDuplicateStreetSection.xml");

        thrown.expect(ParserDuplicateObjectException.class);
        thrown.expectMessage("Two street sections begin at the intersection 0 and end at the intersection 1");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithoutIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithoutIntersection.xml");

        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be at least 2 intersections in a cityMap");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadIdIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadIdIntersection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The id of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadXIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadXIntersection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The x value of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadYIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithBadYIntersection.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The y value of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithDuplicateIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithDuplicateIntersection.xml");

        thrown.expect(ParserDuplicateObjectException.class);
        thrown.expectMessage("Two intersections with the id 0 exist");
        parser.getCityMap(cityMapXmlFile);
    }
    
    @Test
    public void parseCityMapWithAttributeIntersectionNotANumberTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMapWithAttributeIntersectionNotANumber.xml");

        thrown.expect(ParserShouldBeIntegerValueException.class);
        thrown.expectMessage("xIntersection");
        parser.getCityMap(cityMapXmlFile);
    }

    // ================================================== Delivery Request ============================================
    @Test
    public void parseDeliveryRequestTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();

        File cityMapXmlFile = getFile("/services/xml/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);

        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest2x2-2.xml");
        DeliveryRequest actualDeliveryRequest = parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);

        Warehouse expectedW = new Warehouse(new Intersection(2, 140, 420));
        List<DeliveryAddress> expectedDA = new ArrayList<DeliveryAddress>();
        expectedDA.add(new DeliveryAddress(new Intersection(0, 134, 193), 100));
        expectedDA.add(new DeliveryAddress(new Intersection(3, 132, 470), 250));
        DeliveryRequest expectedDeliveryRequest = new DeliveryRequest(expectedW, expectedDA, 28_800);

        assertEquals(actualDeliveryRequest, expectedDeliveryRequest);
    }

    // ================================================= Utility methods ==============================================

    private File getFile(String location) throws URISyntaxException {
        URL testMapPath = getClass().getResource(location);
        assertNotNull(testMapPath);

        File file = null;
        file = new File(testMapPath.toURI());
        return file;
    }
}
