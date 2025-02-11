package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.GameEntity;

public class SilverCoinAnimation extends CustomAnimation {

    private float elapsedTime;
    private Animation animationCoin;

    public SilverCoinAnimation(GameEntity entity) {
        animationCoin = new Animation(0.1f, atlas.findRegions("silver"), Animation.PlayMode.LOOP);
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        textureRegion = (TextureRegion) animationCoin.getKeyFrame(elapsedTime);
    }
}
