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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.methods.*;

import java.util.*;
import java.util.function.Function;

public class GraphicEngine extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    private ShapeRenderer graphicRenderer;
    private Stage stage;
    private List<Segment> processSegments = Collections.singletonList(new Segment(0.5, 4));
    private List<Function<Double, Double>> processFunctions = Collections.emptyList();
    private Map<Button, AbstractDrawableMethod> methods;
    private final Function<Double, Double> target = x -> x - Math.log(x);
    private Graphic graphic;

    Slider slider;
    Texture sliderBackgroundTex;
    Texture sliderKnobTex;
    @Override
    public void create() {
        methods = new HashMap<>();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        graphicRenderer = new ShapeRenderer();
        stage = new Stage();
        graphic = new Graphic(graphicRenderer, target);
        graphic.setBounds(100, 100, 800, 800);
        stage.addActor(graphic);
        ArrayList<TextureRegionDrawable> textures = new ArrayList<>();
        textures.add(getButtonImage("dichotomy.png"));
        textures.add(getButtonImage("golden-section.png"));
        textures.add(getButtonImage("fibonacci.png"));
        textures.add(getButtonImage("parabola.png"));
        textures.add(getButtonImage("brent-comb.png"));
        int index = 0;
        float xCoord = 1200f;
        float yCoord = 800f;
        List<AbstractDrawableMethod> methodList = Arrays.asList(
                new DichotomyMethod(target, 1e-3),
                new GoldenSectionMethod(target),
                new FibonacciMethod(target),
                new ParabolaMethod(target),
                new BrentCombMethod(target));
        for (AbstractDrawableMethod m : methodList) {
            Button b = new ImageButton(textures.get(index));
            if (index % 3 == 0) {
                xCoord += 200f;
            }
            b.setBounds(xCoord, yCoord - 200 * (index % 3), 150f, 150f);
            b.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    graphic.renderPoints.clear();
                    graphic.setSecondary(null);
                    AbstractDrawableMethod method = methods.get(b);
                    method.findMin(0.5, 4, 1e-3);
                    processSegments = method.renderSegments();
                    processFunctions = method.renderFunctions();
                    List<Point> points = method.renderPoints();
                    for (int i = 0; i < points.size(); ++i) {
                        Point p = points.get(i);
                        graphic.renderPoints.add(new ColoredPoint(p.x, p.y, new Color((float) Math.log(i) / 4, 0, 0, 1), 5));
                    }
                };
            });
            index++;
            methods.put(b, m);
        }
        for (Button b : methods.keySet())
            stage.addActor(b);

        // slider
        sliderBackgroundTex = new Texture(Gdx.files.internal("sources/slider_background.png"));
        sliderKnobTex = new Texture(Gdx.files.internal("sources/slider_knob.png"));
        Slider.SliderStyle ss = new Slider.SliderStyle();
        ss.background = new TextureRegionDrawable(new TextureRegion(sliderBackgroundTex));
        ss.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTex));
        slider = new Slider(0f, 100f, 1f, false, ss);
        slider.setPosition(1000, 200);
        graphic.setHighlight(processSegments.get(0));
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                double percent = slider.getValue() / slider.getMaxValue();
                graphic.setHighlight(processSegments.isEmpty()
                        ? null
                        : processSegments.get((int) ((processSegments.size() - 1) * percent)));

                graphic.setSecondary(processFunctions.isEmpty()
                        ? null
                        : processFunctions.get((int) ((processFunctions.size() - 1) * percent)));
            }
        });
        stage.addActor(slider);

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