package exec;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public class Main {
    public static void main(String[] args) {
        // 4 is even, it is a valid argument
        System.out.println("Half of 4:");
        System.out.println(half(4));

        // 11 is odd, it breaks the pre-condition "n is even"
        System.out.println("Half of 11:");
        System.out.println(half(11)); // This should throw an Exception
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
