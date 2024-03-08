package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class kinematicEntity extends Entity{
    private Body body;

    public kinematicEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 1, true,cBits,mBits);

        this.body = body;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
