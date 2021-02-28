package com.mygdx.methods;

import java.util.function.Function;

public abstract class AbstractAlgorithm implements Method {
    final protected Function<Double, Double> func;

    AbstractAlgorithm(Function<Double, Double> func) {
        this.func = func;
    }
}
