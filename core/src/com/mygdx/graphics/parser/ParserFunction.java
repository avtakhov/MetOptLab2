package com.mygdx.graphics.parser;

import com.mygdx.nmethods.QuadraticFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParserFunction extends QuadraticFunction {
    public ParserFunction(List<List<Double>> a, List<Double> b, double c) {
        super(a, b, c);
    }

    public ParserFunction(int n, double c) {
        super(Collections.nCopies(n, Collections.nCopies(n, 0.)), Collections.nCopies(n, 0.), c);
    }

    public ParserFunction(int n, String var) {
        super(Collections.nCopies(n, Collections.nCopies(n, 0.)), var.equals("x") ? Arrays.asList(1., 0.) : Arrays.asList(0., 1.), 0);

    }

    public ParserFunction add(final QuadraticFunction other) {
        final List<List<Double>> ra = new ArrayList<>();
        IntStream.range(0, getN()).forEach(i -> ra.add(i, a.get(i).add(other.a.get(i))));
        return new ParserFunction(ra, b.add(other.b), c + other.c);
    }

    public ParserFunction multiply(final QuadraticFunction other) {
        final List<List<Double>> ra = new ArrayList<>();
        ra.add(Arrays.asList(2 * b.get(0) * other.b.get(0), b.get(0) * b.get(1) + other.b.get(1) * b.get(0)));
        ra.add(Arrays.asList(b.get(0) * b.get(1) + other.b.get(1) * b.get(0), 2 * b.get(1) * other.b.get(1)));
        return new ParserFunction(ra, b.multiply(other.c).add(other.b.multiply(c)), other.c * c);
    }

    public ParserFunction negative() {
        return this.multiply(new ParserFunction(getN(), -1));
    }
}
