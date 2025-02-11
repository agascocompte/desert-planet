package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class FeetCollider extends Collider {

    public FeetCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX() + 45, entity.getY() + 10, entity.getWidth() - 90, 10);
        tag = "feet";
    }

    @Override
    public void update(float delta) {
        setX(gameEntity.getX() + 45);
        setY(gameEntity.getY() + 10);
        setWidth(gameEntity.getWidth() - 90);
        setHeight(10);
    }
}
