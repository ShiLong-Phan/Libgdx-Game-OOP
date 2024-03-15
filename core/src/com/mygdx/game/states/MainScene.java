package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
    private CollisionHandler collisionHandler;
    private Player player;
    private float accumulator = 0;
    private boolean restart = false;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Texture backgroundTexture;



    public MainScene(GameSceneManager gsm) {
        super(gsm);

        //music
        super.playGameMusic();

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


        player = gsm.geteManager().createPlayer(world, 25, 100, 20, 20, Constants.BIT_PLAYER, (short) (Constants.BIT_WALL | Constants.BIT_BLOCK | Constants.BIT_END));
        gsm.geteManager().addPlayer(player);

        map = new TmxMapLoader().load("maps/map1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 1/ Application.SCALE);
        tmr.setView(camera);
        //for(int i = 0; i < map.getLayers().size(); i++) {
        for(int i = 0; i < map.getLayers().size()-1; i++) {
            gsm.geteManager().parseTileLayerEntities(world, map.getLayers().get(i+1).getObjects(), i);
        }

        //resize bg image
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("skybackground.png"));
        pixmap = new Pixmap( Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(),pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,0,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmapOriginal.dispose();

    }

    @Override
    public void update(float delta) {
        world.step(1 / 75f, 6, 2); //just use 6 and 2
        //player.inputUpdate();

        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //draw background
        batch.draw(backgroundTexture,0,0);

        //update all entities
        gsm.geteManager().update(delta, batch);
        accumulator += delta;
        //if r key is pressed restart scene
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && accumulator > 0.5) {
            musicPlayer.stop();
            gsm.setState(GameSceneManager.State.MAIN);
        }
        //if stage completed go to next scene

        batch.end();
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 1f));

        update(Gdx.graphics.getDeltaTime());
        tmr.render();
        b2dr.render(world, camera.combined.scl(Constants.PPM));

    }

    @Override
    public void dispose() {

        System.out.println("Scene Disposed");
        world.dispose();
        b2dr.dispose();
        gsm.geteManager().dispose();
        tmr.dispose();
        map.dispose();
    }




}
