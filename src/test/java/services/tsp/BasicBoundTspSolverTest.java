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
        Planning planning = getPlanning("timeConstraints/uniqueSolution");

        int[] idWayPoints = { 0, 2, 4, 1, 3 };
        int[] waitingTime = { 0, 0, 0, 0, 0 };
        checkPlanning(idWayPoints, waitingTime, 840, planning);
    }

    @Test
    public void testWaitingTime() throws URISyntaxException, IOException, ParserException {
        Planning planning = getPlanning("timeConstraints/waitBeforeAWayPoint");

        int[] idWayPoints = { 0, 2, 4, 1, 3 };
        int[] waitingTime = { 0, 0, 60, 0, 0 };
        checkPlanning(idWayPoints, waitingTime, 900, planning);
    }

    @Test
    public void testNoSolution() throws URISyntaxException, IOException, ParserException {
        Planning planning = getPlanning("timeConstraints/noSolution");

        int[] idWayPoints = { 0, 4, 3, 2, 1 }; // As the solver is unable to create a planning respecting all time constraints, it creates
                                               // the smaller planning possible
        int[] waitingTime = { 0, 0, 0, 0, 0 };

        // The first term of the full time is the moving time, the second one is the delivery time, then the penalty
        checkPlanning(idWayPoints, waitingTime, 5 * 100 + 4 * 60 + 4 * 86400, planning);
    }

    // ================================================= Utility methods ==============================================

    private File getFile(String location) throws URISyntaxException {
        URL testMapPath = getClass().getResource(location);
        assertNotNull(testMapPath);

        File file = null;
        file = new File(testMapPath.toURI());
        return file;
    }

    private Planning getPlanning(String subLocation) throws URISyntaxException, IOException, ParserException {
        String location = "/services/tsp/" + subLocation;
        Parser parser = new Parser();
        File cityMapXmlFile = getFile(location + "/cityMap.xml");
        CityMap cityMap = parser.getCityMap(cityMapXmlFile);

        File deliveryRequestXmlFile = getFile(location + "/deliveryRequest.xml");
        DeliveryRequest deliveryRequest = parser.getDeliveryRequest(deliveryRequestXmlFile, cityMap);

        DeliveryGraph deliveryGraph = cityMap.computeDeliveryGraph(deliveryRequest);

        AbstractTspSolver solver = new BasicBoundTspSolver();
        return solver.solve(deliveryGraph);
    }

    private void checkPlanning(int[] idWayPoints, int[] waitingTime, int fullTime, Planning planning) {
        List<Route> routes = planning.getRoutes();
        for (int i = 0; i < idWayPoints.length; i++) {
            Route route = routes.get(i);
            assertTrue(route.getStartWaypoint().getId() == idWayPoints[i]);
            assertTrue(route.getEndWaypoint().getId() == idWayPoints[(i + 1) % idWayPoints.length]);
            assertTrue(planning.getWaitingTimeAtWayPoint(route.getStartWaypoint()) == waitingTime[i]);
        }
        assertTrue(planning.getFullTime() == fullTime);
    }
}
