package com.mygdx.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Application;

import static com.mygdx.game.utils.Constants.PPM;

public class TiledObjectRender {
    public static void parseTiledObjectLayer(World world, MapObjects objects, int layer) {
        for (MapObject object : objects) {
            Shape shape;

            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else if (object instanceof RectangleMapObject) {
                shape = createRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = createPolygon((PolygonMapObject) object);
            } else {
                continue;
            }

            Body body;
            BodyDef bdef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();
            String userdata = "";
            if(layer <= 2) {
                bdef.type = BodyDef.BodyType.StaticBody;
                fixtureDef.friction = 0f;
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.filter.categoryBits = Constants.BIT_WALL;
                fixtureDef.filter.maskBits = (Constants.BIT_PLAYER);
                if (layer == 0){
                    userdata = "ground";
                } if (layer ==  1){
                    userdata = "reset";
                } if (layer ==  2){
                    userdata = "terrain";
                }
            }else if (layer == 3){
                bdef.type = BodyDef.BodyType.KinematicBody;
                fixtureDef.friction = 0f;
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.filter.categoryBits = Constants.BIT_BLOCK;
                fixtureDef.filter.maskBits = (Constants.BIT_PLAYER);
                userdata = "coin";
            }
            body = world.createBody(bdef);
            body.createFixture(fixtureDef).setUserData(userdata);
            shape.dispose();
        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Application.SCALE / Constants.PPM, vertices[i * 2 + 1] / Application.SCALE / Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }

    private static PolygonShape createRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width / 2) / 2 / PPM,
                (rectangle.y + rectangle.height / 2) / 2 / PPM);
        polygon.setAsBox((rectangle.width / 2) / 2 / PPM,
                (rectangle.height / 2) / 2 / PPM,
                size,
                0.0f);
        return polygon;
    }

    private static PolygonShape createPolygon(PolygonMapObject polygonObject) {
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / 2 / PPM;
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(worldVertices);
        return polygon;
    }

}

