package com.mygdx.graphics;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.nmethods.QuadraticFunction;
import com.mygdx.nmethods.Value;
import com.mygdx.nmethods.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graphic extends Actor implements InputProcessor {

    private final ShapeRenderer renderer;
    private double xl = -1;
    private double yl = -1;
    private float scale = 200;
    private RenderFunction main = null;

    private double xr() {
        return xl + getWidth() / scale;
    }

    private double yr() {
        return yl + getHeight() / scale;
    }

    public Graphic(ShapeRenderer renderer) {
        this.renderer = renderer;
        super.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                xl -= getDeltaX() / scale / 2;
                yl -= getDeltaY() / scale / 2;
            }
            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                lastX = x;
                lastY = y;
                return true;
            }
        });
    }

    public void setMain(RenderFunction function) {
        if (function.getN() != 2) {
            throw new IllegalArgumentException("invalid dimen " + function.getN());
        }
        main = function;
    }

    private boolean between(double x1, double x2, double x3) {
        return x1 <= x2 && x2 <= x3;
    }

    private boolean betweenX(double x) {
        return between(xl, x, xr());
    }

    private boolean betweenY(double y) {
        return between(yl, y, yr());
    }

    private float toRealX(double x) {
        return (float) (x - xl) * scale + this.getX();
    }

    private float toRealY(double y) {
        return (float) (y - yl) * scale + this.getY();
    }

    private void drawPoint(double x, double y, float r) {
        if (betweenX(x) && betweenY(y)) {
            renderer.circle(toRealX(x), toRealY(y), r);
        }
    }

    float normalize(float y) {
        return Math.min(Math.max(y, getY()), getHeight() + getY());
    }

    private void drawLine(double x1, double y1, double x2, double y2, float r) {
        if (!(betweenX(x1) && betweenX(x2) && betweenY(y1) && betweenY(y2))) {
            return;
        }
        renderer.rectLine(toRealX(x1), normalize(toRealY(y1)), toRealX(x2), normalize(toRealY(y2)), r);
    }

    private static double sqr(double t) {
        return t * t;
    }

    private static class DoublePair {
        private final double key;
        private final double value;

        public DoublePair(double key, double value) {
            this.key = key;
            this.value = value;
        }

        public double getKey() {
            return key;
        }

        public double getValue() {
            return value;
        }
    }

    private static DoublePair solveQuadraticEquation(double a, double b, double c) {
        double d = sqr(b) - 4 * a * c;
        assert d >= 0;
        return new DoublePair((-b + Math.sqrt(d)) / (2 * a), (-b - Math.sqrt(d)) / (2 * a));
    }

    private void drawLevel(final double level, final QuadraticFunction f, final float width) {
        final double STEP = 3 / scale;
        double t1 = Double.POSITIVE_INFINITY;
        double t2 = Double.POSITIVE_INFINITY;
        for (double x = xl; x < xr(); x += STEP) {
            DoublePair solve = solveQuadraticEquation(
                    f.a.get(1).get(1) / 2,
                    f.a.get(0).get(1) * x + f.b.get(1),
                    f.a.get(0).get(0) / 2 * sqr(x) + f.b.get(0) * x + f.c - level);
            drawLine(x - STEP, t1, x, solve.getKey(), width);
            drawLine(x - STEP, t2, x, solve.getValue(), width);
            t1 = solve.getKey();
            t2 = solve.getValue();
        }

        t1 = t2 = Double.POSITIVE_INFINITY;
        for (double y = yl; y < yr(); y += STEP) {
            DoublePair solve = solveQuadraticEquation(
                    f.a.get(0).get(0) / 2,
                    f.a.get(0).get(1) * y + f.b.get(0),
                    f.a.get(1).get(1) / 2 * sqr(y) + f.b.get(1) * y + f.c - level);
            drawLine(t1, y - STEP, solve.getKey(), y, width);
            drawLine(t2, y - STEP, solve.getValue(), y, width);

            t1 = solve.getKey();
            t2 = solve.getValue();
        }


    }

    private void listPoint(int index, Color color) {
        renderer.setColor(color);
        drawPoint(main.renderPoints.get(index).getValue().get(0), main.renderPoints.get(index).getValue().get(1), 3);
    }

    @Override
    public void act(float time) {
        drawLine(0, yl, 0, yr(), 2f);
        drawLine(xl, 0, xr(), 0, 2f);
        final float EPS = 0.02f;
        for (float i = (float) Math.floor(xl); i <= xr(); ++i) {
            drawLine(i, -EPS, i, EPS, 1f);
        }
        for (float i = (float) Math.floor(yl); i <= yr(); ++i) {
            drawLine(-EPS, i, EPS, i, 1f);
        }
        if (main != null) {
            listPoint(0, Color.YELLOW);
            for (int i = 1; i < main.renderPoints.size(); ++i) {
                Value<Vector, Double> t = main.renderPoints.get(i);
                Value<Vector, Double> prev = main.renderPoints.get(i - 1);
                renderer.setColor(Color.BLACK);
                drawLevel(t.getFValue(), main, 1);
                renderer.setColor(Color.ORANGE);
                drawPoint(t.getValue().get(0), t.getValue().get(1), 3);
                renderer.setColor(Color.LIGHT_GRAY);
                drawLine(t.getValue().get(0), t.getValue().get(1), prev.getValue().get(0), prev.getValue().get(1), 1);
            }
            listPoint(main.renderPoints.size() - 1, Color.RED);
            renderer.setColor(Color.BLACK);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    private double unrealX(float x) {
        return (x - getX()) / scale + xl;
    }

    private double unrealY(float y) {
        return (y - getY()) / scale + yl;
    }

    private float lastX;
    private float lastY;
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        final float h = (float) (10 + 5 * Math.sqrt(scale));
        if (amountY == -1.0) {
            xl = (xl * scale + h * unrealX(lastX)) / (h + scale);
            yl = (yl * scale + h * unrealY(lastY)) / (h + scale);
            scale += h;
        } else {
            scale -= h;
            scale = Math.max(scale, 3);
        }
        return true;
    }
}