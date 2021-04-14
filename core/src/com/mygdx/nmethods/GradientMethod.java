package com.mygdx.nmethods;

public class GradientMethod<F extends NFunction> extends AbstractNMethod<F> {

    public GradientMethod(final F f) {
        super(f);
    }

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
                    throw new ArithmeticException("alpha equals 0");
                }
            }
        }
    }
}
