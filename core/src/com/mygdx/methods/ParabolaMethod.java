package com.mygdx.methods;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParabolaMethod extends AbstractDrawableMethod {

    private final List<Function<Double, Double>> renderFunctions = new ArrayList<>();
    private final Function<Double, Double> realFunction;

    public ParabolaMethod(Function<Double, Double> func) {
        super(func);
        this.realFunction = func;
    }

    /**
     * Выбор средней точки x2, удовлетворяющей условиям
     * x1 < x2 < x3 && f(x1) >= f(x2) <= f(x3)
     */
    private double getValidMiddlePoint(double left, double right) {
        double m = (right + left) / 2;
        double fm = realFunction.apply(m);
        double fl = realFunction.apply(left);
        double fr = realFunction.apply(right);
        while (!(fl >= fm && fm <= fr)) {
            if (fl < fm) {
                right = m;
            }
            if (fm > fr) {
                left = m;
            }
            m = (right + left) / 2;
            fl = realFunction.apply(left);
            fr = realFunction.apply(right);
            fm = realFunction.apply(m);
        }
        return m;
    }

    /**
     * Реализация нахождения минимума для метода парабол
     */
    @Override
    public double findMin(double left, double right, double eps) {
        clear();
        double m = getValidMiddlePoint(left, right);
        double fm = func.apply(m);
        double fl = realFunction.apply(left);
        double fr = realFunction.apply(right);
        renderFunctions.clear();
        while (right - left > eps) {
            double x = (left + m - (((fm - fl) * (right - m)) / (m - left)) / ((fr - fl) / (right - left) - (fm - fl) / (m - left))) / 2;
            addSegment(left, right);
            double finalFm = fm;
            double finalFl = fl;
            double finalM = m;
            double finalLeft = left;
            double finalRight = right;
            double finalFr = fr;
            renderFunctions.add(t -> {
                double a1 = (finalFm - finalFl) / (finalM - finalLeft);
                double a2 = 1 / (finalRight - finalM) * ((finalFr - finalFl) / (finalRight - finalLeft) - (finalFl - finalFm) / (finalM - finalLeft));
                return finalFl + a1 * (t - finalLeft) + a2 * (t - finalLeft) * (t - finalM);
            });
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

    @Override
    public List<Function<Double, Double>> renderFunctions() {
        return renderFunctions;
    }
}
