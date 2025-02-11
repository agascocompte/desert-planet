package com.mygdx.game.movement;

import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.entities.Troll;
import com.mygdx.game.events.CollisionData;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.helpers.GameInfo;

import static com.mygdx.game.movement.CharacterMovement.GRAVITY;

public class TrollMovement extends Movement implements EventListener {

    public enum state {FALLING, GROUND, JUMPING}

    private state state;

    private float maxVelY = GRAVITY * 5;
    private Troll troll;
    private boolean hit = false;
    private float oldY = 0;
    private int hitTime = 40, timeToDeath = 50;
    private  int followDirection = 0;
    private float jumpForce = 0;
    private boolean targeted;

    public TrollMovement(GameEntity entity, GameManager gameManager, int x, int y) {
        super(entity, gameManager);
        this.x = x;
        this.y = y;
        directionX = -3;
        state = state.FALLING;
        gameManager.addEventListener(this);
        troll = (Troll) entity;
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case COLLISION_EVENT:
                CollisionData cData = (CollisionData) eventData.eventData;
                Collider enemyCollider;
                Collider otherCollider;

                if (cData.entityA.getType() == GameManager.ENTITY_TYPE.ENEMY) {
                    enemyCollider = cData.colliderA;
                    otherCollider = cData.colliderB;
                    if (cData.entityA == entity) // Posible Borrado
                        checkCollisionType(enemyCollider, otherCollider);
                } else if (cData.entityB.getType() == GameManager.ENTITY_TYPE.ENEMY) {
                    enemyCollider = cData.colliderB;
                    otherCollider = cData.colliderA;
                    if (cData.entityB == entity)
                        checkCollisionType(enemyCollider, otherCollider);
                }
                break;
        }
    }

    @Override
    public void update(float delta) {

        if (entity.getX() < GameManager.getInstance().getCharacter().getX() && GameManager.getInstance().getCharacter().getX() - entity.getX() > 50) {
            directionX = 3;
            targeted = false;
        }
        else  if (entity.getX() > GameManager.getInstance().getCharacter().getX() && entity.getX() - GameManager.getInstance().getCharacter().getX() > 50){
            directionX = -3;
            targeted = false;
        }
        else  {
            targeted = true;
        }

        if (entity.getHealth() <= 0 || entity.getY() < -200) {
            entity.setDeath(true);
        }

        else {
            switch (state) {
                case FALLING:
                    fall();
                    break;
                case GROUND:
                    velY = 0;
                    break;
                case JUMPING:
                    jumpForce = 10f;
                    velY = jumpForce;
                    break;
            }

            if (!hit && !targeted)
                x += (directionX) * delta * GameInfo.PPM;

            if (state != state.GROUND) {
                y += velY * delta * GameInfo.PPM;

            }

            state = state.FALLING;

            //Probar las animaciones primero porque asi se para
        }

        checkTimers(); // Si esta hit, restara hasta ser mnor que 0 el timerHit y hit = true;, en ese caso lo reseteara a 2, lo mismo con death oper lo eliminará
    }

    private void fall() {
        velY += GRAVITY;

        if (velY < maxVelY)
            velY = maxVelY;
    }

    private void checkTimers() {
        if (hit) {
            hitTime -= 1;
            if (hitTime <= 0) {
                hit = false;
                hitTime = 40;
            }
        }

        if (entity.isDeath()) {
            timeToDeath -= 0.1;

            if (timeToDeath <= 0) {
                entity.setPositioned(false);
                hit = false;
                entity.setDeath(false);
                entity.setHealth(150);
                timeToDeath = 50;

                if (y > 0) {
                    EventData eventData = new EventData(EventData.EVENT_TYPE.ADD_SCORE_EVENT, entity);
                    GameManager.getInstance().sendEvent(eventData);
                }
                x = -1000;
                y = -1000;
            }
        }
    }

    private void checkCollisionType(Collider enemyCollider, Collider otherCollider) {
        feetCollision(enemyCollider, otherCollider); //Colision con el suelo si esta cayendo
        lateralCollision(enemyCollider, otherCollider); //Colision lateral
        if (!hit)
            bulletCollision(enemyCollider, otherCollider);
    }

    private boolean feetCollision(Collider enemyCollider, Collider otherCollider) {
        boolean collision = false;

        if (enemyCollider.getTag().equals("enemy") && (otherCollider.getTag().equals("platform") || otherCollider.getTag().equals("ground")) && velY < 0) {
            velY = 0;
            y = otherCollider.getY() + otherCollider.getHeight() - 25;
            collision = true;
        }

        return collision;
    }

    private boolean lateralCollision(Collider enemyCollider, Collider otherCollider) {
        boolean collision = false;

        if (enemyCollider.getTag().equals("enemy")  && otherCollider.getTag().equals("block")) {
            if (otherCollider.getTag().equals("block") && enemyCollider.getGameEntity() == troll) {  // Colision con un bloque

                if (gameManager.getCharacter().getY() > y + 20 && velY <= 0 && enemyCollider.getY() < otherCollider.getY() + 20 ) {
                    //x = otherCollider.getX() - entity.getWidth() - 10;
                    state = state.JUMPING;

                    if (directionX > 0) x = otherCollider.getX() - entity.getWidth();
                    else x = otherCollider.getX() + otherCollider.getWidth();
                }
                else {

                }


                collision = true;
            }
        }
        return collision;
    }

    private boolean bulletCollision(Collider enemyCollider, Collider otherCollider) {
        boolean collision = false;

        if (enemyCollider.getTag().equals("enemy")  && otherCollider.getTag().equals("bullet") && !hit) {
            hit = true;
            collision = true;

            if (troll.getHealth() > 0)
                troll.playHitSound();
        }

        return collision;
    }

    @Override
    public float getDirection() {
        return directionX;
    }

    public boolean isHit() {
        return hit;
    }
}
