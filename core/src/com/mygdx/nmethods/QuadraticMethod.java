package com.mygdx.nmethods;

public abstract class QuadraticMethod<F extends QuadraticFunction> extends AbstractNMethod<F> {

    /**
     * Calls {@link AbstractNMethod}'s constructor with {@code func
     * @param func a quadratic function
     */
    public QuadraticMethod(final F func) {
        super(func);
    }

}
