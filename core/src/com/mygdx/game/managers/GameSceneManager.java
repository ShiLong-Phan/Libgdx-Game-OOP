package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Application;
import com.mygdx.game.states.*;

import java.util.Stack;

public class GameSceneManager {

    //Application reference
    private final Application app;
    //dont keep too many states in memory
    private Stack<GameScene> states;
    private EntityManager eManager;
    private PlayerControlManager playerControlManager;
    private IOManager ioManager;
    private Texture backgroundTexture;


    public enum Scene {
        START,
        MAIN,
        END
    }

    public GameSceneManager(final Application app) {
        this.app = app;
        this.playerControlManager = new PlayerControlManager(this);
        this.ioManager = new IOManager();
        this.eManager = new EntityManager(this);
        this.states = new Stack<GameScene>();
        this.setState(Scene.START);
    }

    public Application getApp() {
        return app;
    }


    public void update(float delta) {
        states.peek().update(delta);
    }

    public void render() {
        states.peek().render();
    }

    public void dispose() {
        for (GameScene gs : this.states) {
            gs.dispose();
        }
    }

    public void resize(int w, int h) {
        states.peek().resize(w, h);
    }

    public void setState(Scene scene) {
        if (states.size() >= 1) {
            states.pop().dispose();
        }
        states.push(getState(scene));
    }


    private GameScene getState(Scene scene) {
        switch (scene) {
            case START:
                return new StartScene(this);
            case MAIN:
                return new MainScene(this);
            case END:
                return new EndScene(this);
        }

        return null;
    }

    public PlayerControlManager getPlayerControlManager(){
        return playerControlManager;
    }

    public EntityManager getEntityManager(){
        return eManager;
    }

    public IOManager getIOManager() {
        return ioManager;
    }

}
