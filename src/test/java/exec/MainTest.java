package exec;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void doubleThree() throws Exception {
        assertEquals(3, Main.sqrt(9), 1e-2);
    }

    @Test
    public void breakContract() throws Exception {
        assertEquals(3, Main.sqrt(-9), 1e-2);
    }
}
