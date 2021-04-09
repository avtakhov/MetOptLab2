package com.mygdx.nmethods;

import com.mygdx.graphics.parser.ExpressionParser;
import com.mygdx.methods.*;

public class Main {
    public static void main(String[] args) {
        QuadraticFunction f = new ExpressionParser().parse("10*x*x + y*y - 5*x + 3*y + 1");

        NMethod method = new GradientMethod<>(f);
        System.out.println("1 " + method.findMin(1e-3));

        NMethod method2 = new GradientOpt<>(f, BrentCombMethod::new);
        System.out.println("2 " + method2.findMin(1e-3));

        NMethod method3 = new NonlinearConjugateGradientMethod<>(f);
        System.out.println("3 " + method3.findMin(1e-3));

        System.out.println(f.apply(method3.findMin(1e-3)));
    }
}
