package com.mygdx.methods;

import java.util.List;
import java.util.function.Function;

public interface DrawableMethod extends Method {

    /**
     * Отрисовка графика отрезками
     */
    List<Segment> renderSegments();

    /**
     * Дополнительные функции для отрисовки (в частности параболы для метода парабол)
     */
    List<Function<Double, Double>> renderFunctions();

    /**
     * Точки промежуточных решений для отрисовки
     */
    List<Point> renderPoints();
}
