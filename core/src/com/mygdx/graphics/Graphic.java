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
    private final RenderFunction main;

    private double xr() {
        return xl + getWidth() / scale;
    }

    private double yr() {
        return yl + getHeight() / scale;
    }

    public Graphic(ShapeRenderer renderer, RenderFunction main) {
        this.renderer = renderer;
        this.main = main;
        if (main.n != 2) {
            throw new IllegalArgumentException("invalid function dimen, expected 2, given " + main.n);
        }
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

    private void drawLevel(final double level, final QuadraticFunction f, final float width) {
        final double STEP = 1 / scale;
        for (double x = xl; x < xr(); x += STEP) {
            double qa = f.a.get(1).get(1) / 2;
            double qb = f.a.get(0).get(1) * x + f.b.get(1);
            double qc = f.a.get(0).get(0) / 2 * sqr(x) + f.b.get(0) * x + f.c - level;
            double d = sqr(qb) - 4 * qa * qc;
            assert d >= 0;
            drawPoint(x, (-qb + Math.sqrt(d)) / (2 * qa), width);
            drawPoint(x, (-qb - Math.sqrt(d)) / (2 * qa), width);
        }
    }

    @Override
    public void act(float time) {

        for (int i = 1; i < main.renderPoints.size(); ++i) {
            Value<Vector, Double> t = main.renderPoints.get(i);
            if (i % 100 == 1) {
                drawLevel(t.getFValue(), main, 1f);
            }
            renderer.setColor(Color.ORANGE);
            drawPoint(t.getValue().get(0), t.getValue().get(1), 3);
            renderer.setColor(Color.BLACK);
            Value<Vector, Double> prev = main.renderPoints.get(i - 1);
            drawLine(t.getValue().get(0), t.getValue().get(1), prev.getValue().get(0), prev.getValue().get(1), 1);
        }
        drawLine(0, yl, 0, yr(), 2f);
        drawLine(xl, 0, xr(), 0, 2f);
        final float EPS = 0.02f;
        for (float i = (float) Math.floor(xl); i <= xr(); ++i) {
            drawLine(i, -EPS, i, EPS, 1f);
        }
        for (float i = (float) Math.floor(yl); i <= yr(); ++i) {
            drawLine(-EPS, i, EPS, i, 1f);
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
        if (amountY == -1.0) {
            final float h = 10;
            xl = (xl * scale + h * unrealX(lastX)) / (h + scale);
            yl = (yl * scale + h * unrealY(lastY)) / (h + scale);
            scale += h;
        } else {
            scale -= 10;
            scale = Math.max(scale, 3);
        }
        return true;
    }
}