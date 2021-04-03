package com.mygdx.nmethods;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Vector extends AbstractList<Double> {
    private final List<Double> coordinates;

    public Vector(final List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Double get(final int index) {
        return coordinates.get(index);
    }

    public double dist() {
        return Math.sqrt(coordinates.stream().reduce(0.0, (x, y) -> x + y * y));
    }

    @Override
    public int size() {
        return coordinates.size();
    }

    Vector force() {
        return new Vector(new ArrayList<>(coordinates));
    }
}
