package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;

public class Grass extends GameEntity {
    public Grass(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.GRASS;
        setTexture(new Texture(texturePath));
        setPosition(x, y);
        setSize(width, height);
    }

    @Override
    public void update(float delta) {

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
