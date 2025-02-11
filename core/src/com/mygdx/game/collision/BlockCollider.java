package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class BlockCollider extends Collider {

    public BlockCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight() - 20);
        tag = "block";
    }



    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY());
        setWidth(gameEntity.getWidth());
        setHeight(gameEntity.getHeight() - 20);
    }
}
