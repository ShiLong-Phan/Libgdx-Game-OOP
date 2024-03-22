package com.mygdx.game.GameEngine.utils;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Application;

public class TiledObjectRender {

    public static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Application.SCALE / Constants.PPM, vertices[i * 2 + 1] / Application.SCALE / Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }

    public static PolygonShape createRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width / 2) / Application.SCALE / Constants.PPM,
                (rectangle.y + rectangle.height / 2) / Application.SCALE / Constants.PPM);
        polygon.setAsBox((rectangle.width / 2) / Application.SCALE / Constants.PPM,
                (rectangle.height / 2) / Application.SCALE / Constants.PPM,
                size,
                0.0f);
        return polygon;
    }

    public static PolygonShape createPolygon(PolygonMapObject polygonObject) {
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / Application.SCALE / Constants.PPM;
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(worldVertices);
        return polygon;
    }

    public static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Application.SCALE / Constants.PPM);
        circleShape.setPosition(new Vector2(circle.x / Application.SCALE / Constants.PPM, circle.y / Application.SCALE / Constants.PPM));
        return circleShape;
    }

}

