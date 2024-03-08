package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class dynamicEntity extends Entity{
    private Body body;

    public dynamicEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits,mBits);

        this.body = body;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
