package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.*;
import com.mygdx.game.interfaces.entityBuilder;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.TiledObjectRender;

import java.util.ArrayList;
import java.util.Stack;

import static com.mygdx.game.utils.Constants.PPM;
import static com.mygdx.game.utils.TiledObjectRender.createPolygon;
import static com.mygdx.game.utils.TiledObjectRender.createRectangle;

public class EntityManager implements entityBuilder {
    private static ArrayList<Entity> player = new ArrayList<>();
    private static ArrayList<Entity> staticEntities = new ArrayList<>();
    private static ArrayList<Entity> kinematicEntities = new ArrayList<>();
    private static ArrayList<Entity> dynamicEntities = new ArrayList<>();
    private static Stack<Entity> RemovalStack = new Stack<>();
    private static AIControlManager aiManager;
    private static CollisionManager collisionManager;
    private final GameSceneManager gsm;

    public EntityManager(GameSceneManager gsm) {

        this.gsm = gsm;
        aiManager = new AIControlManager();
        collisionManager = new CollisionManager();
    }

    public ArrayList<Entity> getStaticBody() {
        return staticEntities;
    }

    public ArrayList<Entity> getKinematicBody() {
        return kinematicEntities;
    }

    public ArrayList<Entity> getDynamicBody() {
        return dynamicEntities;
    }

    public ArrayList<Entity> getPlayer() {
        return player;
    }

    public void addStaticEntity(Entity entity) {
        staticEntities.add(entity);
    }

    public void addKinematicEntity(Entity entity) {
        kinematicEntities.add(entity);
    }

    public void addDynamicEntity(Entity entity) {
        dynamicEntities.add(entity);
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
        } else if (dynamicEntities.contains(entity)) {
            dynamicEntities.remove(entity);
            RemovalStack.push(entity);
        }
    }

    public Stack<Entity> getRemovalStack() {
        return RemovalStack;
    }


    public void dispose() {
        player.clear();
        staticEntities.clear();
        dynamicEntities.clear();
        kinematicEntities.clear();
        System.out.println("Entities Disposed");
    }

    public void parseTileLayerEntities(final World world, MapObjects objects, int layer) {
        for (MapObject object : objects) {
            Shape shape;

            if (object instanceof PolylineMapObject) {
                shape = TiledObjectRender.createPolyline((PolylineMapObject) object);
            } else if (object instanceof RectangleMapObject) {
                shape = createRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = createPolygon((PolygonMapObject) object);
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
                createKinematicEntity(world, shape, Constants.BIT_WALL, Constants.BIT_PLAYER, userdata);

            }

            shape.dispose();

        }
    }

    //map render entity creation
    public void createStaticEntity(final World world, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0f;
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        bdef.type = BodyDef.BodyType.StaticBody;
        fixtureDef.filter.categoryBits = cBits;
        fixtureDef.filter.maskBits = mBits;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(userdata);

        staticEntity sEntity = new staticEntity(world, shape, cBits, mBits, body, userdata);
        staticEntities.add(sEntity);
    }

    public void createDynamicEntity(final World world, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0f;
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        bdef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef.filter.categoryBits = cBits;
        fixtureDef.filter.maskBits = mBits;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(userdata);

        dynamicEntity dEntity = new dynamicEntity(world, shape, cBits, mBits, body, userdata);
        dynamicEntities.add(dEntity);
    }

    public void createKinematicEntity(final World world, Shape shape, short cBits, short mBits, String userdata) {
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0f;
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        bdef.type = BodyDef.BodyType.KinematicBody;
        fixtureDef.filter.categoryBits = cBits;
        fixtureDef.filter.maskBits = mBits;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(userdata);

        kinematicEntity kEntity = new kinematicEntity(world, shape, cBits, mBits, body, userdata);
        kinematicEntities.add(kEntity);
    }


    //creating custom entities
    public Body createBody(final World world, float x, float y, float width, float height,
                           int isStatic, boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM); //set position

        if (isStatic == 0)
            def.type = BodyDef.BodyType.StaticBody;
        else if (isStatic == 1)
            def.type = BodyDef.BodyType.DynamicBody;
        else
            def.type = BodyDef.BodyType.KinematicBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); //if getting box2d units divide if giving thn multiply

        //no friction so cant climb walls
        FixtureDef fdef = new FixtureDef();
        if (isStatic == 1) {
            fdef.restitution = .2F;
            fdef.friction = 0.2f;
        } else fdef.friction = 0.0f;
        fdef.shape = shape;
        fdef.density = 1.0f;
        fdef.filter.categoryBits = cBits; //is a
        fdef.filter.maskBits = mBits; // collides with

        Body bod = world.createBody(def);
        if (userdata == null)
            bod.createFixture(fdef).setUserData(this);
        else
            bod.createFixture(fdef).setUserData(userdata);

        return bod;
    }


    public Body createKinematicEntity(final World world, float x, float y, float width, float height
            , boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM); //set position


        def.type = BodyDef.BodyType.KinematicBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); //if getting box2d units divide if giving thn multiply

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

        kinematicEntity kbod = new kinematicEntity(world, x, y, width, height, cBits, mBits, bod);

        addKinematicEntity(kbod);

        System.out.println("Kinematic Body Created");

        return bod;
    }

    public Body createStaticEntity(final World world, float x, float y, float width, float height,
                                   boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM); //set position

        def.type = BodyDef.BodyType.StaticBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); //if getting box2d units divide if giving thn multiply

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

        staticEntity sbod = new staticEntity(world, x, y, width, height, cBits, mBits, bod);
        addStaticEntity(sbod);

        System.out.println("Static Body Created");

        return bod;
    }

    public Body createDynamicEntity(final World world, float x, float y, float width, float height,
                                    boolean fixedRotation, short cBits, short mBits, String userdata) {

        //define physical quality friction,initial position, moves or no etc
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM); //set position

        def.type = BodyDef.BodyType.DynamicBody;

        def.fixedRotation = fixedRotation; //if false will rotate after being interacted

        //give shape box2d works from middle thus /2
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); //if getting box2d units divide if giving thn multiply

        //no friction so cant climb walls
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1.0f;
        fdef.friction = 10f;
        fdef.restitution = 0.15f;
        fdef.filter.categoryBits = cBits; //is a
        fdef.filter.maskBits = mBits; // collides with

        Body bod = world.createBody(def);
        if (userdata == null)
            bod.createFixture(fdef).setUserData(this);
        else
            bod.createFixture(fdef).setUserData(userdata);

        dynamicEntity dbod = new dynamicEntity(world, x, y, width, height, cBits, mBits, bod);

        addDynamicEntity(dbod);

        System.out.println("Dynamic Body Created");

        return bod;
    }


    //player creation
    public Player createPlayer(final World world, float x, float y, float width, float height, short cBits, short mBits) {
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM);
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); //if getting box2d units divide if giving thn multiply

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0.0f;
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

    //upon landing in reset zone recreatePlayer at starting location
    private void recreatePlayer(Entity player) {
        this.RemovalStack.push(player);
        this.player.remove(player);
        createPlayer(player.getWorld(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                player.getcBits(), player.getmBits()).getBody();
    }

    public void update(float delta, SpriteBatch batch) {
        gsm.getPlayerControlManager().PlayerUpdate(player, delta, collisionManager.getOnGround());
        if (delta >= 0) {
            if (collisionManager.getIfReset()) {
                for (int i = 0; i < player.size(); i++) {
                    if (player.get(i) != null) {
                        recreatePlayer(player.get(i));
                        while (!RemovalStack.empty()) {
                            player.get(i).getWorld().destroyBody(RemovalStack.pop().getBody());
                            System.out.println("deleted");
                        }
                    }
                }
            }
        }
        for (Entity e: player){
            if(e != null)
                e.render(batch);
        }

        aiManager.moveBody(kinematicEntities);
        if (collisionManager.checkCollision())
            aiManager.showEndPoint(kinematicEntities, 4.5f);
    }


}
