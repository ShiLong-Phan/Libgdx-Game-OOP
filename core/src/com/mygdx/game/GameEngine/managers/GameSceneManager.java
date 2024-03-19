package com.mygdx.game.GameEngine.managers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Application;
import com.mygdx.game.GameLayer.scenes.*;
import com.mygdx.game.GameEngine.scene.GameScene;

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
    private static GameSceneManager gsm = null;

    public static GameSceneManager getGsm(Application app){
        if(gsm == null){
            gsm = new GameSceneManager(app);
        }
        return gsm;
    }

    private GameSceneManager(final Application app) {
        this.app = app;
        this.playerControlManager = new PlayerControlManager(this);
        this.ioManager = new IOManager();
        this.eManager = EntityManager.getEntityManager(this);
        this.states = new Stack<GameScene>();
        this.setState(Scene.START);
    }


    public enum Scene {
        START,
        LEVELSELECT,
        LEVEL1,
        LEVEL2,
        LEVEL3,
        END
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
        if (states.size() >0) {
            states.pop().dispose();
        }
        states.push(getState(scene));
    }


    private GameScene getState(Scene scene) {
        switch (scene) {
            case START:
                return new StartScene(this);
            case LEVELSELECT:
                return new LevelSelect(this);
            case LEVEL1:
                return new level1(this);
            case LEVEL2:
                return new level2(this);
            case LEVEL3:
                return new level3(this);
            case END:
                return new EndScene(this);
        }

        return null;
    }

    public PlayerControlManager getPlayerControlManager() {
        return playerControlManager;
    }

    public EntityManager getEntityManager() {
        return eManager;
    }

    public IOManager getIOManager() {
        return ioManager;
    }

}
