package com.mygdx.game.map;

import com.mygdx.game.entities.GameEntity;

public class Tile {
    private int id;
    private int x, y;
    private int entityX, entityWidth;
    private GameEntity entity = null;


    public void setEntity(GameEntity entity) {
        this.entity = entity;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEntityX() {
        return entityX;
    }

    public void setEntityX(int entityX) {
        this.entityX = entityX;
    }

    public int getEntityWidth() {
        return entityWidth;
    }

    public void setEntityWidth(int entityWidth) {
        this.entityWidth = entityWidth;
    }

    public Tile (int id, int x, int y, int entityX, int entityWidth) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.entityX = entityX;
        this.entityWidth = entityWidth;


    }
}
