package com.mygdx.methods;

import java.util.function.Function;

public class GoldenSectionMethod extends AbstractDrawableMethod {

    final double TAU = (Math.sqrt(5) - 1) / 2;

    public GoldenSectionMethod(Function<Double, Double> func) {
        super(func);
    }

    /**
     *Реализация нахождения минимума для метода золотого сечения
     */
    @Override
    public double findMin(double left, double right, double eps) {
        clear();
        double x1 = left + ((3 - Math.sqrt(5)) / 2 * (right - left));
        double x2 = left + ((Math.sqrt(5) - 1) / 2 * (right - left));
        double fx1 = drawFunc.apply(x1);
        double fx2 = drawFunc.apply(x2);
        double curEps = (right - left) / 2;
        while (curEps > eps) {
            addSegment(left, right);
            if (fx1 < fx2) {
                right = x2;
                x2 = x1;
                fx2 = fx1;
                x1 = right - TAU * (right - left);
                fx1 = drawFunc.apply(x1);
            } else {
                left = x1;
                x1 = x2;
                fx1 = fx2;
                x2 = left + TAU * (right - left);
                fx2 = drawFunc.apply(x2);
            }
            curEps = TAU * curEps;
        }
        return (left + right) / 2;
    }
}
