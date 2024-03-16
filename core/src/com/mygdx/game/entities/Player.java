package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.utils.Constants.PPM;


public class Player extends Entity{
    private int tokens;
    private int lives;
    private Texture[] characterSprite;


    public Player(final World world, float x, float y, float width, float height, short cBits, short mBits, Body body) {
        super(world, x,y,width,height, 2, true,cBits, mBits, body, "player");
        characterSprite = new Texture[4];

        lives = 3;

        //resize img
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("sprites/human.png"));
        pixmap = new Pixmap((int) (width* 1), (int) (height* 1),pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal,0,0,pixmapOriginal.getWidth(),pixmapOriginal.getHeight(),0,0, pixmap.getWidth(), pixmap.getHeight());
        Texture tex;
        tex = new Texture(pixmap);
        characterSprite[0] = tex;
        //characterSprite[0] = new TextureRegion(tex,0,0,width,height);
        //tex = characterSprite[0].getTexture();
        super.setTex(tex);
        pixmap.dispose();
        pixmapOriginal.dispose();
    }


    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void render(SpriteBatch batch){
        Sprite s = new Sprite(characterSprite[0]);
        s.flip(true,false);
        s.setPosition(super.getBody().getPosition().x * PPM - s.getWidth() / 2,
                super.getBody().getPosition().y * PPM - s.getHeight() / 2);
        s.draw(batch);
    }


}
