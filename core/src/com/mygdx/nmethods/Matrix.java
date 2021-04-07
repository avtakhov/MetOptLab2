package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix extends AbstractList<List<Double>> {
    private final List<Vector> a;

    public Matrix(final List<List<Double>> a) {
        this.a = a.stream().map(Vector::new).collect(Collectors.toList());
        if (a.stream().anyMatch(list -> list.size() != a.size())) {
            throw new IllegalArgumentException("not quadratic matrix");
        }
    }

    public Vector multiply(final Vector other) {
        Vector result = new Vector();
        for (Vector vector : a) {
            result.add(vector.scalarProduct(other));
        }
        return result;
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
