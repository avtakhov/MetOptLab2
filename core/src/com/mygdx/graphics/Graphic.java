package com.mygdx.graphics;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graphic extends Actor implements InputProcessor {

    private final ShapeRenderer renderer;
    private float xl = -1;
    private float yl = -1;
    private float scale = 200;
    public final List<Point> renderPoints;
    private final Function<Float, Float> func;

    private float xr() {
        return xl + getWidth() / scale;
    }

    private float yr() {
        return yl + getHeight() / scale;
    }

    public Graphic(ShapeRenderer renderer, Function<Float, Float> func) {
        this.renderer = renderer;
        this.func = func;
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

    boolean between(float x1, float x2, float x3) {
        return x1 <= x2 && x2 <= x3;
    }

    boolean betweenX(float x) {
        return between(xl, x, xr());
    }

    boolean betweenY(float y) {
        return between(yl, y, yr());
    }

    float toRealX(float x) {
        return (x - xl) * scale + this.getX();
    }

    float toRealY(float y) {
        return (y - yl) * scale + this.getY();
    }

    void drawPoint(float x, float y, float r) {
        if (betweenX(x) && betweenY(y)) {
            renderer.circle(toRealX(x), toRealY(y), r);
        }
    }

    void drawLine(float x1, float y1, float x2, float y2, float r) {
        if (betweenX(x1) && betweenY(y1) && betweenX(x2) && betweenY(y2)) {
            renderer.rectLine(toRealX(x1), toRealY(y1), toRealX(x2), toRealY(y2), r);
        }
    }


    @Override
    public void act(float time) {
        final float STEP = 0.1f / scale;
        for (float i = xl; i <= xr(); i += STEP) {
            drawLine(i, func.apply(i), i + STEP, func.apply(i + STEP), 3);
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
        for (Point p : renderPoints) {
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

    private float unrealX(float x) {
        return (x - getX()) / scale + xl;
    }

    private float unrealY(float y) {
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
            scale = Math.max(scale, 1);
        }
        return true;
    }
}