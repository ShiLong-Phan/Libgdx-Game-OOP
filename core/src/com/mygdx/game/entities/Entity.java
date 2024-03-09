package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

    private final World world;
    private float x, y, width, height;
    private short cBits, mBits;
    private int isStatic;
    private boolean fixedRotation;
    private Body body;

    public Entity(final World world, float x, float y, float width,  float height, int isStatic, boolean fixedRotation, short cBits, short mBits, Body body){
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
        this.fixedRotation = fixedRotation;
        this.cBits = cBits;
        this.mBits = mBits;
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth() {
        this.width = width;
    }

    public float getY() {
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public short getcBits() {
        return cBits;
    }

    public void setCategoryBit(short cBits){
        this.cBits = cBits;
    }

    public short getmBits() {
        return mBits;
    }

    public void setMaskBit(short mBits){
        this.mBits = mBits;
    }

}
