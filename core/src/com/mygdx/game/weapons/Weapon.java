package com.mygdx.game.weapons;

import com.mygdx.game.entities.Bullet;

import java.util.ArrayList;

public abstract class Weapon {

    protected ArrayList<Bullet> charger = new ArrayList<Bullet>();
    protected int shotCooldown;

    protected float x;
    protected float y;

    protected boolean trigger = false;


    public abstract void shoot();
    public abstract boolean canShoot();

    public boolean isTriggered() {
        return trigger;
    }

    public abstract void update(float delta);
}
