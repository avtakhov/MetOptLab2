package com.mygdx.nmethods;

public abstract class QuadraticMethod<F extends QuadraticFunction> extends AbstractNMethod<F> {

    /**
     * я надеюсь сюда никто не посмотрит
     * @param func fun
     */
    public QuadraticMethod(final F func) {
        super(func);
    }

}
