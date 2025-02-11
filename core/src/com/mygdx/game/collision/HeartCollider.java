package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class HeartCollider extends Collider {

    public HeartCollider(GameEntity entity) {
        super(entity);
        tag = "coin";
        bbox = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }

    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY());
        setWidth(gameEntity.getWidth());
        setHeight(gameEntity.getHeight());
    }
}
