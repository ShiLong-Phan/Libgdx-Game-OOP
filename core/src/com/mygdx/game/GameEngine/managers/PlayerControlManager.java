package com.mygdx.game.GameEngine.managers;

import com.mygdx.game.GameLayer.entities.Player;

import java.util.ArrayList;

public class PlayerControlManager {
    private final GameSceneManager gsm;


    public PlayerControlManager(GameSceneManager gsm) {
        this.gsm = gsm;
    }


    public void PlayerUpdate(ArrayList<Player> players, float delta, boolean onGround) {
        int horizontalForce = 0;

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
                    if (onGround == true) {
                        players.get(i).getBody().setLinearVelocity(players.get(i).getBody().getLinearVelocity().x, 6.56f);
                        gsm.getEntityManager().getCollisionManager().getCollisionHandler().setOnGround(false);
                    }
                }
            }
        }
        //dont change y cuz its affected by gravity;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == null) continue;
            else {
                players.get(i).getBody().setLinearVelocity(horizontalForce * 4, players.get(i).getBody().getLinearVelocity().y);
                if (horizontalForce <0) players.get(i).setFlip(true);
                if (horizontalForce >0) players.get(i).setFlip(false);
            }
        }
    }
}
