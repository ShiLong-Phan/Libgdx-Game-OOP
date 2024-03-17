package com.mygdx.game.GameEngine.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLayer.entities.kinematicEntity;

import static com.mygdx.game.GameEngine.utils.Constants.PPM;

public abstract class Entity {

    protected final World world;
    protected float x, y, width, height;
    protected short cBits, mBits;
    protected int isStatic;
    protected boolean fixedRotation;
    protected Body body;
    protected Shape shape;
    protected String entityData;
    protected Texture tex;

    public Entity(final World world, float x, float y, float width, float height, int isStatic, boolean fixedRotation, short cBits, short mBits, Body body, String userdata) {
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
        this.entityData = userdata;
    }

    public Entity(final World world, Shape shape, int isStatic, boolean fixedRotation, short cBits, short mBits, Body body, String userdata) {
        this.world = world;
        this.shape = shape;
        this.entityData = userdata;
        this.isStatic = isStatic;
        this.fixedRotation = fixedRotation;
        this.cBits = cBits;
        this.mBits = mBits;
        this.body = body;
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
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

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public short getcBits() {
        return cBits;
    }

    public void setCategoryBit(short cBits) {
        this.cBits = cBits;
    }

    public short getmBits() {
        return mBits;
    }

    public void setMaskBit(short mBits) {
        this.mBits = mBits;
    }

    public String getEntityData() {
        return entityData;
    }

    public void setEntityData(String entityData) {
        this.entityData = entityData;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void disposeTexture() {
        tex.dispose();
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public Texture getTex() {
        return tex;
    }

    public abstract void render(SpriteBatch batch);

}
