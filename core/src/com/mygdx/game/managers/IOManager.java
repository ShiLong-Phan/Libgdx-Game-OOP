package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.utils.Inputs;

import java.util.ArrayList;

public class IOManager {

    private Inputs input;

    public IOManager() {
        input = new Inputs();
    }

    public boolean moveRight(){
        if (Gdx.input.isKeyPressed(input.rightArrowKey()))
            return true;
        return false;
    }

    public boolean moveLeft(){
        if (Gdx.input.isKeyPressed(input.leftArrowKey()))
            return true;
        return false;
    }

    public boolean moveUp(){
        if (Gdx.input.isKeyJustPressed(input.upArrowKey()))
            return true;
        return false;
    }

    public boolean nextLevel(){
        if (Gdx.input.isKeyPressed(input.downArrowKey()))
            return true;
        return false;
    }

    public boolean restartStage(){
        if(Gdx.input.isKeyPressed(input.rKey()))
            return true;
        return false;
    }



}
