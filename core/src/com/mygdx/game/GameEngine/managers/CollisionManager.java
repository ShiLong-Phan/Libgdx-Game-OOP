package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameEngine.handlers.CollisionHandler;

public class CollisionManager {

    private CollisionHandler collisionHandler;
    public CollisionManager(){
        collisionHandler = new CollisionHandler();
    }


    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public void setCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    public boolean checkCollision(){
        return collisionHandler.getPuzzleCompletion();
    }

    public boolean getOnGround(){
        return collisionHandler.getOnGround();
    }
    public boolean getIfReset(){return collisionHandler.getOnReset();}
    public Body getOnCollection(){
        if(collisionHandler.getOnCollect() != null) {
            return collisionHandler.getOnCollect();
        }
        return null;
    }
}
