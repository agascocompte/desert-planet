package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public abstract class Collider {

    protected Rectangle bbox;
    protected String tag;
    protected GameEntity gameEntity;

    public Collider(GameEntity entity) {
        gameEntity = entity;
    }

    public boolean checkCollision(Collider otherCollider) {
        return otherCollider.bbox.overlaps(bbox);
    }

    public String getTag() {
        return tag;
    }

    public float getX() {
        return bbox.getX();
    }

    public float getY() {
        return bbox.getY();
    }

    public float getWidth() {
        return bbox.getWidth();
    }

    public float getHeight() {
        return bbox.getHeight();
    }

    public void setX(float x) {
        bbox.setX(x);
    }

    public void setY(float y) {
        bbox.setY(y);
    }

    public void setWidth(float width) {
        bbox.setWidth(width);
    }

    public void setHeight(float height) {
        bbox.setHeight(height);
    }

    public GameEntity getGameEntity () { return gameEntity; }

    public abstract void update(float delta);
}
