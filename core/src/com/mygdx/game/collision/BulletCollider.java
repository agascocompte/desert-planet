package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;


public class BulletCollider extends Collider {

    public BulletCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
        tag = "bullet";
        setWidth(entity.getWidth());
        setHeight(entity.getHeight());
    }

    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY());
        setWidth(gameEntity.getWidth());
        setHeight(gameEntity.getHeight());
    }
}

