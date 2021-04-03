package com.mygdx.nmethods;

import java.util.function.Function;

public class Value<T, R> {
    private T value;
    private final Function<T, R> func;
    private R fValue;

    public Value(final T value, final Function<T, R> f) {
        this.value = value;
        this.fValue = f.apply(value);
        this.func = f;
    }

    public void setValue(T value) {
        this.value = value;
        this.fValue = func.apply(value);
    }

    public T getValue() {
        return this.value;
    }

    public R getFValue() {
        return fValue;
    }
}
