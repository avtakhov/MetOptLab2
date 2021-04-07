package com.mygdx.nmethods;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class QuadraticFunction implements Function<Vector, Double> {

    public final int n;
    public final Matrix a;
    public final Vector b;
    public final double c;

    public QuadraticFunction(final List<List<Double>> a, List<Double> b, final double c) {
        this.n = b.size();
        if (a.stream().anyMatch(arr -> arr.size() != n) || a.size() != n) {
            throw new IllegalArgumentException("Invalid arrays sizes " + n);
        }
        this.a = new Matrix(a);
        this.b = new Vector(b);
        this.c = c;
    }

    @Override
    public Double apply(Vector arg) {
        double result = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                result += a.get(i, j) * arg.get(i) * arg.get(j);
            }
        }
        result /= 2;
        for (int i = 0; i < n; ++i) {
            result += b.get(i) * arg.get(i);
        }
        result += c;
        return result;
    }

    public Vector gradient(Vector point) {
        return a.multiply(point).sum(b);
    }

}
