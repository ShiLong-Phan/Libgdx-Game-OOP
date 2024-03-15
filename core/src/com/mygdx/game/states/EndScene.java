package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.managers.GameSceneManager;

public class EndScene extends GameScene {

    private Texture tex;


    private BitmapFont font1, font2, font3;
    private SpriteBatch batch;
    private String text1 = "END SCREEN", text2 = "TESTING", text3 = "Click anywhere to close";
    private int fontSize = 70, fontSize2 = 40, fontSize3 = 32;
    private GlyphLayout layout1, layout2, layout3;


    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private float accumulator = 0;

    public EndScene(GameSceneManager gsm) {
        super(gsm);
        /*
        font = new BitmapFont();
        layout = new GlyphLayout(font, title, Color.RED, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/5, true);
        */

        batch = new SpriteBatch();

        //font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Trajan Pro Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.GREEN;

        //font1
        parameter.size = fontSize;
        font1 = generator.generateFont(parameter);
        layout1 = new GlyphLayout(font1, text1);
        //font2
        parameter.size = fontSize2;
        font2 = generator.generateFont(parameter);
        layout2 = new GlyphLayout(font2, text2);
        //font3
        parameter.size = fontSize3;
        font3 = generator.generateFont(parameter);
        layout3 = new GlyphLayout(font3, text3);

        generator.dispose();

        //play music
        super.playStartEndMusic();

        System.out.println("Scene Changed (LAST)\n");

    }

    @Override
    public void update(float delta) {
        accumulator += delta;
        if ((Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) && accumulator > 1) {
            //change scene
            System.out.println("Scene End\n");
            musicPlayer.stop();
            Gdx.app.exit();

        }
    }

    @Override
    public void render() {
        float x = (Gdx.graphics.getWidth() + layout1.width) / 2;
        float y = Gdx.graphics.getHeight() / 10;
        ScreenUtils.clear(new Color(0, 0, 0.5f, 0.9f));
        batch.begin();
        font1.draw(batch, layout1, Gdx.graphics.getWidth() / 2 - layout1.width / 2, y * 6);
        font2.draw(batch, layout2, Gdx.graphics.getWidth() / 2 - layout2.width / 2, y * 5);
        font3.draw(batch, layout3, Gdx.graphics.getWidth() / 2 - layout3.width / 2, y * 1.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        System.out.println("Scene disposed");

    }
}