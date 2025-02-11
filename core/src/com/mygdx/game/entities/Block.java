package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameManager;
import com.mygdx.game.collision.BlockCollider;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.collision.PlatformCollider;

public class Block extends GameEntity {
    public Block(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.BLOCK;
        // animation = new CharacterAnimationt();
        setTexture(new Texture(texturePath));
        setPosition(x, y);
        setSize(width, height);
        addCollider(new BlockCollider(this));
        addCollider(new PlatformCollider(this));
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
