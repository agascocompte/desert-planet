package com.mygdx.game.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.GameEntity;

public class PlatformCollider extends Collider {

    //GameEntity entity;

    public PlatformCollider(GameEntity entity) {
        super(entity);
        if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM || entity.getType() == GameManager.ENTITY_TYPE.ICE_PLATFORM)
            bbox = new Rectangle(entity.getX(), entity.getY() + entity.getHeight() - 20, entity.getWidth() * 100, 20);
        else
            bbox = new Rectangle(entity.getX(), entity.getY() + entity.getHeight() - 20, entity.getWidth(), 20);
        tag = "platform";
    }


    @Override
    public void update(float delta) {
        setX(gameEntity.getX());
        setY(gameEntity.getY() + gameEntity.getHeight() - 20);
        if (gameEntity.getType() == GameManager.ENTITY_TYPE.PLATFORM || gameEntity.getType() == GameManager.ENTITY_TYPE.ICE_PLATFORM) {
            setWidth(gameEntity.getWidth() * 100);
        }
        else
            setHeight(20);
    }
}
