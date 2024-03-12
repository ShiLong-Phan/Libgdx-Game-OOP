package com.mygdx.game.interfaces;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public interface entityBuilder {

    public void parseTileLayerEntities(final World world, MapObjects objects, int layer);

    public void createKinematicEntity(final World world, Shape shape, short cBits, short mBits, String userdata);

    public void createStaticEntity(final World world, Shape shape, short cBits, short mBits, String userdata);

    public void createDynamicEntity(final World world, Shape shape, short cBits, short mBits, String userdata);

}
