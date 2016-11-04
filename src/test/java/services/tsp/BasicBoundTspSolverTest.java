package services.tsp;

import models.*;
import org.junit.Test;
import services.xml.Parser;
import services.xml.exception.ParserException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BasicBoundTspSolverTest {

    @Test
    public void testTimeConstraints() throws URISyntaxException, IOException, ParserException {
        Parser parser = new Parser();
        File cityMapXmlFile = getFile("/services/tsp/timeConstraints/uniqueSolution/cityMap.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);

        File deliveryRequestXmlFile = getFile("/services/tsp/timeConstraints/uniqueSolution/deliveryRequest.xml");
        DeliveryRequest deliveryRequest = parser.getDeliveryRequest(deliveryRequestXmlFile,cityMap);

        AbstractTspSolver solver = new BasicBoundTspSolver();
        DeliveryGraph deliveryGraph = cityMap.computeDeliveryGraph(deliveryRequest);

        Planning planning = solver.solve(deliveryGraph);

        List<Route> routes = planning.getRoutes();

        for(Route route : routes){
            System.out.println("("+route.getStartWaypoint().getId()+"->"+route.getEndWaypoint().getId()+") duration: "+route.getDuration());
        }
        System.out.println("Total duration : "+planning.getFullTime());

        //check the planning
        Route route = routes.get(0);
        assertTrue( route.getStartWaypoint().getId() == 0 );
        assertTrue( route.getEndWaypoint().getId() == 2 );
        route = routes.get(1);
        assertTrue( route.getStartWaypoint().getId() == 2 );
        assertTrue( route.getEndWaypoint().getId() == 4);
        route = routes.get(2);
        assertTrue( route.getStartWaypoint().getId() == 4);
        assertTrue( route.getEndWaypoint().getId() == 1);
        route = routes.get(3);
        assertTrue( route.getStartWaypoint().getId() == 1 );
        assertTrue( route.getEndWaypoint().getId() == 3 );
        route = routes.get(4);
        assertTrue( route.getStartWaypoint().getId() == 3 );
        assertTrue( route.getEndWaypoint().getId() == 0 );
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
