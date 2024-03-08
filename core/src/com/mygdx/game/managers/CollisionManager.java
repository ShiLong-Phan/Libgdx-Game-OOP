package com.mygdx.game.managers;

import com.mygdx.game.handlers.CollisionHandler;

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

    public boolean getLevelEnd(){
        return collisionHandler.getLevelCompletion();
    }
}
