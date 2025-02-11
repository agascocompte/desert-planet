package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.entities.Troll;
import com.mygdx.game.movement.TrollMovement;

public class TrollAnimation extends CustomAnimation{
    private Animation animationRun;
    private Animation animationJump;
    private Animation animationHurt;
    private Animation animationDead;
    private Animation selectedAnimation;
    private float elapsedTime;

    private GameEntity entity;
    private Troll troll;
    private TrollMovement trollMomevemnt;
    private boolean hit = false;
    private boolean death = false;

    public TrollAnimation(GameEntity entity) {
        this.entity = entity;
        troll = (Troll) entity;
        trollMomevemnt = (TrollMovement) troll.getMovement();

        animationRun = new Animation(0.08f, atlas.findRegions("Troll_RUN"), Animation.PlayMode.LOOP);
        animationJump = new Animation(0.1f, atlas.findRegions("Troll_JUMP"), Animation.PlayMode.LOOP);
        animationHurt = new Animation(0.1f, atlas.findRegions("Troll_HURT"), Animation.PlayMode.NORMAL);
        animationDead = new Animation(0.1f, atlas.findRegions("Troll_DIE"), Animation.PlayMode.NORMAL);
        selectedAnimation = animationJump;
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        if (trollMomevemnt.isHit() && !hit) {
            selectedAnimation = animationHurt;
            hit = true;
            elapsedTime = 0;
            death = false;
        }
        else if (entity.isDeath() && !death) {
            elapsedTime = 0;
            death = true;
            selectedAnimation = animationDead;
        }
        else if (!trollMomevemnt.isHit() && !entity.isDeath()){
            hit = false;
            if (troll.getMovement().getVelY() < -0.9) {
                selectedAnimation = animationJump;
                death = false;
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
