package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Character;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.movement.CharacterMovement;
import com.mygdx.game.movement.Movement;

public class CharacterAnimator extends CustomAnimation{
    Character character;
    GameEntity entity;

    private Animation animationIdle;
    private Animation animationJump;
    private Animation animationRun;
    private Animation animationHit;
    private Animation animationShotRun;
    private Animation animationShot;
    private Animation animationShotJump;
    private Animation animationDead;
    private Animation selectedAnimation;
    private float elapsedTime;

    private boolean jumping = false;
    private boolean shooting = false;
    private boolean hit = false;
    private boolean dead = false;
    private boolean doublejump = false;



    public CharacterAnimator(GameEntity entity) {
        character = (Character) entity;
        this.entity = entity;


        animationIdle = new Animation(0.05f, atlas.findRegions("Idle"), Animation.PlayMode.LOOP);
        animationJump = new Animation(0.05f, atlas.findRegions("Jump"), Animation.PlayMode.NORMAL);
        animationRun = new Animation(0.05f, atlas.findRegions("Run"), Animation.PlayMode.LOOP);
        animationHit = new Animation(0.05f, atlas.findRegions("JumpMelee"), Animation.PlayMode.NORMAL);
        animationShotRun = new Animation(0.05f, atlas.findRegions("RunShoot"), Animation.PlayMode.LOOP);
        animationShot = new Animation(0.05f, atlas.findRegions("Shoot"), Animation.PlayMode.LOOP);
        animationShotJump = new Animation(0.05f, atlas.findRegions("JumpShoot"), Animation.PlayMode.LOOP);
        animationDead = new Animation(0.05f, atlas.findRegions("Dead"), Animation.PlayMode.NORMAL);


        selectedAnimation = animationIdle;
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;



        if (!entity.isDeath()) {
            dead = false;
            if (character.getMovement() != null) {
                if (character.getMovement().getVelY() == 0) {  // Suelo
                    if (jumping) {
                        jumping = false;
                    }

                    if (shooting) {
                        shooting = false;
                    }

                    if (character.getMovement().getVelX() == 0 || ((CharacterMovement) character.getMovement()).isSliding()) // Parado
                        if (character.getWeapon().isTriggered()) {      // Disparo
                            selectedAnimation = animationShot;
                        } else {
                            selectedAnimation = animationIdle;   //Idle
                        }
                    else {                                          // Corriendo
                        if (character.getWeapon().isTriggered()) {  // Disparo
                            selectedAnimation = animationShotRun;
                        } else {
                            selectedAnimation = animationRun;   //Corre
                        }

                    }

                } else {                                          // Aire
                    if (character.getWeapon().isTriggered()) {  // Disparo
                        if (!shooting) {
                            shooting = true;
                            elapsedTime = 0;
                            selectedAnimation = animationShotJump;
                        }
                    } else {                                      // Salto normal
                        selectedAnimation = animationJump;
                    }

                    if (((CharacterMovement) character.getMovement()).isDoubleJump() && !doublejump) {
                        elapsedTime = 0;
                        doublejump = true;
                    }

                    if (!jumping) {                                 // Salto normal y reseteo de elpasedTime
                        selectedAnimation = animationJump;
                        elapsedTime = 0;
                        jumping = true;
                        doublejump = false;
                    }


                }
            }
            else {
                selectedAnimation = animationRun;
            }
            Movement movement = (Movement) character.getMovement();
            if (movement instanceof CharacterMovement) {
                CharacterMovement cm = (CharacterMovement) movement;
                if (cm.isHit()) {                                               // Golpeado
                    if (!hit) {
                        selectedAnimation = animationHit;
                        elapsedTime = 0;
                        hit = true;
                    }
                }
                else {
                    hit = false;
                }
            }


        }
        else if (!dead) {                              // Muerto
            selectedAnimation = animationDead;
            elapsedTime = 0;
            dead = true;
        }

        textureRegion = (TextureRegion) selectedAnimation.getKeyFrame(elapsedTime);
    }
}
