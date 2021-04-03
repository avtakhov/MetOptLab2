package com.mygdx.graphics;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graphic extends Actor implements InputProcessor {

    private final ShapeRenderer renderer;
    private double xl = -1;
    private double yl = -1;
    private float scale = 200;
    public final List<ColoredPoint> renderPoints;
    private final Function<Double, Double> main;
    private Function<Double, Double> secondary;

    private double xr() {
        return xl + getWidth() / scale;
    }

    private double yr() {
        return yl + getHeight() / scale;
    }

    public Graphic(ShapeRenderer renderer,Function<Double, Double> main) {
        this.renderer = renderer;
        this.main = main;
        renderPoints = new ArrayList<>();
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
        return (float) Math.min(Math.max(y, getY()), getHeight() + getY());
    }

    private void drawLine(double x1, double y1, double x2, double y2, float r) {
        if (!(betweenX(x1) && betweenX(x2))) {
            return;
        }
        if (betweenY(y1) || betweenY(y2)) {
            renderer.rectLine(toRealX(x1), normalize(toRealY(y1)), toRealX(x2), normalize(toRealY(y2)), r);
        } else if (between(y1, yl, y2) || between(y1, yr(), y2)) {
            renderer.rectLine(toRealX(x1), normalize(toRealY(y1)), toRealX(x2), normalize(toRealY(y2)), r);
        }
    }

    public void setSecondary(Function<Double, Double> secondary) {
        this.secondary = secondary;
    }

    public void drawFunction(Function<Double, Double> func, float width) {
        if (func == null) {
            return;
        }
        final double STEP = 0.1 / scale;
        for (double i = xl; i <= xr(); i += STEP) {
            drawLine(i, func.apply(i), i + STEP, func.apply(i + STEP), width);
            renderer.setColor(Color.BLACK);
        }
    }

    @Override
    public void act(float time) {
        drawFunction(main, 3);
        drawFunction(secondary, 2);
        drawLine(0, yl, 0, yr(), 2f);
        drawLine(xl, 0, xr(), 0, 2f);
        final float EPS = 0.02f;
        for (float i = (float) Math.floor(xl); i <= xr(); ++i) {
            drawLine(i, -EPS, i, EPS, 1f);
        }
        for (float i = (float) Math.floor(yl); i <= yr(); ++i) {
            drawLine(-EPS, i, EPS, i, 1f);
        }
        for (ColoredPoint p : renderPoints) {
            renderer.setColor(p.color);
            drawPoint(p.x, p.y, p.width);
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
            scale = Math.max(scale, 50);
        }
        return true;
    }
}