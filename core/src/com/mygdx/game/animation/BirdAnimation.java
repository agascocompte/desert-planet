package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Bird;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.movement.BirdMovement;

public class BirdAnimation extends CustomAnimation{
    private Animation animationFly;
    private Animation animationDead;
    private Animation selectedAnimation;
    private float elapsedTime;

    private GameEntity entity;
    private Bird bird;
    private BirdMovement birdMomevemnt;
    private boolean hit = false;
    private boolean death = false;

    public BirdAnimation(GameEntity entity) {
        this.entity = entity;
        bird = (Bird) entity;
        birdMomevemnt = (BirdMovement)bird.getMovement();

        animationFly = new Animation(0.08f, atlas.findRegions("Bird_FLY"), Animation.PlayMode.LOOP);
        animationDead = new Animation(0.1f, atlas.findRegions("Bird_DIE"), Animation.PlayMode.LOOP);
        selectedAnimation = animationFly;
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        if (entity.isDeath() && !death) {
            death = true;
            selectedAnimation = animationDead;
        }
        else if (!birdMomevemnt.isHit() && !entity.isDeath()){
            hit = false;
            selectedAnimation = animationFly;
            death = false;

        }

        elapsedTime += delta;
        textureRegion = (TextureRegion) selectedAnimation.getKeyFrame(elapsedTime);
    }
}
