package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagonalFunction extends QuadraticFunction {

    /**
     * Creates an instance of a DiagonalFunction with values from a specified List.
     * Diagonal function is represented as {@code Ax^2/2 + Bx + C} where {@code A} is a diagonal matrix.
     * {@code A[i][i] = 2 * a[i]}
     * @param a list of values on the diagonal
     * @param b B coefficients
     * @param c C constant
     */
    public DiagonalFunction(List<Double> a, List<Double> b, double c) {
        super(new DiagonalMatrix(a), b, c);
    }

    /**
     * Returns the function's gradient vector.
     * The gradient is a vector of derivatives. For a diagonal function it's Ax.
     * @param point point where the gradient is calculated.
     * @return Ax
     */
    @Override
    public Vector gradient(Vector point) {
        Vector result = new Vector();
        for (int i = 0; i < point.size(); i++) {
            result.add(point.get(i) * a.get(i, i) + b.get(i));
        }
        return result;
    }

    /**
     * Returns the result of applying function to the argument.
     * @param arg the argument
     * @return {@code f(arg)}
     */
    @Override
    public Double apply(Vector arg) {
        double result = 0.;
        for (int i = 0; i < arg.size(); i++) {
            result += arg.get(i) * arg.get(i) * a.get(i, i) / 2 + arg.get(i) * b.get(i);
        }
        result += c;
        return result;
    }
}
