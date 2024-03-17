package com.mygdx.game.GameLayer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.entities.Entity;

import static com.mygdx.game.GameEngine.utils.Constants.PPM;
import static com.mygdx.game.GameEngine.utils.Constants.tokenImages;

public class kinematicEntity extends Entity {

    public kinematicEntity(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body, String userdata) {
        super(world, x, y, width, height, 2, true, cBits, mBits, body, userdata);


        //random image
        int random = (int) (Math.random() * tokenImages.size());

        //resize and set tex
        //resize img
        Pixmap pixmap;
        Pixmap pixmapOriginal;
        if(userdata == "token") {
            pixmapOriginal = new Pixmap(Gdx.files.internal(tokenImages.get(random)));
            pixmap = new Pixmap((int) width / 2, (int) height / 2, pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture tex;
            tex = new Texture(pixmap);
            super.setTex(tex);
            pixmap.dispose();
            pixmapOriginal.dispose();
        }else if (userdata == "ground") {
            super.setX(x/PPM);
            super.setY(y/PPM);
            pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/platform.png"));
            pixmap = new Pixmap((int) width, (int) height, pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture tex;
            tex = new Texture(pixmap);
            super.setTex(tex);
            pixmap.dispose();
            pixmapOriginal.dispose();
        } else if (userdata == "reset") {
            pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/burger.png"));
            pixmap = new Pixmap((int) width / 2, (int) height / 2, pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture tex;
            tex = new Texture(pixmap);
            super.setTex(tex);
            pixmap.dispose();
            pixmapOriginal.dispose();
        }


    }

    public kinematicEntity(final World world, Vector2 vec, Shape shape, short cBits, short mBits, Body body, String userdata) {
        super(world, shape, 2, true, cBits, mBits, body, userdata);
    }


    @Override
    public void render(SpriteBatch batch) {
        if (super.getTex() != null) {
            if (this instanceof kinematicEntity) {
                if (super.getEntityData() == "ground") {
                    batch.draw(tex, body.getPosition().x * PPM - tex.getWidth() / 2 - 1,
                            body.getPosition().y * PPM - tex.getHeight() / 2);
                } else if (super.getEntityData() == "token") {
                    super.setX(x + body.getLinearVelocity().x /PPM / 2);
                    super.setY(y + body.getLinearVelocity().y /PPM / 2);
                    batch.draw(tex, x * PPM - tex.getWidth() / 2 - 1,
                            y * PPM - tex.getHeight() / 2);
                } else if (super.getEntityData() == "reset") {
                    super.setX(x + body.getLinearVelocity().x / PPM / 2);
                    super.setY(y + body.getLinearVelocity().y / PPM / 2);
                    batch.draw(tex, x * PPM - tex.getWidth() / 2 - 1,
                            y * PPM - tex.getHeight() / 2);
                }
            }
        }
    }
}
