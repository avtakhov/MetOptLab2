package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagonalFunction extends QuadraticFunction {

    /**
     * Creates an instance of a DiagonalFunction with values from a specified List.
     * Diagonal function is represented as Ax^2/2 where A is a diagonal matrix.
     * A[i][i] = 2b[i]
     * @param b list of values on the diagonal
     */
    public DiagonalFunction(List<Double> b) {
        super(new DiagonalMatrix(b), Collections.nCopies(b.size(), 0.), 0);
    }

    /**
     * Returns the function's gradient vector.
     * The gradient is a vector of derivatives. For a diagonal function it's Ax.
     * @param point point where the gradient is calculated.
     * @return Ax
     */
    @Override
    public Vector gradient(Vector point){
        Vector result = new Vector();
        for (int i = 0; i < point.size(); i++) {
            result.add(point.get(i) * a.get(i, i));
        }
        return result;
    }

    /**
     * Returns the result of applying function to the argument.
     * @param arg the argument
     * @return f(arg)
     */
    @Override
    public Double apply(Vector arg) {
        double result = 0.;
        for (int i = 0; i < arg.size(); i++) {
            result += arg.get(i) * arg.get(i) * a.get(i, i) / 2;
        }
        return result;
    }
}
