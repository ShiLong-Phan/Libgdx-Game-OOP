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
        for (int i = 0; i < size; i++) {
            if (kinematicEntities.get(i) == null)continue;
            if (kinematicEntities.get(i).getEntityData() == "ground") {
                kinematicEntities.get(i).getBody().setLinearVelocity(2 * direction, 0);
            }
            if (kinematicEntities.get(i).getEntityData() == "token") {
                kinematicEntities.get(i).getBody().setLinearVelocity( 0, (float).5 * direction);
            }
            if (kinematicEntities.get(i).getEntityData() == "reset") {
                kinematicEntities.get(i).getBody().setLinearVelocity(0, (float).4 * -direction);
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
