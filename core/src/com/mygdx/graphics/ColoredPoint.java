package com.mygdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.methods.Point;

public class ColoredPoint extends Point {
    public final Color color;
    public final float width;

    public ColoredPoint(float x, float y, Color color, float width) {
        super(x, y);
        this.color = color;
        this.width = width;
    }
}
