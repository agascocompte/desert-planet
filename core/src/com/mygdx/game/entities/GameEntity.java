package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.animation.CustomAnimation;
import com.mygdx.game.GameManager;
import com.mygdx.game.movement.Movement;

import java.util.ArrayList;
import java.util.List;

public abstract class GameEntity extends Sprite {

    protected Movement movement;
    protected CustomAnimation animation;
    protected List<Collider> colliders;
    protected float direction;
    protected boolean delete = false;
    protected boolean death = false;
    protected String tag = "";
    private boolean moved = false;
    private int offSetX = 0;
    private boolean positioned = true;

    protected GameManager.ENTITY_TYPE type;

    protected int health;

    public abstract void update(float delta);

    public void addCollider(Collider colider) {
        if (colliders == null) {
            colliders = new ArrayList<Collider>();
        }
        colliders.add(colider);
    }

    public GameManager.ENTITY_TYPE getType() {
        return type;
    }

    public List<Collider> getColliders() {
        return colliders;
    }

    public abstract float getDirection();

    public void delete() {
        delete = true;
    }

    public boolean hasToDelete() {
        return delete;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        GameEntity other = (GameEntity) obj;
        if (type == other.type && getWidth() == other.getWidth())
            return true;
        else
            return false;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        this.offSetX = offSetX;
    }

    public boolean isPositioned() {
        return positioned;
    }

    public void setPositioned(boolean positioned) {
        this.positioned = positioned;
    }

    public Movement getMovement() {
        return movement;
    }

    public void playDieSound() {}
}
