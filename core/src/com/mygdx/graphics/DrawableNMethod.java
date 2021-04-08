package com.mygdx.graphics;

import com.mygdx.nmethods.*;

import java.util.Collections;

public class DrawableNMethod implements NMethod {

    private final AbstractNMethod<RenderFunction> nMethod;

    protected DrawableNMethod(AbstractNMethod<RenderFunction> nMethod) {
        this.nMethod = nMethod;
    }
    @Override
    public Vector findMin(double eps) {
        Value<Vector, Double> x = new Value<>(
                new Vector(Collections.nCopies(nMethod.getFunction().getN(), 0.)),
                nMethod.getFunction());
        Value<Vector, Double> y;
        nMethod.getFunction().renderPoints.clear();
        nMethod.getFunction().renderPoints.add(x);
        while ((y = nMethod.nextIteration(x, eps)) != null) {
            x = y;
            nMethod.getFunction().renderPoints.add(x);
        }
        return x.getValue();
    }
}
