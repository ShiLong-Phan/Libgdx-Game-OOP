package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.managers.GameSceneManager;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.tokenImages;

public class Application extends ApplicationAdapter {

    private Boolean DEBUG = false;

    //application titles and measurements
    public static final String TITLE = "GAMETEST";
    public static final int V_WIDTH = 1440, V_HEIGHT = 960;
    public static final float SCALE = 2.0f;

    //can reuse for each new state
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameSceneManager gsm;

    @Override
    public void create() {
        System.out.println("Initializing Managers");
        //get height & width
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //batch
        batch = new SpriteBatch();

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);
        gsm = new GameSceneManager(this);
        System.out.println("Scene Manager Created");

        tokenImages.add("sprites/apple.png");
        tokenImages.add("sprites/banana.png");
        tokenImages.add("sprites/carrot.png");
        tokenImages.add("sprites/celery.png");
    }

    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render();
    }

    @Override
    public void resize(int width, int height) {
        gsm.resize((int) (width / SCALE), (int) (height / SCALE));
    }

    @Override
    public void dispose() {
        System.out.println("Managers Disposed");
        System.out.println("Lifecycle Ended");
        gsm.dispose();
        batch.dispose();

    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public GameSceneManager getGsm() {
        return gsm;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }



}
