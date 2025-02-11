package com.mygdx.game.movement;

import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.GameManager;

public abstract class Movement {

    public enum state {JUMPING, FALLING, GROUND, HIT, SHOOT}

    protected  state state;
    protected float x;
    protected float y;
    protected float speed;
    protected float vSpeed;
    protected float maxVspeed;
    protected GameManager gameManager;
    protected GameEntity entity;
    protected float directionXR;
    protected float directionXL;
    protected float directionX;
    protected float velY = 0;
    protected int moving = 0;  // -1 Left 0 Nothing 1 Right

    public Movement(GameEntity entity, GameManager gameManager) {
        this.entity = entity;
        this.gameManager = gameManager;
    }

    public abstract void update(float delta);

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public abstract float getDirection();

    public float getVelY() {
        return velY;
    }

    public float getVelX () {
        if (directionXL < 0)
            return -1;
        else if (directionXR > 0)
            return  1;
        else
            return 0;
    }

    public int getMoving() {
        return moving;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
    }
}
