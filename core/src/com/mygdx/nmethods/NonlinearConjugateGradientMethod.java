package com.mygdx.nmethods;

public class NonlinearConjugateGradientMethod<F extends QuadraticFunction> extends QuadraticMethod<F> {

    private final int RESTART;
    private int counter = 0;
    private Vector gradient;
    private double gradientLength = Double.POSITIVE_INFINITY;
    private Vector p;

    public NonlinearConjugateGradientMethod(final F f, final int restart) {
        super(f);
        this.RESTART = restart;
    }

    public NonlinearConjugateGradientMethod(final F f) {
        this(f, f.getN());
    }

    @Override
    public Value<Vector, Double> nextIteration(final Value<Vector, Double> x, final double eps) {
        if (counter == 0) {
            gradient = getFunction().gradient(x.getValue());
            gradientLength = gradient.length();
            p = gradient.multiply(-1);
        }
        if (gradientLength < eps) {
            gradientLength = Double.POSITIVE_INFINITY;
            counter = 0;
            return null;
        }
        counter = (counter + 1) % RESTART;
        Vector mulResult = getFunction().a.multiply(p);
        double alpha = gradientLength * gradientLength / mulResult.scalarProduct(p);
        Value<Vector, Double> y = new Value<>(x.getValue().add(p.multiply(alpha)), getFunction());
        gradient = gradient.add(mulResult.multiply(alpha));
        double newGDist = gradient.length();
        double beta = newGDist * newGDist / (gradientLength * gradientLength);
        if (counter == 0) {
            beta = 0;
        }
        p = gradient.multiply(-1).add(p.multiply(beta));
        gradientLength = newGDist;
        return y;
    }
}
