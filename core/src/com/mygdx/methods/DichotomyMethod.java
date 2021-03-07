package com.mygdx.methods;

import java.util.function.Function;

public class DichotomyMethod extends AbstractDrawableMethod {
    private final double delta;
    public DichotomyMethod(Function<Double, Double> func, double delta) {
        super(func);
        this.delta = delta;
    }

    /**
     * Реализация нахождения минимума для метода дихотомии
     */
    public double findMin(double left, double right, double eps) {
        clear();
        log(left + " " + right);

        while ((right - left) / 2 > eps) {
            addSegment(left, right);
//            double x1 = ((left + right) - delta) / 2;
//            double x1 = ((left + right) - delta) / 2;
            double x1 = ((left + right) - eps) / 2;
            double x2 = ((left + right) + eps) / 2;
            if (callFun(x1) < callFun(x2)) {
                right = x2;
            } else {
                left = x1;
            }
            log(left + " " + right);

        }

        return (left + right) / 2;
    }
}
