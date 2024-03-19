package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameEngine.utils.Inputs;

public class IOManager {

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

    public boolean nextLevel(){
        if (Gdx.input.isKeyPressed(Inputs.downArrowKey()))
            return true;
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



}
