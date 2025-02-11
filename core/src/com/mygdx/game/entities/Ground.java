package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.collision.GroundCollider;
import com.mygdx.game.collision.PlatformCollider;

public class Ground extends GameEntity {

    public Ground(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.GROUND;
        setTexture(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        setSize(width, height);
        addCollider(new GroundCollider(this));
    }


    @Override
    public void update(float delta) {
        for (Collider collider : getColliders()) {
            collider.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public float getDirection() {
        return 0;
    }
}
