package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.movement.BulletMovement;

public class BulletAnimation extends CustomAnimation {
    private BulletMovement bulletMovement;
    private float elapsedTime;
    private Animation animationBullet;
    private Animation animationImpact;
    private Animation selectedAnimation;


    public BulletAnimation(GameEntity entity) {
        animationBullet = new Animation(0.05f, atlas.findRegions("Bullet"), Animation.PlayMode.LOOP);
        animationImpact = new Animation(0.05f, atlas.findRegions("Muzzle"), Animation.PlayMode.LOOP);
        selectedAnimation = animationBullet;
        elapsedTime = 0;
        Bullet bullet = (Bullet) entity;
        bulletMovement = (BulletMovement) bullet.getMovement();
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;

        if (bulletMovement.isImpact())
            selectedAnimation = animationImpact;

        textureRegion = (TextureRegion) selectedAnimation.getKeyFrame(elapsedTime);
    }
}
