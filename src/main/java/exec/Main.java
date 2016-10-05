package exec;

import com.google.java.contract.Requires;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        double result = Main.sqrt(9);
        System.out.println(result);
    }

    @Requires("x >= 0")
    public static double sqrt(double x) {
        return Math.sqrt(x);
    }
}
