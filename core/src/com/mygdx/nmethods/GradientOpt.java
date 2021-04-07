package com.mygdx.nmethods;

import com.mygdx.methods.Method;

import java.util.Collections;
import java.util.function.Function;

public class GradientOpt extends QuadraticMethod {

    private final Function<Function<Double, Double>, Method> methodCreator;
    public GradientOpt(final QuadraticFunction f, final Function<Function<Double, Double>, Method> methodCreator) {
        super(f);
        this.methodCreator = methodCreator;
    }

    @Override
    public Vector findMin(final double eps) {
        Value<Vector, Double> x = new Value<>(new Vector(Collections.nCopies(f.n, 0.)), f);
        while (true) {
            Vector gradX;
            if ((gradX = f.gradient(x.getValue())).length() < eps) {
                break;
            }
            Value<Vector, Double> finalX = x;
            Function<Double, Vector> func = t -> finalX.getValue().sum(gradX.multiply(-t));
            double alpha = methodCreator.apply(func.andThen(f)).findMin(0., 1., eps);
            Value<Vector, Double> y = new Value<>(func.apply(alpha), f);
            if (y.getFValue() < x.getFValue()) {
                x = y;
            }
        }
        return x.getValue();
    }
}
