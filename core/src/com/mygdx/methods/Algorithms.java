package com.mygdx.methods;

import java.util.Map;
import java.util.function.Function;

public class Algorithms {
    public static void main(String[] args) {
        Function<Double, Double> func = x -> x - Math.log(x);

        Map<String, Method> methods = Map.of(
                "dichotomy", new DichotomyMethod(func, 1e-5),
                "golden section", new GoldenSectionMethod(func),
                "fibonacci", new FibonacciMethod(func),
                "parabola", new ParabolaMethod(func));

        methods.forEach((k, v) -> System.out.println(k + ": " + v.findMin(0.5, 4, 1e-5)));
    }
}
