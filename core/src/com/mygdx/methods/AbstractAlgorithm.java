package com.mygdx.methods;

import java.util.function.Function;

public abstract class AbstractAlgorithm implements Method {
    final protected Function<Double, Double> drawFunc;

    /**
     * Принимает и инициализирует функцию, для которой будет применяться поиск
     */
    AbstractAlgorithm(Function<Double, Double> func) {
        this.drawFunc = func;
    }
}
