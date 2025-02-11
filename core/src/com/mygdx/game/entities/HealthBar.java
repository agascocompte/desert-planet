package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameManager;

public class HealthBar extends GameEntity {

    private BitmapFont font;
    private int health;

    public HealthBar(int x, int y, int width, int height) {
        setTexture(new Texture("HUD/hudHeart_full.png"));
        font = new BitmapFont();
        setPosition(x, y);
        setSize(width, height);
    }

    @Override
    public void update(float delta) {
        health = GameManager.getInstance().getCharacter().getHealth() * 2;
        if (health < 0 ) health = 0;
    }

    public void drawBatch(SpriteBatch batch) {
        font.draw(batch, String.valueOf(health / 2), getX() + (getWidth() / 2) - 7, getY() + (getHeight() / 2) + 5);
        batch.draw(getTexture(), getX() - 25, getY() - 10, 50,50);
    }

    public void drawRenderer(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        if (health / 2 > 50){
            shapeRenderer.setColor(Color.GREEN);}
        else if (health / 2 > 20){
            shapeRenderer.setColor(Color.ORANGE);}
        else{
            shapeRenderer.setColor(Color.RED);}
        shapeRenderer.rect(getX() + 1, getY() + 1, health, 20);
    }

    @Override
    public float getDirection() {
        return 0;
    }
}
