package com.mygdx.nmethods;

public abstract class QuadraticMethod<F extends QuadraticFunction> extends AbstractNMethod<F> {

    protected QuadraticMethod(final F func) {
        super(func);
    }

}
