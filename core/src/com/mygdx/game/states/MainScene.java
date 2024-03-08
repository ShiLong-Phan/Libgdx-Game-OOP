package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.CollisionHandler;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameSceneManager;
import com.mygdx.game.managers.IOManager;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.TiledObjectRender;

public class MainScene extends GameScene {

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body playerBody, platform, platform2, border1, border2, border3, border4, platform3, dynamicBox, endPlatform, movingPlatform;
    private CollisionHandler collisionHandler;
    private Player player;
    private EntityManager eManager;
    private IOManager ioManager;
    private float accumulator = 0;
    private boolean restart = false;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private TiledObjectRender tiledObjectRenderer;

    public MainScene(GameSceneManager gsm) {
        super(gsm);

        //music
        super.playGameMusic();

        this.eManager = gsm.geteManager();
        this.ioManager = gsm.getIOManager();
        world = new World(new Vector2(0, -9f), false); // y is gravity -10f for reallife
        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);
        b2dr = new Box2DDebugRenderer(
                /*drawBodies*/true,
                /*drawJoints*/false,
                /*drawAABBs*/false,
                /*drawInactiveBodies*/false,
                /*drawVelocities*/false,
                /*drawContacts*/false
        );

        System.out.println("Initialize Entity Manager");

        player = eManager.createPlayer(world, 200, 300, 32, 32, Constants.BIT_PLAYER, (short) (Constants.BIT_WALL | Constants.BIT_BLOCK | Constants.BIT_END));
        playerBody = player.getBody();
        eManager.addPlayer(player);

        System.out.println("Initialize Entity Builder");

        //movingPlatform = platformBuilder.createKinematicBody(world, 415, 150, 255, 150, true, Constants.BIT_WALL, (short) (Constants.BIT_PLAYER | Constants.BIT_BLOCK), null);
        dynamicBox = eManager.createDynamicEntity(world, 268.5f, 200, 30, 30, true, Constants.BIT_BLOCK, (short) (Constants.BIT_PLAYER | Constants.BIT_WALL | Constants.BIT_WALL), "block");
        endPlatform = eManager.createKinematicEntity(world, 525f, 50, 30, 30, true, Constants.BIT_END, Constants.BIT_PLAYER, "end");
        //border to box the game
        eManager.createBorders(world);

        map = new TmxMapLoader().load("maps/map1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 1/ Application.SCALE);
        tmr.setView(camera);
        tiledObjectRenderer = new TiledObjectRender();
        tiledObjectRenderer.parseTiledObjectLayer(world, map.getLayers().get(1).getObjects());
    }

    @Override
    public void update(float delta) {
        world.step(1 / 60f, 6, 2); //just use 6 and 2
        //player.inputUpdate();

        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);

        eManager.update();
        accumulator += delta;
        if (!collisionHandler.getLevelCompletion() && Gdx.input.isKeyJustPressed(Input.Keys.R) && accumulator > 0.5) {
            gsm.setState(GameSceneManager.State.MAIN);
        }

        if (collisionHandler.getLevelCompletion() && Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            musicPlayer.stop();
            gsm.setState(GameSceneManager.State.END);
        }

    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 1f));
        tmr.render();
        update(Gdx.graphics.getDeltaTime());
        b2dr.render(world, camera.combined.scl(Constants.PPM));

    }

    @Override
    public void dispose() {

        System.out.println("Scene Disposed");
        world.dispose();
        b2dr.dispose();
        eManager.dispose();
        tmr.dispose();
        map.dispose();
    }




}
