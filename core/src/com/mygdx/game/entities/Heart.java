package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.animation.HeartAnimation;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.collision.HeartCollider;

public class Heart extends GameEntity {

    private int life = 50;

    public Heart(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.HEART;
        setTexture(new Texture(texturePath));
        setPosition(x, y);
        setSize(width, height);
        animation = new HeartAnimation(this);
        addCollider(new HeartCollider(this));
    }

    @Override
    public void update(float delta) {
        for (Collider collider : getColliders()) {
            collider.update(delta);
        }

        if (animation != null) {
            animation.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (animation.getTextureRegion() != null)
            batch.draw(animation.getTextureRegion(), getX(), getY(),
                    (animation.getTextureRegion().getRegionWidth() / 2) * 0.5f, (animation.getTextureRegion().getRegionHeight() / 2)* 0.5f,
                    animation.getTextureRegion().getRegionWidth() * 0.5f, animation.getTextureRegion().getRegionHeight() * 0.5f,
                    getDirection(), 1, 0);
    }

    @Override
    public float getDirection() {
        return 1;
    }

    public int getLife() {
        return life;
    }

}
