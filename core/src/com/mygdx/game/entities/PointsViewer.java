package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameManager;

public class PointsViewer extends GameEntity {

    private BitmapFont font;
    private int points;

    public PointsViewer(int x, int y) {
        font = new BitmapFont();
        setPosition(x, y);
    }

    @Override
    public void update(float delta) {
        points = GameManager.getInstance().getCharacter().getPoints();
    }

    public void draw(SpriteBatch batch) {
        font.getData().setScale(1, 1);
        font.draw(batch, "POINTS:", getX() + 48, getY() + 30);
        font.getData().setScale(2, 2);
        font.draw(batch, String.valueOf(points), getX() + 112, getY() + 35);
    }

    @Override
    public float getDirection() {
        return 0;
    }
}
