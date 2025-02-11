package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.animation.BirdAnimation;
import com.mygdx.game.collision.*;
import com.mygdx.game.movement.BirdMovement;
import com.mygdx.game.movement.Movement;


public class Bird extends GameEntity {

    private int health;
    private int points;
    private Sound dieSound;

    public Bird(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.ENEMY;
        tag = "bird";
        movement = new BirdMovement(this, GameManager.getInstance(), x , y);
        animation = new BirdAnimation(this);
        addCollider(new BirdCollider(this));
        setTexture(new Texture(texturePath));
        setSize(width, height);

        health = 20;
        points = 25;

        dieSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/bird_death.wav"));

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
                    (animation.getTextureRegion().getRegionWidth() / 2) * 0.2f, (animation.getTextureRegion().getRegionHeight() / 2)* 0.2f,
                    animation.getTextureRegion().getRegionWidth() * 0.2f, animation.getTextureRegion().getRegionHeight() * 0.2f,
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

    public void playSound() {
        dieSound.play(0.5f);
    }
}



