package com.mygdx.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.mygdx.graphics.parser.ExpressionParser;
import com.mygdx.methods.*;
import com.mygdx.nmethods.*;
import com.mygdx.nmethods.Vector;

import java.util.*;
import java.util.List;
import java.util.function.Function;

public class GraphicEngine extends ApplicationAdapter {
    private Batch batch;
    private ShapeRenderer graphicRenderer;
    private Stage stage;
    private Graphic graphic;
    private RenderFunction func;
    private long start = System.currentTimeMillis();
    private Input.TextInputListener input;
    private BitmapFont bitmapFont;
    private ExpressionParser parser;

    Slider slider;
    Texture sliderBackgroundTex;
    Texture sliderKnobTex;

    private RenderFunction initFunction() {
        // 64x^2 + 126xy + 64y^2 - 10x + 30y + 13
        List<List<Double>> a = new ArrayList<>(2);
        a.add(Arrays.asList(128., 126.));
        a.add(Arrays.asList(126., 128.));
        return new RenderFunction(a, Arrays.asList(-10.0, 30.0), 13);
    }

    private void callTest(RenderFunction func) {
        new DrawableNMethod(new GradientMethod<>(func)).findMin(1e-2);
        graphic.setMain(func);
    }

    public void sleep(int fps) {
        if (fps > 0) {
            long diff = System.currentTimeMillis() - start;
            long targetDelay = 1000 / fps;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException ignored) {
                }
            }
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void create() {
        sleep(10);
        parser = new ExpressionParser();
        stage = new Stage();
        bitmapFont = new BitmapFont();
        batch = stage.getBatch();
        input = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                QuadraticFunction qf = parser.parse(text);
                callTest(new RenderFunction(qf.a, qf.b, qf.c));
            }

            @Override
            public void canceled() {
                Gdx.input.getTextInput(input,"эй", "", "введи");
            }
        };

        graphicRenderer = new ShapeRenderer();
        graphic = new Graphic(graphicRenderer, func = initFunction());
        graphic.setBounds(50, 50, 800, 800);
        stage.addActor(graphic);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(graphic);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.getTextInput(input, "Input function", "64*x*x + 126*x*y + 64*y*y - 10*x + 30*y + 13", "");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        graphicRenderer.begin(ShapeRenderer.ShapeType.Filled);
        graphicRenderer.setColor(Color.BLACK);
        stage.act();
        batch.end();
        graphicRenderer.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        graphicRenderer.dispose();
    }
}