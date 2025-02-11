package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.GameEntity;

public class HeartAnimation extends CustomAnimation {

    private float elapsedTime;
    private Animation animationHeart;

    public HeartAnimation(GameEntity entity) {
        animationHeart = new Animation(0.1f, atlas.findRegions("heart"), Animation.PlayMode.LOOP);
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        textureRegion = (TextureRegion) animationHeart.getKeyFrame(elapsedTime);
    }
}
