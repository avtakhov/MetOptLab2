package com.mygdx.methods;

import java.util.function.Function;

public class ParabolaMethod extends AbstractAlgorithm {

    Function<Double, Double> baseFunc;

    public ParabolaMethod(Function<Double, Double> drawFunc, Function<Double, Double> baseFunc) {
        super(drawFunc);
        this.baseFunc = baseFunc;
    }

    /**
     * Выбор средней точки x2, удовлетворяющей условиям
     * x1 < x2 < x3 && f(x1) >= f(x2) <= f(x3)
     */
    private double getValidMiddlePoint(double left, double right) {
        assert baseFunc != null;
        double m = (right + left) / 2;
        double fm = baseFunc.apply(m);
        double fl = baseFunc.apply(left);
        double fr = baseFunc.apply(right);
        while (!(fl >= fm && fm <= fr)) {
            if (fl < fm) {
                right = m;
            }
            if (fm > fr) {
                left = m;
            }
            m = (right + left) / 2;
            fl = baseFunc.apply(left);
            fr = baseFunc.apply(right);
            fm = baseFunc.apply(m);
        }
        return m;
    }

    /**
     * Реализация нахождения минимума для метода парабол
     */
    @Override
    public double findMin(double left, double right, double eps) {
        double m = getValidMiddlePoint(left, right);
        double fm = drawFunc.apply(m);
        double fl = drawFunc.apply(left);
        double fr = drawFunc.apply(right);
        while (right - left > eps) {
            double x = (left + m - (((fm - fl) * (right - m)) / (m - left)) / ((fr - fl) / (right - left) - (fm - fl) / (m - left))) / 2;
            double fx = drawFunc.apply(x);
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
