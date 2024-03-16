package com.mygdx.game.GameEngine.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionHandler implements ContactListener {

    private static boolean puzzleCompletion = false;
    private static boolean onGround = false;
    private static boolean onReset = false;
    private static boolean collect = false;
    private static Body collectToken;
    private static Vector2 playerVelocity = new Vector2();

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() == "ground" || fb.getUserData() == "ground") {
            onGround = true;
        }

        if (fa.getUserData() == "reset" || fb.getUserData() == "reset") {
            onReset = true;
        }

        if (fa.getUserData() == "token" || fb.getUserData() == "token") {
            collect = true;
            if (fa.getUserData() == "token") collectToken = fa.getBody();
            if (fb.getUserData() == "token") collectToken = fb.getBody();
        }

        if (fa.getUserData() == "end" || fb.getUserData() == "end")
            System.out.println("Collision with ai moved entity Detected");

        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if ((fa.getUserData() == "sensor" || fb.getUserData() == "sensor") && (fa.getUserData() == "block" || fb.getUserData() == "block"))
            puzzleCompletion = true;

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "ground" || fb.getUserData() == "onGround")) {
            if(fa.getBody().getType() == BodyDef.BodyType.KinematicBody || fb.getBody().getType() == BodyDef.BodyType.KinematicBody)
                System.out.println("jump from moving platform");
            onGround = false;
            System.out.println("jump from moving platform");

        }
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "reset" || fb.getUserData() == "reset"))
            onReset = false;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "token" || fb.getUserData() == "token")) {
            if (fa.getUserData() == "player") {
                playerVelocity.x = fa.getBody().getLinearVelocity().x;
                playerVelocity.y = fa.getBody().getLinearVelocity().y;
            } else if (fb.getUserData() == "player") {
                playerVelocity.x = fb.getBody().getLinearVelocity().x;
                playerVelocity.y = fb.getBody().getLinearVelocity().y;
            }
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //continue movement after collection
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "token" || fb.getUserData() == "token")) {
            if (fa.getUserData() == "player") {
                fa.getBody().setLinearVelocity(playerVelocity.x, playerVelocity.y);
            } else if (fb.getUserData() == "player") {
                fb.getBody().setLinearVelocity(playerVelocity.x, playerVelocity.y);
            }
        }


    }

    public boolean getPuzzleCompletion() {
        return puzzleCompletion;
    }


    public boolean getOnGround() {
        return onGround;
    }


    public boolean getOnReset() {
        boolean temp = onReset;
        onReset = false;
        return temp;
    }

    public void setOnGround(boolean onGround){this.onGround = onGround;}

    public Body getOnCollect() {
        if (collect) {
            return collectToken;
        }
        return null;
    }


}
