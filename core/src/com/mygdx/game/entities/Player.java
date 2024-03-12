package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.EntityManager;

import static com.mygdx.game.utils.Constants.PPM;

public class Player extends Entity{
    private int Currency;
    private Texture tex;
    private Pixmap pixmap;

    private Body body;


    public Player(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits, mBits, body);

        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/char.png"));
        pixmap = new Pixmap((int) width, (int) height,pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,0,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        tex = new Texture(pixmap);
        super.setTex(tex);
        pixmap.dispose();
        pixmapOriginal.dispose();
    }


    public void render(SpriteBatch batch){
        batch.draw(super.getTex(), super.getBody().getPosition().x * PPM - tex.getWidth()/2,
                super.getBody().getPosition().y * PPM - tex.getHeight()/2);
    }

}
