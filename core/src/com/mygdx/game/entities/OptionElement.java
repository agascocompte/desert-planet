package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;

public class OptionElement extends GameEntity {

    private String mainTexture;

    public OptionElement(int x, int y, int width, int height, String texturePath) {
        type = GameManager.ENTITY_TYPE.BUTTON;

        mainTexture = texturePath;

        setTexture(new Texture(mainTexture));
        setPosition(x, y);
        setSize(width, height);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public float getDirection() {
        return 0;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }
}
