package com.mygdx.game.movement;

import com.mygdx.game.GameManager;
import com.mygdx.game.entities.BackgroundParallax;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.helpers.GameInfo;

public class BackgroundMovement extends Movement {

    BackgroundParallax bg;

    public BackgroundMovement(GameEntity entity, GameManager gameManager, int x, int y, float speed) {
        super(entity, gameManager);
        bg = (BackgroundParallax) entity;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void update(float delta) {

        if (outOfBoundsLeft()) {
            rePositionRight();
        }
        else if (outOfBoundsRight()) {
            rePositionLeft();
        }

        if (!(((CharacterMovement)GameManager.getInstance().getCharacter().getMovement()).isBlockLeftDirection() || ((CharacterMovement)GameManager.getInstance().getCharacter().getMovement()).isBlockRightDirection()))
        this.x += speed * (-bg.getCamera().getDirection());

    }

    public void staticUpdate() {
        if (getX() >= GameInfo.WIDTH)
            this.x = -GameInfo.WIDTH;
        this.x += speed;
    }

    @Override
    public float getDirection() {
        return directionX;
    }

    public boolean outOfBoundsLeft() {
        boolean outOfBounds = false;

        if (this.x + 1920 <= bg.getCamera().getBounds()[0]) {
            outOfBounds = true;
            //System.out.println("Bounds 1 -------------------------> " + GameManager.getInstance().getPlayerCamera().getBounds()[1]);
            //System.out.println(" x -------------------------> " + x + "\n");
        }

        return outOfBounds;
    }

    public boolean outOfBoundsRight() {
        boolean outOfBounds = false;

        if (this.x - 1920 >= bg.getCamera().getBounds()[0]) {
            outOfBounds = true;
        }

        return outOfBounds;
    }

    public void rePositionRight() {
        this.x = this.x + (1920 * 2) - 1;
        //this.x = GameManager.getInstance().getPlayerCamera().getBounds()[0] + (1920 * 5);
    }

    public void rePositionLeft() {
        x = this.x - (1920 * 2) + 1;
    }

    public float getSpeed() {
        return speed;
    }
}
