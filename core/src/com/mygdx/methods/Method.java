package com.mygdx.methods;

public interface Method {


    /**
     * Searches for a function's local minima in range {@code [left, right]} within an eps-tolerance.
     * @param left left limit
     * @param right right limit
     * @param eps tolerance
     * @return the minima {@code eps}-close to the real one.
     */
    double findMin(double left, double right, double eps);
}
