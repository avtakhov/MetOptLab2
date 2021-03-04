package com.mygdx.methods;

import java.util.function.Function;

public class DichotomyMethod extends AbstractDrawableMethod {
    final double delta;
    public DichotomyMethod(Function<Double, Double> func, double delta) {
        super(func);
        this.delta = delta;
    }

    /**
     * Реализация нахождения минимума для метода дихотомии
     */
    public double findMin(double left, double right, double eps) {
        clear();
        while ((right - left) / 2 > eps) {
            addSegment(left, right);
            double x1 = ((left + right) - delta) / 2;
            double x2 = ((left + right) + delta) / 2;
            if (drawFunc.apply(x1) < drawFunc.apply(x2)) {
                right = x2;
            } else {
                left = x1;
            }
        }
        return (left + right) / 2;
    }
}
