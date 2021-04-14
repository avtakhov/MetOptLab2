package com.mygdx.nmethods;

import com.mygdx.nmethods.Vector;
import java.util.function.Function;

public interface NFunction extends Function<Vector, Double> {
    /**
     * Returns the result of applying the function to the argument.
     * @param arg the argument
     * @return f(arg)
     */
    @Override
    Double apply(Vector arg);

    /**
     * Returns the gradient of the function calculated at a specified point.
     * The gradient is a vector of derivatives.
     * @param point the point where the gradient is calculated
     * @return gradient at point.
     */
    Vector gradient(Vector point);

    /**
     * Returns function's dimensionality.
     * @return n
     */
    int getN();
}
