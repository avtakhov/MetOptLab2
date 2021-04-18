package com.mygdx.nmethods;

import java.util.function.Function;

public class Value<T, R> {
    private T value;
    private final Function<T, R> func;
    private R fValue;

    /**
     * Creates an instance of {@link Value} from the specified value and function.
     * {@link Value#getFValue()} returns the result of applying the function
     * to value.
     * @param value the value
     * @param f the function
     */
    public Value(final T value, final Function<T, R> f) {
        this.value = value;
        this.fValue = f.apply(value);
        this.func = f;
    }

    /**
     * Changes the value to a new one and the fValue according to the
     * specified function.
     * @param value new value
     */
    public void setValue(T value) {
        this.value = value;
        this.fValue = func.apply(value);
    }

    /**
     * returns the value
     * @return the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Returns the fValue which is a result of f(value).
     * @return the fValue
     */
    public R getFValue() {
        return fValue;
    }
}
