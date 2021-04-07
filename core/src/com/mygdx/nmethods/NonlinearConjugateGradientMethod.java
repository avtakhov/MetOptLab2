package com.mygdx.nmethods;

import java.util.Collections;

public class NonlinearConjugateGradientMethod extends QuadraticMethod {

    private final int RESTART;

    public NonlinearConjugateGradientMethod(final QuadraticFunction f, final int restart) {
        super(f);
        this.RESTART = restart;
    }

    public NonlinearConjugateGradientMethod(final QuadraticFunction f) {
        super(f);
        this.RESTART = f.n;
    }

    @Override
    public Vector findMin(final double eps) {
        Value<Vector, Double> x = new Value<>(new Vector(Collections.nCopies(f.n, 0.)), f);
        Vector gradient = null;
        double gDist = Double.POSITIVE_INFINITY;
        Vector p = null;
        int counter = 0;

        while (gDist > eps) {
            if (counter == 0) {
                gradient = f.gradient(x.getValue());
                gDist = gradient.length();
                p = gradient.multiply(-1);
            }
            counter = (counter + 1) % RESTART;
            Vector mulResult = f.a.multiply(p);
            double alpha = gDist * gDist / mulResult.scalarProduct(p);
            x.setValue(x.getValue().sum(p.multiply(alpha)));
            gradient = gradient.sum(mulResult.multiply(alpha));
            double newGDist = gradient.length();
            double beta = newGDist * newGDist / (gDist * gDist);
            p = gradient.multiply(-1).sum(p.multiply(beta));
            gDist = newGDist;
        }

        return x.getValue();
    }
}
