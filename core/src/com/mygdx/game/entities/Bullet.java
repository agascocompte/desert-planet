package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.animation.BulletAnimation;
import com.mygdx.game.collision.BulletCollider;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.movement.BulletMovement;
import com.mygdx.game.movement.Movement;

public class Bullet extends GameEntity {

    Sound collisionSound;

    public Bullet (int x, int y, int direction) {
        type = GameManager.ENTITY_TYPE.PROJECTILE;
        this.direction = direction;
        movement = new BulletMovement(this, GameManager.getInstance(), x, y, direction);
        animation = new BulletAnimation(this);
        setX(x);
        setY(y);
        addCollider(new BulletCollider(this));
        // setTexture(new Texture("badlogic.jpg"));
        setSize(30, 30);

        collisionSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/bullet_impact.wav"));
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
            //setTexture(animation.getTexture());
        }

        checkOutOfBounds();
    }

    private void checkOutOfBounds() {
        float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
        boolean outLeft = getX() + getWidth() < cameraBounds[0];
        boolean outRight = getX() > cameraBounds[1];
        boolean outBottom = getY() + getHeight() < cameraBounds[2];
        boolean outTop = getY() > cameraBounds[3];

        if (outLeft || outRight || outBottom || outTop)
            delete = true;
    }

    @Override
    public void draw(Batch batch) {
        if (animation != null && animation.getTextureRegion() != null)
            batch.draw(animation.getTextureRegion(), getX(), getY(),
                    (animation.getTextureRegion().getRegionWidth() / 2) * 0.12f, (animation.getTextureRegion().getRegionHeight() / 2)* 0.12f,
                    animation.getTextureRegion().getRegionWidth() * 0.12f, animation.getTextureRegion().getRegionHeight() * 0.12f,
                    direction, 1, 0);

    }

    @Override
    public float getDirection() {
        return direction;
    }

    public Movement getMovement() {
        return movement;
    }

    public void playSound () {
        collisionSound.play(0.3f);
    }
}
