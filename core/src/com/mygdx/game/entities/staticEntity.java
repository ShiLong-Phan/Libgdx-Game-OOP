package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class staticEntity extends Entity {
    private Body body;

    public staticEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 0, true,cBits,mBits, body);
    }

    public staticEntity(final World world, Shape shape, short cBits, short mBits, Body body, String userdata) {
        super(world, shape, 0, true,cBits,mBits, body, userdata);
    }

}
