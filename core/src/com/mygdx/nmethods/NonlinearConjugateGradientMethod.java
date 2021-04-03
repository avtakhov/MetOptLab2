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
                gDist = gradient.dist();
                p = MatrixAlgebra.multiply(gradient, -1);
            }
            counter = (counter + 1) % RESTART;
            Vector mulResult = MatrixAlgebra.multiply(f.a, p);
            double alpha = gDist * gDist / MatrixAlgebra.scalar(mulResult, p);
            x.setValue(MatrixAlgebra.sum(x.getValue(), MatrixAlgebra.multiply(p, alpha)));
            gradient = MatrixAlgebra.sum(gradient, MatrixAlgebra.multiply(mulResult, alpha)).force();
            double newGDist = gradient.dist();
            double beta = newGDist * newGDist / (gDist * gDist);
            p = MatrixAlgebra.sum(
                    MatrixAlgebra.multiply(gradient, -1),
                    MatrixAlgebra.multiply(p, beta)).force();
            gDist = newGDist;
        }

        return x.getValue();
    }
}
