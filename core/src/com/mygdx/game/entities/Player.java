package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.EntityManager;

public class Player extends Entity{
    private int Currency;
    private int Stage;


    private Body body;


    public Player(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits, mBits, body);
    /*
        Texture texture = new Texture(Gdx.files.internal("sprites/char.png"));
        TextureRegion[] textureRegion = new TextureRegion[4];
        for(int i = 0; i< textureRegion.length; i++){
            textureRegion[i] = new TextureRegion(texture,x,y, width, height);
        }
        this.body = body;

    }
     */
    }

}
