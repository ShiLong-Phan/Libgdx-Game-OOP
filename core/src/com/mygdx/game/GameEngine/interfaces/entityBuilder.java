package com.mygdx.game.GameEngine.interfaces;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public interface entityBuilder {

    public void parseTileLayerEntities(final World world, MapObjects objects, int layer);

    public void createStaticEntity(final World world, Shape shape, short cBits, short mBits, String userdata);
    public void createKinematicEntity(final World world, Vector2 position, float width, float height, Shape shape, short cBits, short mBits, String userdata);
    public Body buildBox2dBody(final World world, BodyDef.BodyType bodyType, Shape shape, short cBits, short mBits, String userdata);
}
