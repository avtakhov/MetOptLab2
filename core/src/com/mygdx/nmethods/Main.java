package com.mygdx.nmethods;

import com.mygdx.graphics.parser.ExpressionParser;
import com.mygdx.methods.*;

import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        QuadraticFunction f = new ExpressionParser()
                .parse("64 * x * x + 126 * x * y + 64 * y * y - 10 * x + 30 * y + 13");
        final double eps = 1e-3;
        NMethod method = new GradientMethod<>(f);
        System.out.println("1 " + method.findMin(eps));

        NMethod method2 = new GradientOpt<>(f, BrentCombMethod::new);
        System.out.println("2 " + method2.findMin(eps));

        NMethod method3 = new NonlinearConjugateGradientMethod<>(f);
        System.out.println("3 " + method3.findMin(eps));

        Map<String, Function<Function<Double, Double>, Method>> oneDimensionalMethods = new LinkedHashMap<>();
        oneDimensionalMethods.put("dichotomy", (func) -> new DichotomyMethod(func, eps));
        oneDimensionalMethods.put("golden section", GoldenSectionMethod::new);
        //oneDimensionalMethods.put("fibonacci", FibonacciMethod::new);
        //oneDimensionalMethods.put("parabola", ParabolaMethod::new);
        oneDimensionalMethods.put("brent", BrentCombMethod::new);
        for (String nameKey : oneDimensionalMethods.keySet()) {
            GradientCounter counter = new GradientCounter(f, oneDimensionalMethods.get(nameKey));
            counter.findMin(eps);
            System.out.println("Count of iterations with " + nameKey + " : " + counter.getIterationCount());
        }
        System.out.println(f.apply(method3.findMin(eps)));
    }
}
