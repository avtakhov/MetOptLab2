import java.util.*;
import java.util.function.Function;
import com.mygdx.methods.*;
public class Test {
    static List<Method> createMethods(Function<Double, Double> f) {
        return Arrays.asList(
                new DichotomyMethod(f, 1e-3),
                new GoldenSectionMethod(f),
                new FibonacciMethod(f),
                new ParabolaMethod(f),
                new BrentCombMethod(f));
    }
    static final Function<Double, Double> mainFunc = (x) -> x - Math.log(x);
    static final double left = 0.5;
    static final double right = 4.0;
    static final List<Function<Double, Double>> polymodalFunctions = Arrays.asList(
            (x) -> Math.sin(x) * x,
            (x) -> x*x*x*x - x*x*x - 15*x*x + x + 4
    );
    public static void main(String[] args) {
        List<Method> methods = createMethods(mainFunc);
        for (Method m : methods) {
            System.out.println("_____");
            for (int i = 1; i <= 10; i++) {
                m.setLog(false);
                m.resetFunCalls();
                m.findMin(left, right, Math.pow(10, -i));
                System.out.println(m.getFunCalls());
            }
        }
        double l = -5, r = 4, eps = 1e-5;
        for (Function<Double, Double> f : polymodalFunctions) {
            System.out.println("________");
            List<Method> ms = createMethods(f);
            for (Method m : ms) {
                m.setLog(false);
                System.out.println(m.findMin(l, r, eps));
            }
        }
    }
}
