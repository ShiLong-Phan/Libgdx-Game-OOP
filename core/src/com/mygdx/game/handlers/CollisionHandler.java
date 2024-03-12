package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionHandler implements ContactListener {

    private static boolean puzzleCompletion = false,  completion = false;
    private static boolean onGround = false;
    private static boolean onReset = false;
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa.getUserData() == "ground" || fb.getUserData() == "ground"){
            onGround = true;
        }

        if(fa.getUserData() == "reset" || fb.getUserData() == "reset"){
            onReset = true;
        }

        if(fa.getUserData() == "end" || fb.getUserData() == "end")
            System.out.println("Collision with ai moved entity Detected");

        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if ((fa.getUserData() == "sensor" || fb.getUserData() == "sensor") && (fa.getUserData() == "block" || fb.getUserData() == "block"))  puzzleCompletion = true;
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "end" || fb.getUserData() == "end"))  completion = true;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "end" || fb.getUserData() == "end"))  completion = false;
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "ground" || fb.getUserData() == "onGround"))  onGround = false;
        if ((fa.getUserData() == "player" || fb.getUserData() == "player") && (fa.getUserData() == "reset" || fb.getUserData() == "reset"))  onReset = false;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean getPuzzleCompletion() {return puzzleCompletion;}
    public boolean getLevelCompletion() {return completion;}
    public boolean getOnGround() {return onGround;}
    public void setOnGround(boolean ground) {onGround = ground;}
    public boolean onReset() {
        boolean temp = onReset;
        onReset = false;
        return temp;
    }

}
