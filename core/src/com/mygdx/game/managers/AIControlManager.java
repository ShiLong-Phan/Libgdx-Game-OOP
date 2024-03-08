package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.Entity;

import java.util.ArrayList;

public class AIControlManager {

    public AIControlManager(){}

    public void moveBody(ArrayList<Entity> kinematicEntities){
        //for loop to loop through array list of kinematic body
        //move body from in one direction from original to target spot
        //change direction
        //move back to original spot
    }


    public void showEndPoint(ArrayList<Entity> kinematicEntities, float yLevel){
        for(Entity b: kinematicEntities){
            if(b.getBody().getFixtureList().get(0).getUserData() == "end"){
                if (b.getBody().getPosition().y <= yLevel) {
                    System.out.println("Moving AI Controlled Body");
                    b.getBody().setLinearVelocity(0, 2);
                }
                else b.getBody().setLinearVelocity(0, 0);
            }
        }
    }

}
