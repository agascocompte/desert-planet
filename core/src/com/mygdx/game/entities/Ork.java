package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.animation.Ork1Animation;
import com.mygdx.game.collision.*;
import com.mygdx.game.movement.Movement;
import com.mygdx.game.movement.OrkMovement;


public class Ork extends GameEntity {

    private int health;
    private int points;
    private Sound hitSound;
    private Sound dieSound;

    public Ork(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.ENEMY;
        tag = "ork1";
        movement = new OrkMovement(this, GameManager.getInstance(), x , y);
        animation = new Ork1Animation(this);
        addCollider(new OrkCollider(this));
        setTexture(new Texture(texturePath));
        setSize(width, height);

        health = 100;
        points = 50;

        hitSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/ork_hit.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/ork_death.wav"));
    }

    @Override
    public void update(float delta) {
        if (movement != null) {
            movement.update(delta);
            setX(movement.getX());
            setY(movement.getY());
        }

        for (Collider collider : getColliders()) {
            collider.update(delta);
        }

        if (animation != null) {
            animation.update(delta);
            setTexture(animation.getTexture());
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
        return movement.getDirection();
    }

    public Movement getMovement() {
        return movement;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPoints() {
        return points;
    }

    public void playHitSound() {
        hitSound.play(0.4f);
    }

    public void playDieSound() {
        dieSound.play(0.4f);
    }
}

