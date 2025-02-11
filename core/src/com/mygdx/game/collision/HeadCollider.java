package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class HeadCollider extends Collider {

    public HeadCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX() + (entity.getWidth() / 2) - 20, entity.getY() + entity.getHeight() - 30,  40, 20);
        tag = "head";
    }



    @Override
    public void update(float delta) {
        setX(gameEntity.getX() + (gameEntity.getWidth() / 2) - 20);
        setY(gameEntity.getY() + gameEntity.getHeight() - 30);
        setWidth(40);
        setHeight(20);
    }
}
