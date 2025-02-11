package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class RightCollider extends Collider {

    public RightCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX() + entity.getWidth() - 10 - 50, entity.getY() + 50,  10, entity.getHeight() - 100);
        tag = "lateralR";
    }



    @Override
    public void update(float delta) {
        setX(gameEntity.getX() + getGameEntity().getWidth() - 10 - 50);
        setY(gameEntity.getY() + 50);
        setWidth(10);
        setHeight(gameEntity.getHeight() - 100);
    }
}
