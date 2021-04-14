package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagonalFunction extends QuadraticFunction {

    public DiagonalFunction(List<Double> a, List<Double> b, double c) {
        super(new DiagonalMatrix(a), b, c);
    }

    @Override
    public Vector gradient(Vector point) {
        Vector result = new Vector();
        for (int i = 0; i < point.size(); i++) {
            result.add(point.get(i) * a.get(i, i) + b.get(i));
        }
        return result;
    }

    @Override
    public Double apply(Vector arg) {
        double result = 0.;
        for (int i = 0; i < arg.size(); i++) {
            result += arg.get(i) * arg.get(i) * a.get(i, i) / 2 + arg.get(i) * b.get(i);
        }
        result += c;
        return result;
    }
}
