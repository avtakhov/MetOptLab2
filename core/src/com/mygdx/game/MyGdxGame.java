package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.methods.*;

import java.util.function.Function;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture texture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    BitmapFont bitmapFont;
    ShapeRenderer shapeRenderer;
    ShapeRenderer pointRenderer;
    ImageButton button;
    Stage stage;
    Method fib;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    Label.LabelStyle labelStyle;
    Label label;

    class RedPointFunc implements Function<Double, Double> {
        @Override
        public Double apply(Double x) {
            pointRenderer.setColor(Color.RED);
            pointRenderer.circle((float) (x * 90 + 100), (float) (x - Math.log(x)) * 90 + 100, 5);

            return x - Math.log(x);
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        pointRenderer = new ShapeRenderer();
        stage = new Stage();
        texture = new Texture(Gdx.files.internal("sources/memkek.jpg"));
        textureRegion = new TextureRegion(texture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        button = new ImageButton(textureRegionDrawable);
        button.setBounds(600f, 600f, 100f, 100f);
        stage = new Stage(new ScreenViewport());
        stage.addActor(button);
        fib = new FibonacciMethod(new RedPointFunc());
        Gdx.input.setInputProcessor(stage);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ShortBaby-Mg2w.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        bitmapFont = generator.generateFont(parameter);
        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        label = new Label("XXX", labelStyle);
        label.setFontScale(3);
        stage.addActor(label);
    }

    private void drawAxes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rectLine(100, 100, 900, 100, 2);
        shapeRenderer.rectLine(100, 100, 100, 650, 2);
        shapeRenderer.end();
    }

    private void drawGraphic() {
        pointRenderer.begin(ShapeRenderer.ShapeType.Filled);
        pointRenderer.setColor(Color.BLACK);
        float step = 0.0001f;
        for (float i = 0.001f; i < 10.0; i += step) {
            if (i >= 0.5f && i <= 4.0) {
                pointRenderer.setColor(Color.PURPLE);
            } else {
                pointRenderer.setColor(Color.BLACK);
            }
            pointRenderer.circle(i * 90 + 100, (float) (i - Math.log(i)) * 90 + 100, 3);
        }
        fib.findMin(0.5, 4, 0.001);
        pointRenderer.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawAxes();
        drawGraphic();
        batch.begin();
        batch.setColor(Color.VIOLET);
        bitmapFont.draw(batch, "XXX", 1500, 800);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
        pointRenderer.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        texture.dispose();
    }
}
