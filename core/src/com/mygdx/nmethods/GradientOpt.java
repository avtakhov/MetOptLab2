package com.mygdx.nmethods;

import com.mygdx.methods.Method;

import java.util.function.Function;

public class GradientOpt<F extends NFunction> extends AbstractNMethod<F> {

    private final Function<Function<Double, Double>, Method> methodCreator;
    public GradientOpt(final F f, final Function<Function<Double, Double>, Method> methodCreator) {
        super(f);
        this.methodCreator = methodCreator;
    }

    @Override
    public Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps) {
        final Vector gradient;
        if ((gradient = getFunction().gradient(x.getValue())).length() < eps) {
            return null;
        }

        Function<Double, Vector> func = t -> x.getValue().add(gradient.multiply(-t));
        double alpha = methodCreator.apply(func.andThen(getFunction())).findMin(0., 1., eps);
        return new Value<>(func.apply(alpha), getFunction());
    }
}
