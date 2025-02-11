package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.animation.BronzeCoinAnimation;
import com.mygdx.game.collision.CoinCollider;
import com.mygdx.game.collision.Collider;

public class BronzeCoin extends GameEntity {

    private int score = 1;
    private Sound coinSound;

    public BronzeCoin(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.BRONZE_COIN;
        setTexture(new Texture(texturePath));
        setPosition(x, y);
        setSize(width, height);
        animation = new BronzeCoinAnimation(this);
        addCollider(new CoinCollider(this));

        coinSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/bronze_coin.wav"));
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
                    (animation.getTextureRegion().getRegionWidth() / 2) * 0.6f, (animation.getTextureRegion().getRegionHeight() / 2)* 0.6f,
                    animation.getTextureRegion().getRegionWidth() * 0.6f, animation.getTextureRegion().getRegionHeight() * 0.6f,
                    getDirection(), 1, 0);
    }

    @Override
    public float getDirection() {
        return -1;
    }

    public int getScore() {
        return score;
    }

    public void playSound() {
        coinSound.play(0.2f);
    }
}
