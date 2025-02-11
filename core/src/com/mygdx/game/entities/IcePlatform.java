package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.collision.PlatformCollider;

public class IcePlatform extends GameEntity {

    private Sprite leftSide;
    private Sprite mediumSide;
    private Sprite rightSide;

    public IcePlatform(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.ICE_PLATFORM;
        setPosition(x, y);
        setSize(width, height);
        addCollider(new PlatformCollider(this));

        leftSide = new Sprite(new Texture("Platforms/platLeftIce.png"));
        mediumSide = new Sprite(new Texture("Platforms/platMediumIce.png"));
        rightSide = new Sprite(new Texture("Platforms/platRightIce.png"));
    }


    @Override
    public void update(float delta) {
        for (Collider collider : getColliders()) {
            collider.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(leftSide.getTexture(), getX(), getY(), 100, getHeight());
        for (int i = 0; i < getWidth() - 2; i++) {
            batch.draw(mediumSide.getTexture(), getX() + ((i + 1) * 100), getY(), 100, getHeight());
        }
        batch.draw(rightSide.getTexture(), getX() + ((getWidth() - 1) * 100), getY(), 100, getHeight());
    }

    @Override
    public float getDirection() {
        return 0;
    }
}

