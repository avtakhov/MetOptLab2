package com.mygdx.nmethods;

import com.mygdx.methods.Method;

import java.util.Arrays;
import java.util.function.Function;

public class GradientOpt<F extends NFunction> extends AbstractNMethod<F> {

    private final Function<Function<Double, Double>, Method> methodCreator;

    /**
     * Creates an instance from a specified function and a methodCreator
     * @param f a quadratic function
     * @param methodCreator a function that converts a function to Method
     */
    // ето конечно крайне сильный док но я хз че тут написать
    public GradientOpt(final F f, final Function<Function<Double, Double>, Method> methodCreator) {
        super(f);
        this.methodCreator = methodCreator;
    }

    /**
     * Performs a single iteration of minimization according to Fast Gradient descend method.
     * @param x The current minima value
     * @param eps tolerance
     * @return The next minima value.
     */
    @Override
    public Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps) {
        final Vector gradient;
        if ((gradient = getFunction().gradient(x.getValue())).length() < eps) {
            return null;
        }
        Function<Double, Vector> func = t -> x.getValue().add(gradient.multiply(-t));
        double alpha = methodCreator.apply(getFunction().compose(func)).findMin(0., (double) 2 / 50, eps);
        return new Value<>(func.apply(alpha), getFunction());
    }
}
