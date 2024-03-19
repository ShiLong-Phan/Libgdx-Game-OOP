package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameEngine.entities.*;
import com.mygdx.game.GameEngine.interfaces.entityBuilder;
import com.mygdx.game.GameEngine.utils.Constants;
import com.mygdx.game.GameEngine.utils.TiledObjectRender;
import com.mygdx.game.GameLayer.entities.Player;
import com.mygdx.game.GameLayer.entities.kinematicEntity;
import com.mygdx.game.GameLayer.entities.staticEntity;

import java.util.ArrayList;
import java.util.Stack;

public class EntityManager implements entityBuilder {
    private static ArrayList<Player> player = new ArrayList<>();
    private static ArrayList<Entity> staticEntities = new ArrayList<>();
    private static ArrayList<Entity> kinematicEntities = new ArrayList<>();
    private static Stack<Entity> RemovalStack = new Stack<>();
    private static AIControlManager aiManager;
    private static CollisionManager collisionManager;
    private final GameSceneManager gsm;
    private static EntityManager eManager;

    private EntityManager(GameSceneManager gsm) {
        this.gsm = gsm;
        aiManager = new AIControlManager();
        collisionManager = new CollisionManager();
    }

    public static EntityManager getEntityManager(GameSceneManager gsm){
        if(eManager == null){
            eManager = new EntityManager(gsm);
        }
        return eManager;
    }

    public ArrayList<Entity> getStaticBody() {
        return staticEntities;
    }

    public ArrayList<Entity> getKinematicBody() {
        return kinematicEntities;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }

    public void addStaticEntity(Entity entity) {
        staticEntities.add(entity);
    }

    public void addKinematicEntity(Entity entity) {
        kinematicEntities.add(entity);
    }


    public void addPlayer(Player player) {
        this.player.add(player);
    }

    public void removeBody(Entity entity) {
        if (staticEntities.contains(entity)) {
            staticEntities.remove(entity);
            RemovalStack.push(entity);
        } else if (kinematicEntities.contains(entity)) {
            kinematicEntities.remove(entity);
            RemovalStack.push(entity);
        }
    }

    public Stack<Entity> getRemovalStack() {
        return RemovalStack;
    }


    public void dispose() {
        player.clear();
        staticEntities.clear();
        kinematicEntities.clear();
        aiManager.resetAll();
        System.out.println("Entities Disposed");
    }

    public void parseTileLayerEntities(final World world, MapObjects objects, int layer) {
        int token = 0;
        for (MapObject object : objects) {
            Shape shape;

            if (object instanceof PolylineMapObject) {
                shape = TiledObjectRender.createPolyline((PolylineMapObject) object);
            } else if (object instanceof RectangleMapObject) {
                shape = TiledObjectRender.createRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = TiledObjectRender.createPolygon((PolygonMapObject) object);
            } else {
                continue;
            }


            String userdata = "";
            if (layer <= 2) {

                if (layer == 0) {
                    userdata = "ground";
                }
                if (layer == 1) {
                    userdata = "reset";
                }
                if (layer == 2) {
                    userdata = "terrain";
                }
                createStaticEntity(world, shape, Constants.BIT_WALL, Constants.BIT_PLAYER, userdata);

            } else if (layer == 3) {
                userdata = "token";
                token++;

                RectangleMapObject rectObj = (RectangleMapObject) object;
                Rectangle rect = rectObj.getRectangle();
                Vector2 size = new Vector2((rect.x + rect.width / 2) / 2 / Constants.PPM,
                        (rect.y + rect.height / 2) / 2 / Constants.PPM);
                createKinematicEntity(world, size, rect.width, rect.height, shape, Constants.BIT_WALL, Constants.BIT_PLAYER, userdata);
            } else if (layer == 4) {
                userdata = "reset";
                RectangleMapObject rectObj = (RectangleMapObject) object;
                Rectangle rect = rectObj.getRectangle();
                Vector2 size = new Vector2((rect.x + rect.width / 2) / 2 / Constants.PPM,
                        (rect.y + rect.height / 2) / 2 / Constants.PPM);
                System.out.println(size.x + "\t" + size.y);
                createKinematicEntity(world, size, rect.width, rect.height, shape, Constants.BIT_ENEMY, Constants.BIT_PLAYER, userdata);
            } else {

                continue;
            }

            shape.dispose();

        }
        if (layer == 3) {
            for (int i = 0; i < player.size(); i++) {
                if (player.get(i) != null)
                    player.get(i).setTokens(token);
            }
        }
    }

    //map render entity creation
    public void createStaticEntity(final World world, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        body = buildBox2dBody(world, BodyDef.BodyType.StaticBody, shape, cBits, mBits, userdata);

        staticEntity sEntity = new staticEntity(world, shape, cBits, mBits, body, userdata);
        staticEntities.add(sEntity);
    }

    public void createKinematicEntity(final World world, Vector2 position, float width, float height, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        body = buildBox2dBody(world, BodyDef.BodyType.KinematicBody, shape, cBits, mBits, userdata);
        kinematicEntity kEntity = new kinematicEntity(world, position.x, position.y, width, height, cBits, mBits, body, userdata);
        kinematicEntities.add(kEntity);
    }


    public Body buildBox2dBody(final World world, BodyDef.BodyType bodyType, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0f;
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        bdef.type = bodyType;
        fixtureDef.filter.categoryBits = cBits;
        fixtureDef.filter.maskBits = mBits;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(userdata);
        return body;
    }

    //player creation
    public Player createPlayer(final World world, float x, float y, float width, float height, short cBits, short mBits) {
        BodyDef def = new BodyDef();
        def.position.set(x / Constants.PPM, y / Constants.PPM);
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM); //if getting box2d units divide if giving thn multiply

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 1.0f;
        fdef.shape = shape;
        fdef.density = 1.0f;
        fdef.filter.categoryBits = cBits; //is a
        fdef.filter.maskBits = mBits; // collides with

        Body bod = world.createBody(def);
        bod.createFixture(fdef).setUserData("player");
        Player player = new Player(world, x, y, width, height, cBits, mBits, bod);

        this.player.add(player);

        return player;
    }

    public Entity createKinematicEntity(final World world, float x, float y, float width, float height
            , boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / Constants.PPM, y / Constants.PPM); //set position


        def.type = BodyDef.BodyType.KinematicBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM); //if getting box2d units divide if giving thn multiply

        //no friction so cant climb walls
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1.0f;
        fdef.friction = 1.0f;
        fdef.filter.categoryBits = cBits; //is a
        fdef.filter.maskBits = mBits; // collides with

        Body bod = world.createBody(def);
        if (userdata == null)
            bod.createFixture(fdef).setUserData(this);
        else
            bod.createFixture(fdef).setUserData(userdata);

        kinematicEntity kbod = new kinematicEntity(world, x, y, width, height, cBits, mBits, bod, userdata);

        addKinematicEntity(kbod);

        System.out.println("Kinematic Body Created");
        return kbod;

    }

    public Entity createStaticEntity(final World world, float x, float y, float width, float height,
                                     boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / Constants.PPM, y / Constants.PPM); //set position

        def.type = BodyDef.BodyType.StaticBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM); //if getting box2d units divide if giving thn multiply

        //no friction so cant climb walls
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1.0f;
        fdef.friction = 0.25f;
        fdef.filter.categoryBits = cBits; //is a
        fdef.filter.maskBits = mBits; // collides with

        Body bod = world.createBody(def);
        if (userdata == null) {
            bod.createFixture(fdef).setUserData(this);
        } else
            bod.createFixture(fdef).setUserData(userdata);

        staticEntity sbod = new staticEntity(world, x, y, width, height, cBits, mBits, bod, userdata);
        addStaticEntity(sbod);

        System.out.println("Static Body Created");
        return sbod;

    }


    //upon landing in reset zone recreatePlayer at starting location
    private void recreatePlayer(Player player) {
        this.RemovalStack.push(player);
        this.player.remove(player);
        createPlayer(player.getWorld(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                player.getcBits(), player.getmBits()).getBody();
        for (int i = 0; i < this.player.size(); i++) {
            if (this.player.get(i) != null) {
                this.player.get(i).setTokens(player.getTokens());
                this.player.get(i).setLives(player.getLives() - 1);
            }
        }
    }

    public void update(float delta, SpriteBatch batch) {
        //update movement
        gsm.getPlayerControlManager().PlayerUpdate(player, delta, collisionManager.getOnGround());
        //check if touched water if so recreate char
        if (collisionManager.getIfReset()) {
            for (int i = 0; i < player.size(); i++) {
                if (player.get(i) != null) {
                    recreatePlayer(player.get(i));
                    while (!RemovalStack.empty()) {
                        player.get(i).getWorld().destroyBody(RemovalStack.pop().getBody());
                    }
                }
            }
        }
        //check for colliding with fruits & veg if so thn reduce token to collect by 1
        if (collisionManager.getOnCollection() != null) {
            for (int i = 0; i < kinematicEntities.size(); i++) {
                if (kinematicEntities.get(i).getBody() == collisionManager.getOnCollection()) {
                    removeBody(kinematicEntities.get(i));
                    //reduce tokens to be collected by 1
                    for (int j = 0; j < player.size(); j++) {
                        if (player.get(j) != null) {
                            if (player.get(j).getTokens() != 0)
                                player.get(j).setTokens(player.get(j).getTokens() - 1);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        for (Entity e : kinematicEntities) {
            if (e.getTex() != null) {
                e.render(batch);

            }
        }
        for (Player e : player) {
            if (e != null)
                e.render(batch);
        }

        aiManager.moveBody(kinematicEntities);
        while (!RemovalStack.empty()) {
            try {
                player.get(0).getWorld().destroyBody(RemovalStack.pop().getBody());
            } catch (Exception e) {
            }
        }
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }


}
