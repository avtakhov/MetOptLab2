package com.mygdx.methods;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

public class BrentCombMethod extends AbstractAlgorithm {

    private final static double K = (3 - Math.sqrt(5)) / 2;

    public BrentCombMethod(Function<Double, Double> func) {
        super(func);
    }

    private static boolean allDistinct(Object... os) {
        return Arrays.stream(os).distinct().count() == os.length;
    }

    /**
     * Реализация нахождения минимума для комбинированного метода Брента
     */
    public double findMin(double left, double right, double eps) {
        double d = right - left, pd = d;
        double x = left + K * d, px = x, ppx = x;
        double fx = drawFunc.apply(x), fpx = fx, fppx = fx;
        double u = 0, fu;
        while ((right - left) / 2 > eps) {
            double ppd = pd;
            pd = d;
            double tol = eps * Math.abs(x) + eps / 10;
            if (Math.abs(x - (left + right) / 2)   +   (right - left) / 2   <=   2 * tol) {
                break;
            }

            boolean acceptedParabola = false;
            if (allDistinct(x, px, ppx) && allDistinct(fx, fpx, fppx)) {
                assert px < x && x < ppx;
                double[][] a = {{x,fx}, {px, fpx}, {ppx, fppx}};
                Arrays.sort(a, new Comparator<double[]>() {
                    public int compare(double[] x, double[] y) {
                        return Double.compare(x[0], y[0]);
                    }
                });

                u = a[1][0] - ( Math.pow((a[1][0] - a[0][0]), 2) * (a[1][1] - a[2][1])  -  Math.pow((a[1][0] - a[2][0]), 2) * (a[1][1] - a[0][1])  ) / 2 / (        (a[1][0] - a[0][0]) * (a[1][1] - a[2][1])  -  (a[1][0] - a[2][0]) * (a[1][1] - a[0][1])    );

                if (left <= u && u <= right && Math.abs(u - x) < ppd / 2) {
                    acceptedParabola = true;
                    if (u - left < 2 * tol || right - u < 2 * tol) {
                        u = x - Math.signum(x - (left + right) / 2) * tol;
                    }
                }
            }
            if (!acceptedParabola) {
                if (x < (left + right) / 2) {
                    u = x + K * (right - x);
                    pd = right - x;
                } else {
                    u = x - K * (right - x);
                    pd = x - left;
                }
            }
            if (Math.abs(u - x) < tol) {
                u = x + Math.signum(u - x) * tol;
            }
            d = Math.abs(u-x);
            fu = drawFunc.apply(u);

            if (fu <= fx) {
                if (u >= x) {
                    left = x;
                } else {
                    right = x;
                }
                ppx = px;
                px = x;
                x = u;
                fppx = fpx;
                fpx = fx;
                fx = fu;
            } else {
                if (u >= x) {
                    right = u;
                } else {
                    left = u;
                }
                if (fu <= fpx || px == x) {
                    ppx = px;
                    px = u;
                    fppx = fpx;
                    fpx = fu;
                } else if (fu <= fppx || ppx == x || ppx == px) {
                    ppx = u;
                    fppx = fu;
                }
            }

        }

        return (left + right) / 2;
    }

}