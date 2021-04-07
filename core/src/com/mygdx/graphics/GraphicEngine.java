package com.mygdx.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.methods.*;
import com.mygdx.nmethods.*;
import com.mygdx.nmethods.Vector;

import java.util.*;
import java.util.function.Function;

public class GraphicEngine extends ApplicationAdapter {
    private Batch batch;
    private BitmapFont bitmapFont;
    private ShapeRenderer graphicRenderer;
    private Stage stage;
    private Graphic graphic;
    private RenderFunction func;

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

    private void callTest() {
        new GradientMethod(func).findMin(1e-3);
    }

    @Override
    public void create() {
        stage = new Stage();
        batch = stage.getBatch();
        graphicRenderer = new ShapeRenderer();
        graphic = new Graphic(graphicRenderer, func = initFunction());
        graphic.setBounds(50, 50, 800, 800);
        stage.addActor(graphic);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(graphic);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        callTest();
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
        bitmapFont.dispose();
        batch.dispose();
        graphicRenderer.dispose();
    }
}