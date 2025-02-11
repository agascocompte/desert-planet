package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class GroundCollider extends Collider {

    public GroundCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX(), entity.getY() + entity.getHeight() - 20, entity.getWidth(), 20);
        tag = "ground";
    }


    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY() + gameEntity.getHeight() - 20);
        setWidth(gameEntity.getWidth());
        setHeight(20);
    }
}
