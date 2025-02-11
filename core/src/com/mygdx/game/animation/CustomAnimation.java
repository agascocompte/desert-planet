package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameManager;

public abstract class CustomAnimation {

    protected Texture texture;
    protected TextureRegion textureRegion;

    TextureAtlas atlas = GameManager.getInstance().getAtlas();

    public abstract void update(float delta);

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getTextureRegion () {
        return textureRegion;
    }

}
