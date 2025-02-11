package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.entities.Ork;
import com.mygdx.game.movement.OrkMovement;

public class Ork1Animation extends CustomAnimation{
    private Animation animationRun;
    private Animation animationJump;
    private Animation animationHurt;
    private Animation animationDead;
    private Animation selectedAnimation;
    private float elapsedTime;

    private GameEntity entity;
    private Ork ork;
    private OrkMovement ork1Momevemnt;
    private boolean hit = false;
    private boolean death = false;

    public Ork1Animation(GameEntity entity) {
        this.entity = entity;
        ork = (Ork) entity;
        ork1Momevemnt = (OrkMovement) ork.getMovement();

        animationRun = new Animation(0.1f, atlas.findRegions("Ork1_WALK"), Animation.PlayMode.LOOP);
        animationJump = new Animation(0.1f, atlas.findRegions("Ork1_JUMP"), Animation.PlayMode.LOOP);
        animationHurt = new Animation(0.1f, atlas.findRegions("Ork1_HURT"), Animation.PlayMode.NORMAL);
        animationDead = new Animation(0.1f, atlas.findRegions("Ork1_DIE"), Animation.PlayMode.NORMAL);
        selectedAnimation = animationJump;
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        if (ork1Momevemnt.isHit() && !hit) {
            selectedAnimation = animationHurt;
            hit = true;
            elapsedTime = 0;
        }
        else if (entity.isDeath() && !death) {
            death = true;
            selectedAnimation = animationDead;
        }
        else if (!ork1Momevemnt.isHit() && !entity.isDeath()){
            hit = false;
            if (ork.getMovement().getVelY() < -0.9) {
                selectedAnimation = animationJump;
            }
            else {
                selectedAnimation = animationRun;
                death = false;
            }
        }

        elapsedTime += delta;
        textureRegion = (TextureRegion) selectedAnimation.getKeyFrame(elapsedTime);
    }
}
