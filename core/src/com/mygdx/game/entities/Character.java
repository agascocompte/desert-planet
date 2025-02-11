package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.*;
import com.mygdx.game.animation.CharacterAnimator;
import com.mygdx.game.collision.*;
import com.mygdx.game.movement.CharacterMovement;
import com.mygdx.game.movement.Movement;
import com.mygdx.game.weapons.BasicGun;
import com.mygdx.game.weapons.Weapon;

public class Character extends GameEntity {

    private static final int HEALTH = 100;
    private Weapon weapon;
    private int score;
    private int coins;
    CharacterMovement characterMovement;

    private Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_Hurt.mp3"));
    private Sound dieSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_Die.mp3"));
    private Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_Jump.mp3"));
    private Sound landingSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_Landing.mp3"));
    private Sound slidingSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_slide.mp3"));
    private Sound hearthSound = Gdx.audio.newSound(Gdx.files.internal("sound/effects/player_addLife.mp3"));


    public Character(int x, int y, int width, int height, String texturePath, boolean isMainScreen) {
        type = GameManager.ENTITY_TYPE.CHARACTER;

        if (!isMainScreen)
            characterMovement = new CharacterMovement(this, GameManager.getInstance(), x , y);
        else
            characterMovement = null;

        if (!isMainScreen)
            movement = characterMovement;
        else
            movement = null;
        weapon = new BasicGun(this, GameManager.getInstance());
        animation = new CharacterAnimator(this);
        addCollider(new FeetCollider(this));
        addCollider(new LeftCollider(this));
        addCollider(new RightCollider(this));
        addCollider(new HeadCollider(this));
        setTexture(new Texture(texturePath));
        setSize(width, height);
        setDeath(false);
        setHealth(HEALTH);
        score = 0;
        coins = 0;
    }

    @Override
    public void update(float delta) {
        if (movement != null) {
            movement.update(delta);
            setX(movement.getX());
            setY(movement.getY());
            weapon.update(delta);
        }

        for (Collider collider : getColliders()) {
            collider.update(delta);
        }

        if (animation != null) {
            animation.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (animation.getTextureRegion() != null)
        batch.draw(animation.getTextureRegion(), getX(), getY(),
                (animation.getTextureRegion().getRegionWidth() / 2) * 0.5f, (animation.getTextureRegion().getRegionHeight() / 2)* 0.5f,
                animation.getTextureRegion().getRegionWidth() * 0.5f, animation.getTextureRegion().getRegionHeight() * 0.5f,
                getDirection(), 1, 0);
    }

    @Override
    public float getDirection() {
        if (movement != null)
            return movement.getDirection();
        else return 1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addHealth(int health) {
        this.health += health;

        if (this.health > HEALTH) {
            this.health = HEALTH;
        }
    }

    public Movement getMovement () {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getPoints() {
        return score;
    }

    public void addPoints(int score) {
        this.score += score;
        GameManager.getInstance().addEnemiesKilled();
    }

    public void addCoins(int score) {
        coins += score;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isOnFloor () {
        CharacterMovement cm = (CharacterMovement) movement;
        return cm.isOnFloor();
    }

    public void playHurtSound () {
        hurtSound.play();
    }

    public void playDieSound () {
        dieSound.play();
    }

    public void playJumpSound () {
        jumpSound.play();
    }

    public void playLandingSound () {
        landingSound.play();
    }

    public void playSlideSound () {
        slidingSound.play();
    }

    public void stopSlideSound () {
        slidingSound.stop();
    }

    public void playLifeSound() {
        hearthSound.play(0.5f);
    }

    public void dispose() {
        this.dispose();
    }
}
