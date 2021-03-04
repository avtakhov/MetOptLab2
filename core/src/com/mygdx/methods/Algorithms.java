package com.mygdx.methods;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Algorithms {

    public static List<AbstractDrawableMethod> methodList(Function<Double, Double> func) {

        return Arrays.asList(new DichotomyMethod(func, 1e-3),
                new GoldenSectionMethod(func),
                new FibonacciMethod(func),
                new ParabolaMethod(func),
                new BrentCombMethod(func));
    }

    public static void main(String[] args) {

    }
}
