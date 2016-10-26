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
import services.xml.exception.ParserInvalidIdException;
import services.xml.exception.ParserMalformedXmlException;
import services.xml.exception.ParserNodesNumberException;
import services.xml.exception.ParserShouldBeIntegerValueException;
import services.xml.exception.ParserTimeSyntaxException;
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

    // ================================================== City Map ====================================================
    // -------------------------------------------------- Normal test -------------------------------------------------
    @Test
    public void parseCityMapNormalTest() throws URISyntaxException, IOException, ParserException {
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

        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        Parser parser = new Parser();
        CityMap actualCityMap = parser.getCityMap(cityMapXmlFile);

        assertEquals(expectedCityMap, actualCityMap);
    }

    // -------------------------------------------------- Malformed xml -----------------------------------------------
    @Test
    public void parseCityMapMalformedXmlFileTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/malformedXml.xml");

        thrown.expect(ParserMalformedXmlException.class);
        thrown.expectMessage("Les structures de document XML doivent commencer et se terminer dans la même entité.");
        parser.getCityMap(cityMapXmlFile);
    }

    // -------------------------------------------------- Street section ----------------------------------------------
    @Test
    public void parseCityMapWithoutStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/withoutStreetSection.xml");

        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be at least 1 street section in a cityMap");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadIdStartStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/badIdStart.xml");

        thrown.expect(ParserInvalidIdException.class);
        thrown.expectMessage("The start of a street section must exist");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadIdEndStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/badIdEnd.xml");

        thrown.expect(ParserInvalidIdException.class);
        thrown.expectMessage("The end of a street section must exist");
        parser.getCityMap(cityMapXmlFile);
    }
    
    @Test
    public void parseCityMapWithIdentiqueIdStartAndEndStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/identiqueIdStartAndEnd.xml");

        thrown.expect(ParserInvalidIdException.class);
        thrown.expectMessage("A street section can not begin and end at the same intersection");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadLengthStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/badLength.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The length of a street section must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadSpeedStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/badSpeed.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The speed of a street section must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithAttributeStreetSectionNotANumberTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/attributeNotANumber.xml");

        thrown.expect(ParserShouldBeIntegerValueException.class);
        thrown.expectMessage("lengthStreetSection");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithBadDuplicateStreetSectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/streetSection/duplicateStreetSection.xml");

        thrown.expect(ParserDuplicateObjectException.class);
        thrown.expectMessage("Two street sections begin at the intersection 0 and end at the intersection 1");
        parser.getCityMap(cityMapXmlFile);
    }

    // -------------------------------------------------- Intersection ------------------------------------------------
    @Test
    public void parseCityMapWithoutIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/withoutIntersection.xml");

        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be at least 2 intersections in a cityMap");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadIdIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/badId.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The id of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadXIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/badX.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The x value of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapBadYIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/badY.xml");

        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The y value of an intersection must be positive");
        parser.getCityMap(cityMapXmlFile);
    }

    @Test
    public void parseCityMapWithDuplicateIntersectionTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/duplicateIntersection.xml");

        thrown.expect(ParserDuplicateObjectException.class);
        thrown.expectMessage("Two intersections with the id 0 exist");
        parser.getCityMap(cityMapXmlFile);
    }
    
    @Test
    public void parseCityMapWithAttributeIntersectionNotANumberTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/intersection/attributeNotANumber.xml");

        thrown.expect(ParserShouldBeIntegerValueException.class);
        thrown.expectMessage("xIntersection");
        parser.getCityMap(cityMapXmlFile);
    }
    
    // ================================================== Delivery Request ============================================
    // -------------------------------------------------- Normal test -------------------------------------------------
    @Test
    public void parseDeliveryRequestNormalTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();

        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);

        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/deliveryRequest2x2-2.xml");
        DeliveryRequest actualDeliveryRequest = parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);

        Warehouse expectedW = new Warehouse(new Intersection(2, 140, 420));
        List<DeliveryAddress> expectedDA = new ArrayList<DeliveryAddress>();
        expectedDA.add(new DeliveryAddress(new Intersection(0, 134, 193), 100));
        expectedDA.add(new DeliveryAddress(new Intersection(3, 132, 470), 250));
        DeliveryRequest expectedDeliveryRequest = new DeliveryRequest(expectedW, expectedDA, 28_800);

        assertEquals(actualDeliveryRequest, expectedDeliveryRequest);
    }
    
    // -------------------------------------------------- Malformed xml------------------------------------------------
    @Test
    public void parseDeliveryRequestMalformedXmlTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/malformedXml.xml");
        
        thrown.expect(ParserMalformedXmlException.class);
        thrown.expectMessage("Les structures de document XML doivent commencer et se terminer dans la même entité.");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    // -------------------------------------------------- Warehouse ---------------------------------------------------
    @Test
    public void parseDeliveryRequestWithoutWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/withoutWarehouse.xml");
        
        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be exactly 1 warehouse in a delivery request");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestWithTowWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/withTwoWarehouse.xml");
        
        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be exactly 1 warehouse in a delivery request");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestBadAddressWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/badAddress.xml");
        
        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("The address of a warehouse must exist in the city map");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestWithTimeOutsideADayWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/timeOutsideADay.xml");
        
        thrown.expect(ParserTimeSyntaxException.class);
        // TODO verify "start delivery time"
        thrown.expectMessage("The start delivery time must be on the format hh:mm:ss");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestWithBadSyntaxTimeWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/badSyntaxTime.xml");
        
        thrown.expect(ParserTimeSyntaxException.class);
        // TODO verify "start delivery time"
        thrown.expectMessage("The start delivery time must be on the format hh:mm:ss");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestWithNegativeTimeWarehouseTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/negativeTime.xml");
        
        thrown.expect(ParserTimeSyntaxException.class);
        // TODO verify "start delivery time"
        thrown.expectMessage("The start delivery time must be on the format hh:mm:ss");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestWithAttributeWarehouseNotANumberTest() throws URISyntaxException, IOException, ParserException{
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/warehouse/attributeNotANumber.xml");
        
        thrown.expect(ParserShouldBeIntegerValueException.class);
        thrown.expectMessage("addressWarehouse");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    // -------------------------------------------------- Delivery address --------------------------------------------
    // TODO add time constraints in test
    @Test
    public void parseDeliveryRequestWithoutDeliveryAddressTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/deliveryAddress/withoutDeliveryAddress.xml");
        
        thrown.expect(ParserNodesNumberException.class);
        thrown.expectMessage("There must be at least 1 delivery address in a delivery request");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestBadAddressDeliveryAddressTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/deliveryAddress/badAddress.xml");
        
        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("There must be at least 1 delivery address in a delivery request");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
    }
    
    @Test
    public void parseDeliveryRequestDuplicateDeliveryAddressTest() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/xml/cityMap/cityMap2x2.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);
        
        File deliveryRequestXmlFile = getFile("/services/xml/deliveryRequest/deliveryAddress/duplicateDeliveryAddress.xml");
        
        thrown.expect(ParserIntegerValueException.class);
        thrown.expectMessage("There must be at least 1 delivery address in a delivery request");
        parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);
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
