package exec;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import gui.Application;

public class Main {
    public static void main(String[] args) {
        Application.open();
    }

    /**
     * Returns the half of the even integer n.
     *
     * @param n The input integer. It must be even.
     * @return The integer k such that `n = 2k`
     */
    @Requires("n % 2 == 0") // Check the pre-condition "n is even"
    @Ensures("2 * result == n") // Check the post-condition "n = 2k"
    public static int half (int n) {
        return n / 2;
    }
}
