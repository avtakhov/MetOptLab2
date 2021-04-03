package com.mygdx.nmethods;

public abstract class QuadraticMethod implements NMethod {
    protected final QuadraticFunction f;

    protected QuadraticMethod(QuadraticFunction f) {
        this.f = f;
    }
}
