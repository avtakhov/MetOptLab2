package com.mygdx.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.methods.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GraphicEngine extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont bitmapFont;
    ShapeRenderer graphicRenderer;
    Stage stage;
    Map<Button, Method> methods;
    Function<Double, Double> target = x -> x - Math.log(x);
    Graphic graphic;

    class RedPointFunc implements Function<Double, Double> {

        final Function<Double, Double> func;
        int count = 0;

        RedPointFunc(final Function<Double, Double> func) {
            this.func = func;
        }

        @Override
        public Double apply(Double x) {
            count++;
            Double fx = func.apply(x);
            graphic.renderPoints.add(new Point(x.floatValue(), fx.floatValue(), new Color((float) Math.log(count) / 3.5f, 0,0,1)));
            return fx;
        }
    }


    @Override
    public void create() {
        methods = new HashMap<>();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        graphicRenderer = new ShapeRenderer();
        stage = new Stage();
        RedPointFunc func = new RedPointFunc(target);
        graphic = new Graphic(graphicRenderer, x -> target.apply(x.doubleValue()).floatValue());
        graphic.setBounds(100, 100, 800, 800);
        stage.addActor(graphic);
        ArrayList<TextureRegionDrawable> textures = new ArrayList<>();
        textures.add(getButtonImage("sources/dichotomy.png"));
        textures.add(getButtonImage("sources/golden-section.png"));
        textures.add(getButtonImage("sources/fibonacci.png"));
        textures.add(getButtonImage("sources/parabola.png"));
        textures.add(getButtonImage("sources/brent-comb.png"));
        int index = 0;
        int coef = 0;
        float xCoord = 1200f;
        float yCoord = 800f;
        for (Method m : Algorithms.methodList(func)) {
            System.out.println(index + " " + coef);
            Button b = new ImageButton(textures.get(index));
            if (index % 3 == 0) {
                xCoord += 200f;
            }
            b.setBounds(xCoord, yCoord - 200 * (index % 3), 150f, 150f);
            b.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    graphic.renderPoints.clear();
                    func.count = 0;
                    methods.get(b).findMin(0.5, 4, 1e-3);
                };
            });
            index++;
            methods.put(b, m);
        }
        for (Button b : methods.keySet())
            stage.addActor(b);
        Gdx.input.setInputProcessor(stage);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(graphic);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private TextureRegionDrawable getButtonImage(String content) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(content))));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        graphicRenderer.begin(ShapeRenderer.ShapeType.Filled);
        graphicRenderer.setColor(Color.BLACK);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        graphicRenderer.end();
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        bitmapFont.dispose();
        batch.dispose();
        graphicRenderer.dispose();
    }
}