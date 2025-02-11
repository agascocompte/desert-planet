package com.mygdx.game.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.GameManager;

public class Button extends GameEntity {

    private String mainTexture;
    private String hoverTexture;
    private String clickTexture;

    private Sound buttonSound;

    public Button(int x, int y, int width, int height, String texturePath, String hoverPath, String clickPath) {
        type = GameManager.ENTITY_TYPE.BUTTON;

        mainTexture = texturePath;
        hoverTexture = hoverPath;
        clickTexture = clickPath;

        setTexture(new Texture(mainTexture));
        setPosition(x, y);
        setSize(width, height);
        //buttonSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/silver_coin.wav"));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Batch batch) {

        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    public void playSound() {
        buttonSound.play(0.2f);
    }

    @Override
    public float getDirection() {
        return 0;
    }

    public void changeButtonStatus(int code) {
        getTexture().dispose();
        switch (code) {
            case 1:
                setTexture(new Texture(mainTexture));
                break;
            case 2:
                setTexture(new Texture(hoverTexture));
                break;
            case 3:
                setTexture(new Texture(clickTexture));
                break;
        }
    }
}
