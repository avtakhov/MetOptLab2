package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix extends AbstractList<List<Double>> {
    private final List<Vector> a;

    /**
     * Creates a matrix from a List of Lists.
     * @throws IllegalArgumentException if the List of Lists doesn't represent
     * a quadratic matrix
     * @param a List of Lists
     */
    public Matrix(final List<List<Double>> a) {
        this.a = a.stream().map(Vector::new).collect(Collectors.toList());
        if (a.stream().anyMatch(list -> list.size() != a.size())) {
            throw new IllegalArgumentException("not quadratic matrix");
        }
    }

    /**
     * Returns a result of multiplying a matrix by a vector.
     * @param other the vector
     * @return the result of multiplication
     */
    public Vector multiply(final Vector other) {
        Vector result = new Vector();
        for (Vector vector : a) {
            result.add(vector.scalarProduct(other));
        }
        return result;
    }

    /**
     * Returns the value of a matrix in r-th row and c-th column.
     * @param r row
     * @param c ccolumn
     * @return the value of a matrix in r-th row and c-th column.
     */
    public double get(final int r, final int c) {
        return a.get(r).get(c);
    }

    /**
     * Returns the index-th vector of the matrix.
     * @param index the index
     * @return the index-th vector of the matrix.
     */
    @Override
    public Vector get(final int index) {
        return new Vector(a.get(index));
    }

    /**
     * Returns the dimensionality of the matrix.
     * @return size of a matrix.
     */
    @Override
    public int size() {
        return a.size();
    }
}
