package com.mygdx.game.GameEngine.utils;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

public final class Constants {

    public static final float PPM = 32;//pixel per meter
    public static final short BIT_WALL = 1;
    public static final short BIT_PLAYER = 2;
    public static final short BIT_ENEMY = 4;
    public static ArrayList<String> tokenImages = new ArrayList<>();
    public static OrthogonalTiledMapRenderer tmr[];
}
