package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.EntityManager;

public class Player extends Entity{
    private int Currency;
    private int Stage;


    private Body body;


    public Player(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits,mBits);

        this.body = body;
    }



    @Override
    public Body getBody() {
        return body;
    }


}
