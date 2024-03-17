package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.scene.GameScene;

public class StartScene extends GameScene {


    private BitmapFont font1, font2, font3;
    private String text1 = "MY GAME", text2 = "TESTING", text3 = "Click anywhere to continue";
    private int fontSize = 70, fontSize2 = 40, fontSize3 = 32;
    private GlyphLayout layout1, layout2, layout3;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private Vector2 vec;
    private Texture backgroundTexture, playButton;

    private float playbuttonX,playbuttonY;

    public StartScene(GameSceneManager gsm) {
            super(gsm);

            super.playStartEndMusic();

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


            //vector to detect click position
            vec = new Vector2();

            //resize bg image
            Pixmap pixmap;
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/main page background.png"));
            pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            backgroundTexture = new Texture(pixmap);
            pixmap.dispose();
            pixmapOriginal.dispose();

            //Start button
            playButton = new Texture(Gdx.files.internal("sprites/start button.png"));
            playbuttonX = Gdx.graphics.getWidth()/2 - playButton.getWidth()/2;
            playbuttonY = Gdx.graphics.getHeight()/2 - playButton.getHeight()/2;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isTouched()) {
            vec.x = Gdx.input.getX();
            vec.y = Gdx.input.getY();
            if (vec.x > playbuttonX && vec.x < playbuttonX +  playButton.getWidth() &&
                    vec.y > playbuttonY && vec.y < playbuttonY + playButton.getHeight()) {

                //change scene
                System.out.println("Scene Changed\n");
                musicPlayer.stop();
                gsm.setState(GameSceneManager.Scene.LEVELSELECT);
            }

        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 0f));
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(playButton, playbuttonX, playbuttonY);
        batch.end();


    }

    @Override
    public void dispose() {
        System.out.println("Scene disposed");
        font1.dispose();
        ;
        font2.dispose();
        ;
        font3.dispose();
        ;
    }
}
