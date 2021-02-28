package com.mygdx.methods;

import java.util.function.Function;

public class ParabolaMethod extends AbstractAlgorithm {
    ParabolaMethod(Function<Double, Double> func) {
        super(func);
    }

    @Override
    public double findMin(double left, double right, double eps) {
        double m = (right + left) / 2;
        double fm = func.apply(m);
        double fl = func.apply(left);
        double fr = func.apply(right);
        while (right - left > eps) {
            double x = (left + m - (((fm - fl) * (right - m)) / (m - left)) / ((fr - fl) / (right - left) - (fm - fl) / (m - left))) / 2;
            double fx = func.apply(x);
            if (fx > fm) {
                if (x > m) {
                    right = x;
                    fr = fx;
                } else {
                    left = x;
                    fl = x;
                }
            } else {
                if (m > x) {
                    right = m;
                    fr = fm;
                } else {
                    left = m;
                    fl = fm;
                }
                m = x;
                fm = fx;
            }
        }
        return m;
    }
}
