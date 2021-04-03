package com.mygdx.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParabolaMethod extends AbstractDrawableMethod {
    
    public ParabolaMethod(Function<Double, Double> func) {
        super(func);
    }
    
    /**
     * Выбор средней точки x2, удовлетворяющей условиям
     * x1 < x2 < x3 && f(x1) >= f(x2) <= f(x3)
     */
    private double getValidMiddlePoint(double left, double right) {
        double m = (right + left) / 2;
        double fm = func.apply(m);
        double fl = func.apply(left);
        double fr = func.apply(right);
        while (!(fl >= fm && fm <= fr)) {
            if (fl < fm) {
                right = m;
            }
            if (fm > fr) {
                left = m;
            }
            m = (right + left) / 2;
            fm = func.apply(m);
            fl = func.apply(left);
            fr = func.apply(right);
        }
        return m;
    }

    /**
     * Реализация нахождения минимума для метода парабол
     */
    @Override
    public double findMin(double left, double right, double eps) {
        double m = getValidMiddlePoint(left, right);
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
