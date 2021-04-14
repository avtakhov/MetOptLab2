package com.mygdx.nmethods;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Vector extends AbstractList<Double> {
    private final List<Double> coordinates;

    /**
     * Creates a vector from a list of coordinates
     * @param coordinates the coordinates
     */
    public Vector(final List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Creates a 0-dimensional vector
     */
    public Vector() {
        this(new ArrayList<>());
    }

    /**
     * Calculates a scalar product of this vector and the other one.
     * @param other other vector
     * @return scalar product
     */
    public double scalarProduct(final List<Double> other) {
        double result = 0;
        for (int i = 0; i < other.size(); i++) {
            result += this.coordinates.get(i) * other.get(i);
        }
        return result;
    }

    /**
     * Returns a vector multiplied by a scalar
     * @param scalar the scalar
     * @return a new Vector with each coordinate multiplied by the scalar.
     */
    public Vector multiply(final double scalar) {
        return this.stream()
                .map(x -> x * scalar)
                .collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Adds a vector to the current one
     * @param other the other vector
     * @return sum of the vectors
     */
    public Vector add(List<Double> other) {
        Vector result = new Vector();
        for (int i = 0; i < other.size(); i++) {
            result.add(this.coordinates.get(i) + other.get(i));
        }
        return result;
    }

    /**
     * Returns the i-th coordinate of a vector
     * @param index coordinate's index
     * @return index-th coordinate
     */
    @Override
    public Double get(final int index) {
        return coordinates.get(index);
    }

    /**
     * Returns vector's length
     * @return vector's length
     */
    public double length() {
        return Math.sqrt(coordinates.stream().reduce(0.0, (x, y) -> x + y * y));
    }

    /**
     * Returns vector's size
     * @return vector's size
     */
    @Override
    public int size() {
        return coordinates.size();
    }

    /**
     * Appends a new coordinate to this vector
     * @param elem the new coordinate
     * @return true
     */
    @Override
    public boolean add(final Double elem) {
        return this.coordinates.add(elem);
    }
}
