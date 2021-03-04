package com.mygdx.methods;

import java.util.List;
import java.util.function.Function;

public interface DrawableMethod extends Method {
    List<Segment> renderSegments();

    List<Function<Double, Double>> renderFunctions();

    List<Point> renderPoints();
}
