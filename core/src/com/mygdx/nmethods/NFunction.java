package com.mygdx.nmethods;

import com.mygdx.nmethods.Vector;
import java.util.function.Function;

public interface NFunction extends Function<Vector, Double> {
    @Override
    Double apply(Vector arg);

    Vector gradient(Vector point);

    int getN();
}
