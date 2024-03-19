package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.entities.Entity;
import com.mygdx.game.GameEngine.scene.GameScene;
import com.mygdx.game.GameLayer.entities.Player;
import com.mygdx.game.GameEngine.handlers.CollisionHandler;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.utils.Constants;

public class level3 extends GameScene {

    private World world;
    private Player player;
    private float accumulator = 0;

    private TiledMap map;

    private Texture backgroundTexture;

    //colelctible
    private int collectable = 0;

    public level3(GameSceneManager gsm) {
        super(gsm);
        //music
        super.playGameMusic();

        //create box2d world and set collision handler
        world = new World(new Vector2(0, -9f), false); // y is gravity -10f for reallife
        world.setContactListener(gsm.getEntityManager().getCollisionManager().getCollisionHandler());


        player = gsm.getEntityManager().createPlayer(world, 25, 100, 20, 23, Constants.BIT_PLAYER, (short) (Constants.BIT_WALL | Constants.BIT_ENEMY));

        //resize bg image
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/Plain Background.png"));
        pixmap = new Pixmap( (int) (Gdx.graphics.getWidth()/Application.SCALE), (int) (Gdx.graphics.getHeight()/Application.SCALE), pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmapOriginal.dispose();

        map = new TmxMapLoader().load("maps/map3.tmx");
        for (int i = 0; i < map.getLayers().size() - 1; i++) {
            gsm.getEntityManager().parseTileLayerEntities(world, map.getLayers().get(i + 1).getObjects(), i);
        }


        //add moving platform
        Entity movingPlatform = gsm.getEntityManager().createKinematicEntity(world, 385,175 ,50,16, true, Constants.BIT_WALL, (short) (Constants.BIT_PLAYER | Constants.BIT_WALL), "ground");

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
        //if r key is pressed restart scene
        if (gsm.getIOManager().restartStage() && accumulator > 0.5) {
            musicPlayer.stop();
            gsm.setState(GameSceneManager.Scene.LEVEL3);
        }
        //if escape is pressed go back to level select
        if (gsm.getIOManager().backToLevelSelect()  || player.getLives() == 0){
            musicPlayer.stop();
            gsm.setState(GameSceneManager.Scene.LEVELSELECT);
        }

        //for testing.
        if(Gdx.input.isKeyPressed(Input.Keys.U) && accumulator > .3){
            musicPlayer.stop();
            gsm.setState(GameSceneManager.Scene.LEVEL2);
        }

    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 1f));

        update(Gdx.graphics.getDeltaTime());
        Constants.tmr[2].render();
        if (player.getTokens() == 0 && accumulator > 0.5) {
            musicPlayer.stop();
            gsm.setState(GameSceneManager.Scene.END);
        }


    }

    @Override
    public void dispose() {

        System.out.println("Scene Disposed");
        gsm.getEntityManager().dispose();
        map.dispose();
        world.dispose();
    }


}
