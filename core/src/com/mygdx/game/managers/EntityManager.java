package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.*;

import java.util.ArrayList;
import java.util.Stack;

import static com.mygdx.game.utils.Constants.PPM;

public class EntityManager {
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
        System.out.println("Player Entity Created");

        return player;
    }

    public Body reCreatePlayer(Entity player) {
        this.RemovalStack.push(player);
        this.player.remove(player);
        return createPlayer(player.getWorld(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                player.getcBits(), player.getmBits()).getBody();
    }


    public void update(float delta) {
        gsm.getPlayerControlManager().PlayerUpdate(player, delta, collisionManager.getOnGround());

        if (delta >= 0) {
            if (collisionManager.getIfReset()) {
                for (int i = 0; i < player.size(); i++) {
                    if (player.get(i) != null) {
                        reCreatePlayer(player.get(i));
                        while (!RemovalStack.empty()) {
                            player.get(i).getWorld().destroyBody(RemovalStack.pop().getBody());
                            System.out.println("deleted");
                        }
                    }
                }
            }
        }


        aiManager.moveBody(kinematicEntities);
        if (collisionManager.checkCollision())
            aiManager.showEndPoint(kinematicEntities, 4.5f);
    }


}
