package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public class Player extends Entity{
    private int Currency;

    private Body body;


    public Player(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits, mBits, body);

        //resize img
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/apple.png"));
        pixmap = new Pixmap((int) width, (int) height,pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,0,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        Texture tex;
        tex = new Texture(pixmap);
        super.setTex(tex);
        pixmap.dispose();
        pixmapOriginal.dispose();
    }




}
