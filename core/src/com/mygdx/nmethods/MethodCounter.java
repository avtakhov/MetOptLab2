package com.mygdx.nmethods;

import com.mygdx.graphics.RenderFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class MethodCounter<F extends NFunction> implements NMethod {

    private final AbstractNMethod<F> nMethod;
    private int countIteration = 0;

    protected MethodCounter(AbstractNMethod<F> nMethod) {
        this.nMethod = nMethod;
    }
    @Override
    public Vector findMin(double eps) {
        Value<Vector, Double> x = new Value<>(
                new Vector(Collections.nCopies(nMethod.getFunction().getN(), 0.)),
                nMethod.getFunction());
        Value<Vector, Double> y;
        while ((y = nMethod.nextIteration(x, eps)) != null) {
            x = y;
            countIteration++;
        }
        return x.getValue();
    }

    public int getCountIteration() {
        return countIteration;
    }
}
