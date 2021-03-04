package com.mygdx.methods;

public class Segment {
    private final double left;
    private final double right;

    public Segment(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
