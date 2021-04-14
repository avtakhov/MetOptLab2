package com.mygdx.nmethods;

import java.util.Collections;

public abstract class AbstractNMethod<F extends NFunction> implements NMethod {
    private final F function;

    /**
     * Associates function with the instance.
     * @param function should be an instance of NFunction
     */
    protected AbstractNMethod(final F function) {
        this.function = function;
    }

    /**
     * returns the function associated with the instance
     * @return the function
     */
    public F getFunction() {
        return function;
    }


    /**
     * Performs a single iteration of the minima search. Any descendant has to
     * implement this method according it's minimization algorithm.
     * @param x The current minima value
     * @param eps tolerance
     * @return The next step minima value
     */
    public abstract Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps);


    /**
     * Searches for an associated function's minima. Assuming the function
     * is quadratic performs multiple iterations of minimization according
     * to {@link AbstractNMethod#nextIteration(Value, double)} implementation of
     * the instance until it returns null. 
     * {@link AbstractNMethod#nextIteration(Value, double)} should return null either when the value  -close to
     * minima is found or if the number of iteration has exceeded the limit.
     * @param eps tolerance
     * @return function's minima if the search was successful and any value in
     * the other case
     */
    @Override
    public Vector findMin(double eps) {
        Value<Vector, Double> x = new Value<>(
                new Vector(Collections.nCopies(function.getN(), 0.)),
                function);
        Value<Vector, Double> y;

        while ((y = nextIteration(x, eps)) != null) {
            x = y;
        }
        return x.getValue();
    }
}
