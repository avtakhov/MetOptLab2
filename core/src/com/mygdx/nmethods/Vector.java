package com.mygdx.nmethods;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Vector extends AbstractList<Double> {
    private final List<Double> coordinates;

    public Vector(final List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public Vector() {
        this(new ArrayList<>());
    }

    public double scalarProduct(final List<Double> other) {
        double result = 0;
        for (int i = 0; i < other.size(); i++) {
            result += this.coordinates.get(i) * other.get(i);
        }
        return result;
    }

    public Vector multiply(final double scalar) {
        return this.stream()
                .map(x -> x * scalar)
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector sum(List<Double> other) {
        Vector result = new Vector();
        for (int i = 0; i < other.size(); i++) {
            result.add(this.coordinates.get(i) + other.get(i));
        }
        return result;
    }

    @Override
    public Double get(final int index) {
        return coordinates.get(index);
    }

    public double length() {
        return Math.sqrt(coordinates.stream().reduce(0.0, (x, y) -> x + y * y));
    }

    @Override
    public int size() {
        return coordinates.size();
    }

    @Override
    public boolean add(final Double elem) {
        return this.coordinates.add(elem);
    }
}
