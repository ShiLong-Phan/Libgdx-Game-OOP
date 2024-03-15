package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.Constants;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.tokenImages;

public class kinematicEntity extends Entity{

    public kinematicEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits,mBits, body);
    }

    public kinematicEntity(final World world, Vector2 vec, Shape shape, short cBits, short mBits, Body body, String userdata) {
        super(world, vec.x, vec.y, 38,38, 2, true,cBits,mBits, body);
        /*
        int random = (int) (Math.random() * Constants.tokenImages.size());
        //resize img
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/char.png"));

        //get body width & height
        int height,width;
        height = 38;
        width = 38;

        pixmap = new Pixmap((int) width, (int) height,pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,0,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        Texture tex;
        tex = new Texture(pixmap);
        super.setTex(tex);
        pixmap.dispose();
        pixmapOriginal.dispose();
         */

        //resize img
        Pixmap pixmap;

        //random image
        int random = (int) (Math.random() * tokenImages.size());

        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(tokenImages.get(random)));
        pixmap = new Pixmap((int) 19, (int) 19,pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,-20,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        Texture tex;
        tex = new Texture(pixmap);
        super.setTex(tex);
        pixmap.dispose();
        pixmapOriginal.dispose();
    }

}
