package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class kinematicEntity extends Entity{

    public kinematicEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits,mBits, body);
    }

    public kinematicEntity(final World world, Shape shape, short cBits, short mBits, Body body, String userdata) {
        super(world, shape, 2, true,cBits,mBits, body, userdata);
    }
    public void render(SpriteBatch batch){}

}
