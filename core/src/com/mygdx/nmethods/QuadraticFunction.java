package com.mygdx.nmethods;

import java.util.List;

public class QuadraticFunction implements NFunction {

    private final int n;
    public final Matrix a;
    public final Vector b;
    public final double c;

    /**
     * Creates a function instance with specified coefficients.
     * The function is equivalent to {@code ax^2/2 + bx + c}
     * @throws IllegalArgumentException if the matrix isn't quadratic.
     * @param a Matrix
     * @param b Vector
     * @param c Constant
     */
    public QuadraticFunction(final Matrix a, List<Double> b, final double c) {
        this.n = b.size();
        if (a.stream().anyMatch(arr -> arr.size() != n) || a.size() != n) {
            throw new IllegalArgumentException("Invalid arrays sizes " + n);
        }
        this.a = a;
        this.b = new Vector(b);
        this.c = c;
    }

    /**
     * Same as {@link QuadraticFunction#QuadraticFunction(Matrix, List, double)}
     * but the first argument is represented as a List of Lists
     * @param a Matrix
     * @param b Vector
     * @param c Constant
     */
    public QuadraticFunction(final List<List<Double>> a, List<Double> b, final double c) {
        this(new Matrix(a), b, c);
    }

    /**
     * Applies the function to the argument.
     * @param arg the argument
     * @return f(arg) = a(arg)/2 + b(arg) + c
     */
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

    /**
     * Returns the function's gradient vector at a specified point.
     * The gradient is a vector of derivatives.
     * For quadratic functions it's equal to {@code a * point + b}
     * @param point point where the gradient is calculated.
     * @return the gradient.
     */
    public Vector gradient(Vector point) {
        return a.multiply(point).add(b);
    }

    /**
     * Returns the function's dimensionality
     * @return n =)
     */
    @Override
    public int getN() {
        return n;
    }

}
