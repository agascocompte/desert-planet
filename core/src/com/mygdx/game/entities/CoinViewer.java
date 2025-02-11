package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameManager;

public class CoinViewer extends GameEntity {

    private BitmapFont font;
    private int coins;

    public CoinViewer(int x, int y) {
        setTexture(new Texture("HUD/hudCoin.png"));
        setPosition(x, y);
        font = new BitmapFont();
    }

    @Override
    public void update(float delta) {
        coins = GameManager.getInstance().getCharacter().getCoins();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), 50, 50);
        font.getData().setScale(1, 1);
        font.draw(batch, "X", getX() + 48, getY() + 30);
        font.getData().setScale(2, 2);
        font.draw(batch, String.valueOf(coins), getX() + 65, getY() + 35);
    }

    @Override
    public float getDirection() {
        return 0;
    }
}
