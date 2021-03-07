package com.mygdx.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDrawableMethod implements DrawableMethod {
    protected final Function<Double, Double> func;
    private final List<Segment> renderSegments = new ArrayList<>();
    private final List<Point> renderPoints = new ArrayList<>();
    private int funCalls;
    private boolean log;

    AbstractDrawableMethod(Function<Double, Double> func) {
        this.func = func;
        log = true;
    }

    protected void addSegment(double l, double r) {
        renderSegments.add(new Segment(l, r));
    }

    protected void clear() {
        renderPoints.clear();
        renderSegments.clear();
    }

    @Override
    public List<Segment> renderSegments() {
        return renderSegments;
    }

    @Override
    public List<Function<Double, Double>> renderFunctions() {
        return Collections.emptyList();
    }

    @Override
    public List<Point> renderPoints() {
        return renderPoints;
    }

    public double callFun(Double arg) {
        return callFun(arg, true);
    }

    public double callFun(Double arg, boolean draw) {
        Double y = this.func.apply(arg);
        if (draw) {
            renderPoints.add(new Point(arg.floatValue(), y.floatValue()));
        }
        funCalls++;
        return y;
    }

    public int getFunCalls() {
        return funCalls;
    }

    public void resetFunCalls() {
        funCalls = 0;
    }

    public void log(String msg) {
        if (log) {
            System.out.println(msg);
        }
    }

    public void setLog(boolean log) {
        this.log = log;
    }

}
