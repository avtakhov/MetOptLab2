package com.mygdx.nmethods;

import java.util.Collections;

public abstract class AbstractNMethod<F extends NFunction> implements NMethod {
    private final F function;

    protected AbstractNMethod(final F function) {
        this.function = function;
    }

    public F getFunction() {
        return function;
    }

    public abstract Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps);

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
