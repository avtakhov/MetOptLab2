package com.mygdx.nmethods;

import com.mygdx.methods.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Double>> a = new ArrayList<>(2);
        a.add(Arrays.asList(128., 126.));
        a.add(Arrays.asList(126., 128.));
        // 64x^2 + 126xy + 64y^2 - 10x + 30y + 13
        QuadraticFunction f = new QuadraticFunction(a, Arrays.asList(-10.0, 30.0), 13);

        NMethod method = new GradientMethod<>(f);
        System.out.println("1 " + method.findMin(1e-3));

        NMethod method2 = new GradientOpt<>(f, BrentCombMethod::new);
        System.out.println("2 " + method2.findMin(1e-3));

        NMethod method3 = new NonlinearConjugateGradientMethod<>(f);
        System.out.println("3 " + method3.findMin(1e-3));

        System.out.println(f.apply(method3.findMin(1e-3)));
    }
}
