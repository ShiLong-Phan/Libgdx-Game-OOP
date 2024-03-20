package com.mygdx.game.GameLayer.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Application;
import com.mygdx.game.GameEngine.managers.GameSceneManager;
import com.mygdx.game.GameEngine.scene.GameScene;

public class LevelSelect extends GameScene {

    private Vector2 vec;
    private Texture backgroundTexture;
    private Texture[] playButton;
    private float accumulator;
    private float playbuttonX, playbuttonY;
    private SpriteBatch batch;


    public LevelSelect(GameSceneManager gsm) {
        super(gsm);
        //play moosic
        gsm.getIOManager().playStartEndMusic();

        batch = new SpriteBatch();
        //initialize and resize textures
        //vector to detect click position
        vec = new Vector2();
        accumulator = 0;

        //resize bg image
        Pixmap pixmap;
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal("backgrounds/game background.png"));
        pixmap = new Pixmap((int) (Gdx.graphics.getWidth()), (int) (Gdx.graphics.getHeight()), pixmapOriginal.getFormat());
        pixmap.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmapOriginal.dispose();

        //Start button
        playButton = new Texture[3];
        playButton[0] = new Texture(Gdx.files.internal("sprites/level 1 button.png"));
        playButton[1] = new Texture(Gdx.files.internal("sprites/level 2 button.png"));
        playButton[2] = new Texture(Gdx.files.internal("sprites/level 3 button.png"));
        playbuttonX = Gdx.graphics.getWidth() / 2 - playButton[0].getWidth() / 2;
        playbuttonY = Gdx.graphics.getHeight() / 2 - playButton[0].getHeight() / 2;
    }

    @Override
    public void update(float delta) {
        accumulator +=delta;
        if (Gdx.input.isTouched() && accumulator > .5) {
            vec.x = Gdx.input.getX();
            vec.y = Gdx.input.getY();
            //lvl1 selected

            if (vec.x > playbuttonX && vec.x < playbuttonX + playButton[0].getWidth() &&
                    vec.y > playbuttonY/2 && vec.y < playbuttonY/ 2  + playButton[0].getHeight() ) {

                //change scene
                System.out.println("lvl 1 selected\n");
                gsm.getIOManager().stopMusic();
                gsm.setState(GameSceneManager.Scene.LEVEL1);
            }
            //lvl2 selected
            if (vec.x > playbuttonX && vec.x < playbuttonX + playButton[0].getWidth() &&
                    vec.y > playbuttonY && vec.y < playbuttonY + playButton[0].getHeight()) {

                //change scene
                System.out.println("lvl 2 selected\n");
                gsm.getIOManager().stopMusic();
                gsm.setState(GameSceneManager.Scene.LEVEL2);

            }
            //lvl3 selected
            if (vec.x > playbuttonX && vec.x < playbuttonX + playButton[0].getWidth() &&
                    vec.y > playbuttonY*2 - playButton[0].getHeight()*1.2 && vec.y < playbuttonY*2) {
                //change scene
                System.out.println("lvl 3 selected\n");
                gsm.getIOManager().stopMusic();
                gsm.setState(GameSceneManager.Scene.LEVEL3);
            }
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(0, 0, 0f, 0f));
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(playButton[0], playbuttonX, playbuttonY * 2 - playButton[1].getHeight() * 1.2f);
        batch.draw(playButton[1], playbuttonX, playbuttonY);
        batch.draw(playButton[2], playbuttonX, playbuttonY / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButton[0].dispose();
        playButton[1].dispose();
        playButton[2].dispose();
    }
}
