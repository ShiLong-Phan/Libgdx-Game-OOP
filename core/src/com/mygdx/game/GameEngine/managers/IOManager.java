package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.GameEngine.utils.Inputs;

public class IOManager {

    private Music musicPlayer;

    public IOManager() {
    }

    public boolean moveRight(){
        if (Gdx.input.isKeyPressed(Inputs.rightArrowKey()))
            return true;
        return false;
    }

    public boolean moveLeft(){
        if (Gdx.input.isKeyPressed(Inputs.leftArrowKey()))
            return true;
        return false;
    }

    public boolean moveUp(){
        if (Gdx.input.isKeyPressed(Inputs.upArrowKey())) {
            return true;
        }
        return false;
    }


    public boolean restartStage(){
        if(Gdx.input.isKeyPressed(Inputs.rKey()))
            return true;
        return false;
    }

    public boolean backToLevelSelect(){
        if(Gdx.input.isKeyPressed(Inputs.escKey()))
            return true;
        return false;
    }

    public boolean anyInputs(){
        if (Gdx.input.isKeyPressed(Inputs.AnyKey()))
            return true;
        return false;
    }

    public void playStartEndMusic(){
        if(this.musicPlayer != null)
            musicPlayer.stop();
        musicPlayer = Gdx.audio.newMusic(Gdx.files.internal("sound/A Very Brady Special.mp3"));
        musicPlayer.setLooping(true);
        musicPlayer.setVolume(0.03f);
        musicPlayer.play();
    }

    public void playGameMusic(){
        if(this.musicPlayer != null)
            musicPlayer.stop();
        musicPlayer = Gdx.audio.newMusic(Gdx.files.internal("sound/Derp Nugget.mp3"));
        musicPlayer.setLooping(true);
        musicPlayer.setVolume(0.006f);
        musicPlayer.play();
    }

    public void stopMusic(){
        musicPlayer.stop();
    }



}
