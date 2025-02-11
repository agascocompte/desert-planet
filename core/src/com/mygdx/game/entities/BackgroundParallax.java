package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.GameManager;
import com.mygdx.game.camera.PlayerCamera;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.movement.BackgroundMovement;


public class BackgroundParallax extends GameEntity {

    protected PlayerCamera camera;
    protected int layer;

    public BackgroundParallax(int x, int y, int width, int height, String texturePath, int layer, float speed) {
        type = GameManager.ENTITY_TYPE.BACKGROUND;
        movement = new BackgroundMovement(this, GameManager.getInstance(), x , y, speed);
        setTexture(new Texture(texturePath));
        setSize(width, height);
        camera = GameManager.getInstance().getPlayerCamera();
        this.layer = layer;
    }

    @Override
    public void update(float delta) {
        if (movement != null) {
            //if (layer != 5 && layer !=6 && layer != 7) {
                movement.update(delta);
                setX(movement.getX());// + GameManager.getInstance().getPlayerCamera().getBounds()[0]);
                setY(movement.getY());
            //}
            //else {
           //     movement.update(delta);
            //    setX(GameManager.getInstance().getPlayerCamera().getBounds()[0]);
            //    setY(movement.getY());
            //}
        }
    }

    public void staticUpdate(float delta) {
        if (((BackgroundMovement) movement).getSpeed() > 0) {
            ((BackgroundMovement) movement).staticUpdate();
            setX(movement.getX());
            setY(movement.getY());
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public float getDirection() {
        return movement.getDirection();
    }

    public void setCamera (PlayerCamera camera) {
        this.camera = camera;
    }

    public PlayerCamera getCamera() {
        return camera;
    }

    public int getLayer() {
        return layer;
    }
}
