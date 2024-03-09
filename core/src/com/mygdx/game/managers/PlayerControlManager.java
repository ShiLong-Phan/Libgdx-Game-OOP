package com.mygdx.game.managers;

import com.mygdx.game.entities.Entity;

import java.util.ArrayList;

public class PlayerControlManager {
    GameSceneManager gsm;
    public PlayerControlManager(GameSceneManager gsm){
        this.gsm = gsm;
    }
    public void PlayerUpdate(ArrayList<Entity> players) {
        int horizontalForce = 0, verticalForce = 0;
        if (gsm.getIOManager().moveLeft()) { //move left
            System.out.println("Player Move Left");
            horizontalForce -= 1;
        }
        if (gsm.getIOManager().moveRight()) { //move right
            System.out.println("Player Move Right");
            horizontalForce += 1;
        }
        if (gsm.getIOManager().moveUp())  {
            System.out.println("Player Move Up");
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i) == null) continue;
                else {
                    if(players.get(i).getBody().getLinearVelocity().y == 0)
                        players.get(i).getBody().applyForceToCenter(0, 75, false);
                }
            }
        }
        //dont change y cuz its affected by gravity;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == null) continue;
            else {
                players.get(i).getBody().setLinearVelocity(horizontalForce * 4, players.get(i).getBody().getLinearVelocity().y);
            }
        }
    }
}
