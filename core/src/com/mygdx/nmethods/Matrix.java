package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.List;

public class Matrix extends AbstractList<List<Double>> {
    private final List<List<Double>> a;

    public Matrix(final List<List<Double>> a) {
        this.a = a;
        if (a.stream().anyMatch(list -> list.size() != a.size())) {
            throw new IllegalArgumentException("not quadratic matrix");
        }
    }

    public double get(final int r, final int c) {
        return a.get(r).get(c);
    }

    @Override
    public Vector get(final int index) {
        return new Vector(a.get(index));
    }

    @Override
    public int size() {
        return a.size();
    }
}
