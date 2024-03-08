package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Application;
import com.mygdx.game.managers.GameSceneManager;

public abstract class GameScene {
    //References
    protected GameSceneManager gsm;
    protected Application app;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected Music musicPlayer;

    protected GameScene(GameSceneManager gsm){
        this.gsm = gsm;
        this.app = gsm.getApp();
        batch = this.app.getSpriteBatch();
        camera = this.app.getCamera();
    }

    public void resize(int w, int h){
        camera.setToOrtho(false, w,h);
    }

    public abstract void update(float delta);
    public abstract void render();
    public abstract void dispose();

    protected void cameraUpdate() {
        Vector3 position = camera.position;
        //center character
        //position.x = player.getPosition().x * PPM; //if getting box2d units divide if giving thn multiply
        //position.y = player.getPosition().y * PPM;

        camera.position.set(position);
        camera.update();
    }

    protected void playStartEndMusic(){
        if(this.musicPlayer != null)
            musicPlayer.stop();
        musicPlayer = Gdx.audio.newMusic(Gdx.files.internal("sound/A Very Brady Special.mp3"));
        musicPlayer.setLooping(true);
        musicPlayer.setVolume(0.03f);
        musicPlayer.play();
    }

    protected void playGameMusic(){
        if(this.musicPlayer != null)
            musicPlayer.stop();
        musicPlayer = Gdx.audio.newMusic(Gdx.files.internal("sound/Derp Nugget.mp3"));
        musicPlayer.setLooping(true);
        musicPlayer.setVolume(0.008f);
        musicPlayer.play();
    }

}
