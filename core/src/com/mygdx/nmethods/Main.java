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
        List<Double> squareCoeffs = new ArrayList<>();
        int n = 100;
        int k = 500;
        double val = 1;
        for (double i = 0, step = (double) k / n; i < n; i++) {
            squareCoeffs.add(val);
            val += step;
        }
        List<Double> linearCoeffs = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            linearCoeffs.add((double) Math.abs(rand.nextInt()));
        }
        System.out.println(squareCoeffs.size());
        DiagonalFunction megaNDim = new DiagonalFunction(squareCoeffs, linearCoeffs, 228);
        NFunction threeDimFunc = new NFunction() {
            @Override
            public Double apply(Vector arg) {
                double x = arg.get(0);
                double y = arg.get(1);
                double z = arg.get(2);
                return x * x + x * y + x * z + z * z + z * y + 15 * y * y + 25 * x + 13 * y + 11 * z;
            }

            @Override
            public Vector gradient(Vector point) {
                double x = point.get(0);
                double y = point.get(1);
                double z = point.get(2);
                return new Vector(Arrays.asList(2 * x + y + z + 25, 30 * y + x + z + 13, 2 * z + y + x + 11));
            }

            @Override
            public int getN() {
                return 3;
            }
        };
        Map<String, Function<Function<Double, Double>, Method>> oneDimensionalMethods = new LinkedHashMap<>();
        oneDimensionalMethods.put("dichotomy", (func) -> new DichotomyMethod(func, eps));
        oneDimensionalMethods.put("golden section", GoldenSectionMethod::new);
        oneDimensionalMethods.put("brent", BrentCombMethod::new);
        MethodCounter<QuadraticFunction> defaultGradientCounter = new MethodCounter<>(new GradientMethod<>(f));
        defaultGradientCounter.findMin(eps);
        System.out.println("eps: " + eps);
        System.out.println("Count of iterations on default: " + defaultGradientCounter.getCountIteration());
        for (String nameKey : oneDimensionalMethods.keySet()) {
            MethodCounter<QuadraticFunction> counter = new MethodCounter<>(new GradientOpt<>(f, oneDimensionalMethods.get(nameKey)));
            counter.findMin(eps);
            System.out.println("Count of iterations with " + nameKey + " : " + counter.getCountIteration());
        }
        System.out.println("Test on n = " + n + "   k = " + k);
        MethodCounter<DiagonalFunction> methodCounter = new MethodCounter<>(new NonlinearConjugateGradientMethod<>(megaNDim));
        methodCounter.findMin(eps);
        System.out.println("Iterations " + methodCounter.getCountIteration());
    }
}
