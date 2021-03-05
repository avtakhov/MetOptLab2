package com.mygdx.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDrawableMethod implements DrawableMethod {
    protected final Function<Double, Double> func;
    private final List<Segment> renderSegments = new ArrayList<>();
    private final List<Point> renderPoints = new ArrayList<>();

    /**
     * Добавление возможности отметки точек текущего решения одновременно с подсчетом функции
     */
    private class PointFunctions implements Function<Double, Double> {
        public final Function<Double, Double> func;

        PointFunctions(Function<Double, Double> func) {
            this.func = func;
        }

        @Override
        public Double apply(Double x) {
            Double y = this.func.apply(x);
            renderPoints.add(new Point(x.floatValue(), y.floatValue()));
            return y;
        }
    }

    /**
     * Принимает и инициализирует функцию, для которой будет применяться поиск
     */
    AbstractDrawableMethod(Function<Double, Double> func) {
        this.func = new PointFunctions(func);
//        this.func = func;
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


}
