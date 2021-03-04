package com.mygdx.methods;

import java.util.function.Function;

public class FibonacciMethod extends AbstractDrawableMethod {

    public FibonacciMethod(Function<Double, Double> func) {
        super(func);
    }

    @Override
    public double findMin(double left, double right, double eps) {
        clear();
        long fibPrevious = 1;
        long fibCurrent = 1;
        int n = 1;
        while ((right - left) / fibCurrent > eps) {
            long next = fibCurrent + fibPrevious;
            fibPrevious = fibCurrent;
            fibCurrent = next;
            n++;
        }
        double x2 = left + (right - left) * fibPrevious / fibCurrent;
        double x1 = left + (right - x2);
        double fx1 = func.apply(x1);
        double fx2 = func.apply(x2);
        while (n > 0) {
            addSegment(left, right);
            n--;
            if (fx1 < fx2) {
                right = x2;
                x2 = x1;
                fx2 = fx1;
                x1 = left + (right - x2);
                fx1 = func.apply(x1);
            } else {
                left = x1;
                x1 = x2;
                fx1 = fx2;
                x2 = right - (x1 - left);
                fx2 = func.apply(x2);
            }
        }
        return (left + right) / 2;
    }
}