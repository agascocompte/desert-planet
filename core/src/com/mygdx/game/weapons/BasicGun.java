package com.mygdx.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameManager;
import com.mygdx.game.entities.Character;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.events.InputData;

public class BasicGun extends Weapon implements EventListener{

    private GameEntity entity;
    Character character;
    Sound shootSound;

    private float cooldownTimer = 0;


    public BasicGun (GameEntity entity, GameManager gameManager) {
        shotCooldown = 3;
        this.x = (entity.getX() + entity.getWidth()) / 2;
        this.y = (entity.getY() + entity.getHeight()) / 2;
        this.entity = entity;
        character = (Character) entity;

        gameManager.addEventListener(this);

        shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/shoot.mp3"));
    }

    @Override
    public void shoot() {
        if (canShoot()) {

            GameManager.getInstance().addNewGameEntity(GameManager.ENTITY_TYPE.PROJECTILE, x, y, entity.getDirection(),"bullet");
            cooldownTimer = 0;

            playSound();
            GameManager.getInstance().addShots();
        }
    }

    @Override
    public boolean canShoot() {
        boolean can = false;
        if (cooldownTimer >= shotCooldown) {
            can = true;
        }
        return can;
    }

    @Override
    public void update(float delta) {
        cooldownTimer += 0.1f;
        if (character.getMovement().getDirection() > 0)
            x = entity.getX() + entity.getWidth() / 2 + 40;
        else
            x = entity.getX() + entity.getWidth() / 2 - 60;
        y = entity.getY() + (entity.getHeight() / 2) - 15;

        if (trigger) shoot();
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case KEYDOWN_EVENT:
                InputData kdData = (InputData) eventData.eventData;
                if ((kdData.keyCode == Input.Keys.SPACE || kdData.keyCode == 1) && !entity.isDeath()) {
                    trigger = true;
                }
                break;
            case KEYUP_EVENT:
                InputData kuData = (InputData) eventData.eventData;
                if (kuData.keyCode == Input.Keys.SPACE || kuData.keyCode == 1) {
                    trigger = false;
                }
                break;
        }
    }

    public void playSound() {
        shootSound.play(0.5f);
    }
}
