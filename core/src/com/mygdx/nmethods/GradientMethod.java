package com.mygdx.nmethods;

public class GradientMethod<F extends NFunction> extends AbstractNMethod<F> {

    /**
     * Creates an instance from a specified NFunction.
     * @param f a quadratic function
     */
    public GradientMethod(final F f) {
        super(f);
    }

    /**
     * Performs a single iteration of minimization according to the Gradient descend method.
     * @param x The current minima value
     * @param eps tolerance
     * @return The next minima value
     */
    @Override
    public Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps) {
        Vector gradient = getFunction().gradient(x.getValue());
        double gradientLength = gradient.length();
        if (gradientLength < eps) {
            return null;
        }

        double alpha = 1.;
        while (true) {
            Value<Vector, Double> y = new Value<>(
                    x.getValue().add(gradient.multiply(-alpha / gradientLength)),
                    getFunction());
            if (y.getFValue() < x.getFValue()) {
                return y;
            } else {
                alpha /= 2;
                if (alpha == 0) {
                    // can't find :(
                    return null;
                }
            }
        }
    }
}
