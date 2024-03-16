package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.managers.GameSceneManager;

public class StartScene extends GameScene {

    private Texture tex;


    private BitmapFont font1, font2, font3;
    private String text1 = "MY GAME", text2 = "TESTING", text3 = "Click anywhere to continue";
    private int fontSize = 70, fontSize2 = 40, fontSize3 = 32;
    private GlyphLayout layout1, layout2, layout3;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private TextButton button;
    private Stage stage;
    private Vector2 vec;
    private float btnWidth = 100, btnHeight = 70;
    private Texture backgroundTexture;

    private TextureRegion character;

    public StartScene(GameSceneManager gsm) {
            super(gsm);
            /*
            font = new BitmapFont();
            layout = new GlyphLayout(font, title, Color.RED, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/5, true);
            */
            //music
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

            //buttons
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);

            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = font2;
            textButtonStyle.fontColor = Color.WHITE;

            button = new TextButton("Play Game", textButtonStyle);
            button.setSize(btnWidth, btnHeight);
            button.setScale(Application.SCALE);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //gsm.setState(GameSceneManager.State.MAIN);
                }
            });

            button.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            stage.addActor(button);

            //vector to detect click position
            vec = new Vector2();

            //resize bg image
            Pixmap pixmap;
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("skybackground.png"));
            pixmap = new Pixmap(Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            backgroundTexture = new Texture(pixmap);
            pixmap.dispose();
            pixmapOriginal.dispose();


            //testing
            Texture chartex;
            //System.out.println(chartex.getWidth() + " " +chartex.getHeight());

            Pixmap pixmap1;
            Pixmap pixmapOriginal2 = new Pixmap(Gdx.files.internal("sprites/char.png"));
            pixmap1 = new Pixmap((int) 100, (int) 100,pixmapOriginal2.getFormat());
            pixmap1.drawPixmap(pixmapOriginal2,0,0,pixmapOriginal2.getWidth(),pixmapOriginal2.getHeight(),0,0, pixmap1.getWidth(), pixmap1.getHeight());
            Texture tex;
            chartex = new Texture(pixmap1);
            pixmap1.dispose();
            pixmapOriginal2.dispose();
            //character = new TextureRegion(chartex,40,0,300,500);

            character = new TextureRegion(chartex,0,0,50,50);
            //character = new TextureRegion(chartex,0,0,100,100);
            //character = new TextureRegion(chartex,0,0,100,100);

    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isTouched()) {
            vec.x = Gdx.input.getX();
            vec.y = Gdx.input.getY();
            if (vec.x > button.getX() - btnWidth && vec.x < button.getX() + btnWidth &&
                    vec.y > button.getY() - btnHeight && vec.y < button.getY() + btnHeight) {

                //change scene
                System.out.println("Scene Changed\n");
                musicPlayer.stop();
                gsm.setState(GameSceneManager.Scene.MAIN);
            }

        }
    }

    @Override
    public void render() {
        float x = (Gdx.graphics.getWidth() + layout1.width) / 2;
        float y = Gdx.graphics.getHeight() / 10;
        ScreenUtils.clear(new Color(0, 0, 0f, 0f));
        batch.begin();
        //write text
        //font1.draw(batch, layout1, Gdx.graphics.getWidth()/2-layout1.width/2,y*6);
        //font2.draw(batch, layout2, Gdx.graphics.getWidth()/2-layout2.width/2,y*5);
        //font3.draw(batch, layout3, Gdx.graphics.getWidth()/2-layout3.width/2,y*1.5f);
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(character,0,0);
        batch.end();

        stage.act();
        stage.draw();

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
        stage.dispose();
    }
}
