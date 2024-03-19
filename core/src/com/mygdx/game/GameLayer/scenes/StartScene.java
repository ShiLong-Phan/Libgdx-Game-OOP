package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.scene.GameScene;

public class StartScene extends GameScene {

    private Vector2 vec;
    private Texture backgroundTexture, playButton;

    private float playbuttonX,playbuttonY;

    public StartScene(GameSceneManager gsm) {
            super(gsm);

            super.playStartEndMusic();

            //vector to detect click position
            vec = new Vector2();

            //resize bg image
            Pixmap pixmap;
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/main page background.png"));
            pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), pixmapOriginal.getFormat());
            pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
            backgroundTexture = new Texture(pixmap);
            pixmap.dispose();
            pixmapOriginal.dispose();

            //Start button
            playButton = new Texture(Gdx.files.internal("sprites/start button.png"));
            playbuttonX = Gdx.graphics.getWidth()/2 - playButton.getWidth()/2;
            playbuttonY = Gdx.graphics.getHeight()/2 - playButton.getHeight()/2;
    }

    @Override
    public void update(float delta) {
        //check for mouse click and get position
        if (Gdx.input.isTouched()) {
            vec.x = Gdx.input.getX();
            vec.y = Gdx.input.getY();
            if (vec.x > playbuttonX && vec.x < playbuttonX +  playButton.getWidth() &&
                    vec.y > playbuttonY && vec.y < playbuttonY + playButton.getHeight()) {

                //change scene
                System.out.println("Scene Changed\n");
                musicPlayer.stop();
                gsm.setState(GameSceneManager.Scene.LEVELSELECT);
            }

        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 0f));
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(playButton, playbuttonX, playbuttonY);
        batch.end();


    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButton.dispose();
        System.out.println("Scene disposed");
    }
}
