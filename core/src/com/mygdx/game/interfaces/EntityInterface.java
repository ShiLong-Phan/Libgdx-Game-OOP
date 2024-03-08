package com.mygdx.game.interfaces;

import com.badlogic.gdx.physics.box2d.*;

public interface EntityInterface {
    Body createBody(World world, int x, int y, int width, int height, int isStatic);
    Body createBody(final World world, float x, float y, float width, float height,
                           int isStatic, boolean canRotate, short cBits, short mBits, String userdata);




}
