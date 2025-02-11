package com.mygdx.game.collision;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.GameEntity;

public class TrollCollider extends Collider {

    public TrollCollider(GameEntity entity) {
        super(entity);
        bbox = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
        tag = "enemy";
        setWidth(entity.getWidth());
        setHeight(entity.getHeight());
    }

    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY() - 25);
        setWidth(gameEntity.getWidth());
        setHeight(gameEntity.getHeight());
    }
}

