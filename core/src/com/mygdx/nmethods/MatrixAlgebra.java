package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.Collections;
import java.util.function.BinaryOperator;

public class MatrixAlgebra {

    private static Vector dotOperation(final Vector a, final Vector b, final BinaryOperator<Double> op) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("different sizes " + a.size() + " " + b.size());
        }
        return new Vector(new AbstractList<Double>() {
            @Override
            public Double get(int index) {
                return op.apply(a.get(index), b.get(index));
            }

            @Override
            public int size() {
                return a.size();
            }
        });
    }

    public static double scalar(final Vector a, final Vector b) {
        return dotOperation(a, b, (x, y) -> x * y).stream().reduce(Double::sum).orElse(0.);
    }

    public static Vector multiply(final Vector a, final double x) {
        return dotOperation(a, new Vector(Collections.nCopies(a.size(), x)), (p, q) -> p * q);
    }

    public static Vector multiply(final Matrix a, final Vector b) {
        return new Vector(new AbstractList<Double>() {
            @Override
            public Double get(int index) {
                return scalar(a.get(index), b);
            }

            @Override
            public int size() {
                return a.size();
            }
        });
    }

    public static Vector sum(final Vector a, final Vector b) {
        return dotOperation(a, b, Double::sum);
    }

}
