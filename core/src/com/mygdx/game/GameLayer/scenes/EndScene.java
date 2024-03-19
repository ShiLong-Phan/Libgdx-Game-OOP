package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.scene.GameScene;

public class EndScene extends GameScene {

    private Texture backgroundTexture;

    private float accumulator = 0;

    public EndScene(GameSceneManager gsm) {
        super(gsm);

        super.playStartEndMusic();

        //resize bg image
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/thank you page.png"));
        pixmap = new Pixmap(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmapOriginal.dispose();

        System.out.println("Scene Changed (LAST)\n");

    }

    @Override
    public void update(float delta) {
        accumulator += delta;
        //if any key is selected close the game
        if ((Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) && accumulator > 1) {
            //change scene
            System.out.println("Scene End\n");
            gsm.getApp().dispose();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0,0));
        batch.begin();
        batch.draw(backgroundTexture,0,0);
        batch.end();
    }

    @Override
    public void dispose() {
        System.out.println("Scene disposed");
        backgroundTexture.dispose();
    }
}