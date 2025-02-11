package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class LeftCollider extends Collider {

    public LeftCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX() + 45, entity.getY() + 50,  10, entity.getHeight() - 100);
        tag = "lateralL";
    }

    @Override
    public void update(float delta) {
        setX(gameEntity.getX() + 45);
        setY(gameEntity.getY() + 50);
        setWidth(10);
        setHeight(gameEntity.getHeight() - 100);
    }
}
