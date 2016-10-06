package exec;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void doubleThree() throws Exception {
        assertEquals(4, Main.half(8));
    }

    @Test(expected=com.google.java.contract.PreconditionError.class)
    public void breakContract() throws Exception {
        Main.half(11);
    }
}
