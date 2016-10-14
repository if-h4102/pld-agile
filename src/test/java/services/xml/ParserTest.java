package services.xml;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class ParserTest {
    @Test()
    public void testParser() throws Exception {
        URL testMapPath = getClass().getResource("/services/xml/test-map.xml");
        Assert.assertNotNull(testMapPath);
        // TODO: open, parse and compare with the expected result
    }
}
