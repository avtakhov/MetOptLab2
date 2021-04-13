package com.mygdx.nmethods;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class DiagonalMatrix extends Matrix{
    public DiagonalMatrix(List<Double> a) {
        super(generateDiagonal(a));
    }

    private static List<List<Double>> generateDiagonal(List<Double> b) {
        List<List<Double>> diagonalMatrix = new ArrayList<>();
        for (int i = 0; i < b.size(); i++) {
            final int ind = i;
            diagonalMatrix.add(new AbstractList<Double>() {
                @Override
                public Double get(int index) {
                    if (ind == index) {
                        return 2 * b.get(index);
                    } else {
                        return 0.;
                    }
                }

                @Override
                public int size() {
                    return b.size();
                }
            });
        }
        return diagonalMatrix;
    }

    @Override
    public Vector multiply(final Vector other) {
        Vector result = new Vector();
        for (int i = 0; i < other.size(); i++) {
            result.add(super.get(i, i) * other.get(i));
        }
        return result;
    }
}
