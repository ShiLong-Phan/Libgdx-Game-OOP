package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.GameEngine.managers.GameSceneManager;

import static com.mygdx.game.GameEngine.utils.Constants.tmr;
import static com.mygdx.game.GameEngine.utils.Constants.tokenImages;

public class Application extends ApplicationAdapter {


    //application titles and measurements
    public static final String TITLE = "NutriQuest";
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

        //add immages for collection
        tokenImages.add("sprites/apple.png");
        tokenImages.add("sprites/watermelon.png");
        tokenImages.add("sprites/carrot.png");
        tokenImages.add("sprites/celery.png");


        //set the files in lifecycle so that there is no errors when loading them in the game scenes
        tmr = new OrthogonalTiledMapRenderer[3];

        //lvl1
        TiledMap map1 = new TmxMapLoader().load("maps/map1.tmx");
        tmr[0] = new OrthogonalTiledMapRenderer(map1, .5f);
        tmr[0].setView(camera);

        //lvl2
        TiledMap map2 = new TmxMapLoader().load("maps/map2.tmx");
        tmr[1] = new OrthogonalTiledMapRenderer(map2, .5f);
        tmr[1].setView(camera);


        //lvl3
        TiledMap map3 = new TmxMapLoader().load("maps/map3.tmx");
        tmr[2] = new OrthogonalTiledMapRenderer(map3, .5f);
        tmr[2].setView(camera);
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
        for(int i = 0; i < tmr.length; i++){
            tmr[i].dispose();
        }
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
