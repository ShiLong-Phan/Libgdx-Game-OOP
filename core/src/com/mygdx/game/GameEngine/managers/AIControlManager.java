package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameEngine.entities.Entity;

import java.util.ArrayList;

public class AIControlManager {

    public AIControlManager() {
    }

    private float accumulator = 0;
    private int direction = -1;
    public void moveBody(ArrayList<Entity> kinematicEntities) {
        accumulator += Gdx.graphics.getDeltaTime();
        int size = kinematicEntities.size();
        for (Entity e : kinematicEntities) {
            if (size % 2 == 0)direction = -direction;
            if (e.getEntityData() == "ground") {
                e.getBody().setLinearVelocity(2 * direction, 0);
            }
            if (e.getEntityData() == "token") {
                e.getBody().setLinearVelocity( 0, (float).5 * direction);
            }
            if (e.getEntityData() == "reset") {
                e.getBody().setLinearVelocity(0, (float).75 * direction);
            }
            if (accumulator > 1.5) {
                if (direction < 0) {
                    direction = 1;
                }
                else if (direction > 0) {
                    direction = -1;
                }
                accumulator = 0;
            }
        }
    }

    public void resetAll() {
        accumulator = 0;
        direction = -1;
    }


}
