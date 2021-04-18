package com.mygdx.nmethods;

import com.mygdx.graphics.RenderFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class MethodCounter<F extends NFunction> implements NMethod {

    private final AbstractNMethod<F> nMethod;
    private int countIteration = 0;

    /**
     * Creates an instance with the specified nMethod. The iterations counter
     * is set to 0 by default.
     * @param nMethod nMethod
     */
    protected MethodCounter(AbstractNMethod<F> nMethod) {
        this.nMethod = nMethod;
    }

    /**
     * Searches for the minima while also counting
     * the number of performed iterations.
     * @param eps tolerance
     * @return function's minima
     */
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

    /**
     * Returns the number of iterations performed since the instance's creation.
     * @return the number of iterations.
     */
    public int getCountIteration() {
        return countIteration;
    }
}
