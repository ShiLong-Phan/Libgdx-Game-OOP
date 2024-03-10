package com.mygdx.game.interfaces;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.entities.Entity;

public interface entityBuilder {

    public Entity createEntity();
    public FixtureDef createKinematicBody();
    public FixtureDef createStaticBody();
    public FixtureDef createDynamicBody();

}
