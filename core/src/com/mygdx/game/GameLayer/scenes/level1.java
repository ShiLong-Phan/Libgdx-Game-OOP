package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.entities.Entity;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.scene.GameScene;
import com.mygdx.game.GameEngine.utils.Constants;
import com.mygdx.game.GameLayer.entities.Player;

import static com.mygdx.game.GameEngine.utils.Constants.tmr;

public class level1 extends GameScene {

    private World world;
    private Player player;
    private float accumulator = 0, accumulator2 = 0;

    //tiled map
    private TiledMap map;

    //background texture
    private Texture backgroundTexture;

    //colelctible
    private int collectable = 0;

    private Box2DDebugRenderer b2dr;

    public level1(GameSceneManager gsm) {
        super(gsm);

        //music
        gsm.getIOManager().playGameMusic();


        //create box2d world  and set collision handler
        world = new World(new Vector2(0, -9f), false); // y is gravity -10f for reallife
        world.setContactListener(gsm.getEntityManager().getCollisionManager().getCollisionHandler());

        //create player
        player = gsm.getEntityManager().createPlayer(world, 25, 100, 20, 23, Constants.BIT_PLAYER, (short) (Constants.BIT_WALL | Constants.BIT_ENEMY));

        //resize bg image
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/instructions.png"));
        pixmap = new Pixmap((int) (Gdx.graphics.getWidth() / Application.SCALE), (int) (Gdx.graphics.getHeight() / Application.SCALE), pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmapOriginal.dispose();

        map = tmr[0].getMap();
        for (int i = 0; i < map.getLayers().size() - 1; i++) {
            //skip layer 0 as it is just a tile layer with no objs
            gsm.getEntityManager().parseTileLayerEntities(world, map.getLayers().get(i + 1).getObjects(), i);
        }

        collectable = player.getTokens();
    }

    @Override
    public void update(float delta) {
        world.step(1 / 75f, 6, 2); //just use 6 and 2

        cameraUpdate();
        for (int i = 0; i < gsm.getEntityManager().getPlayer().size(); i++) {
            if (gsm.getEntityManager().getPlayer().get(i) != null) {
                player = gsm.getEntityManager().getPlayer().get(i);
                break;
            }
        }

        //set details
        layout.setText(super.font, collectable - player.getTokens() + "/" + collectable + "\nLives: " + player.getLives());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //draw background
        batch.draw(backgroundTexture, 0, 0);
        //write details
        super.font.draw(batch, layout, 20, Gdx.graphics.getHeight() / 2 - 20);
        //update all entities
        gsm.getEntityManager().update(delta, batch);
        batch.end();

        accumulator += delta;
        //if esc key pressed or lives == 0 back to level select
        if (gsm.getIOManager().backToLevelSelect() || player.getLives() == 0) {
            gsm.getIOManager().stopMusic();
            gsm.setState(GameSceneManager.Scene.LEVELSELECT);
        }

        if (gsm.getIOManager().restartStage()){
            gsm.getIOManager().stopMusic();
            gsm.setState(GameSceneManager.Scene.LEVEL1);
        }

        //for testing purposes go next stage
        if (Gdx.input.isKeyPressed(Input.Keys.U) && accumulator > .3) {
            gsm.getIOManager().stopMusic();
            gsm.setState(GameSceneManager.Scene.LEVEL2);
        }

        //if token 0 thn go next stage
        if (player.getTokens() == 0 && accumulator > 0.3) {
            gsm.getIOManager().stopMusic();
            gsm.setState(GameSceneManager.Scene.LEVEL2);
        }

    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 1f));

        //render sprites >> map >> box2d
        update(Gdx.graphics.getDeltaTime());

        //render first tiled map
        tmr[0].render();




    }

    @Override
    public void dispose() {
        System.out.println("Scene Disposed");
        world.dispose();
        gsm.getEntityManager().dispose();
        //map.dispose();

    }
}
