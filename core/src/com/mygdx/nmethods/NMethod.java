package com.mygdx.nmethods;

public interface NMethod {
    /**
     * Searches for a function's minima.
     * @param eps tolerance
     * @return The value eps-close to the real minima.
     */
    Vector findMin(double eps);
}
