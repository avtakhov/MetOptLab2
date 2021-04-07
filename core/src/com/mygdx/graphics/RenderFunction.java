package com.mygdx.graphics;

import com.mygdx.nmethods.QuadraticFunction;
import com.mygdx.nmethods.Value;
import com.mygdx.nmethods.Vector;

import java.util.ArrayList;
import java.util.List;

public class RenderFunction extends QuadraticFunction {
    public final List<Value<Vector, Double>> renderPoints;

    public RenderFunction(final List<List<Double>> a, final List<Double> b, final double c) {
        super(a, b, c);
        renderPoints = new ArrayList<>();
    }

    @Override
    public Double apply(final Vector arg) {
        Value<Vector, Double> value = new Value<>(arg, super::apply);
        renderPoints.add(value);
        return value.getFValue();
    }

}
