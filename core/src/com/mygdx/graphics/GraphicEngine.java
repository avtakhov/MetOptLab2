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
    private Input.TextInputListener input;
    private ExpressionParser parser;

    private void inputFunction(final String title) {
        Gdx.input.getTextInput(input, title, "10*x*x - x*y + y*y - 2*x + y + 8", "");
    }

    private void callTest(RenderFunction func, Function<RenderFunction, AbstractNMethod<RenderFunction>> methodCreator) {
        new DrawableNMethod(methodCreator.apply(func)).findMin(1e-2);
        graphic.setMain(func);
    }

    private void addButton(
            final String name,
            final float x,
            final float y,
            final Function<RenderFunction, AbstractNMethod<RenderFunction>> methodCreator) {
        Image b = new Image(new Texture("button_" + name + ".png"));
        b.setPosition(x, y);
        b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                callTest(func, methodCreator);
            }
        });
        stage.addActor(b);
    }

    @Override
    public void create() {
        parser = new ExpressionParser();
        stage = new Stage();
        batch = stage.getBatch();
        input = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                func = new RenderFunction(parser.parse(text));
            }

            @Override
            public void canceled() {
                if (func == null) {
                    inputFunction("эй");
                }
            }
        };
        inputFunction("Enter function, use +-*");
        graphicRenderer = new ShapeRenderer();
        graphic = new Graphic(graphicRenderer);
        graphic.setBounds(50, 50, 900, 900);
        stage.addActor(graphic);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(graphic);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        addButton("gradient-method", 1100, 800, GradientMethod::new);
        addButton("optimized-gradient", 1100, 650, func -> new GradientOpt<>(func, BrentCombMethod::new));
        addButton("conjugate-gradient", 1100, 500, NonlinearConjugateGradientMethod::new);
        Image change = new Image(new Texture("button_change-function.png"));
        change.setPosition(1100, 350);
        change.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.getTextInput(input, "Input function", "10*x*x - x*y + y*y - 2*x + y + 8", "");
            }
        });
        stage.addActor(change);
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
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        graphicRenderer.dispose();
    }
}