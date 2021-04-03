package com.mygdx.methods;

import com.mygdx.nmethods.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDrawableMethod implements Method {
    protected final Function<Double, Double> func;

    AbstractDrawableMethod(Function<Double, Double> func) {
        this.func = func;
    }

}
