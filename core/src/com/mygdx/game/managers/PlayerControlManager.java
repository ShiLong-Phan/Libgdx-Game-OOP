package com.mygdx.game.managers;

import com.mygdx.game.entities.Player;

import java.util.ArrayList;

public class PlayerControlManager {
    private final GameSceneManager gsm;


    public PlayerControlManager(GameSceneManager gsm) {
        this.gsm = gsm;
    }
    /*

    public void PlayerUpdate(ArrayList<Entity> players, float delta) {
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
            verticalForce +=1;
        }
        //dont change y cuz its affected by gravity;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == null) continue;
            else {
                // Dampening check
                if(horizontalForce != 0) {
                    players.get(i).getBody().setLinearVelocity(horizontalForce * 300 * delta, players.get(i).getBody().getLinearVelocity().y);
                }
                if(verticalForce != 0) {
                        players.get(i).getBody().setLinearVelocity(players.get(i).getBody().getLinearVelocity().x, verticalForce * 300 * delta);
                        System.out.println("Player Move Up");

                }
            }
        }

    }
    }*/

    public void PlayerUpdate(ArrayList<Player> players, float delta, boolean onGround) {
        int horizontalForce = 0, verticalForce = 0;

        if (gsm.getIOManager().moveLeft()) { //move left
            horizontalForce -= 1;
        }
        if (gsm.getIOManager().moveRight()) { //move right
            horizontalForce += 1;
        }
        if (gsm.getIOManager().moveUp()) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i) == null) continue;
                else {
                    if (onGround == true)
                        players.get(i).getBody().setLinearVelocity(players.get(i).getBody().getLinearVelocity().x, 6.56f);
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
