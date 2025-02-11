package com.mygdx.game.movement;

import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.Bird;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.events.CollisionData;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.helpers.GameInfo;

public class BirdMovement extends Movement implements EventListener {

    private state state;

    private Bird bird;
    private boolean hit = false;
    private int hitTime = 20, timeToDeath = 50;
    private int maxMove = 200;
    private int actualMove = 0;

    public BirdMovement(GameEntity entity, GameManager gameManager, int x, int y) {
        super(entity, gameManager);
        this.x = x;
        this.y = y;
        directionX = 1;
        gameManager.addEventListener(this);
        bird = (Bird) entity;
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
        if (!hit) {
            actualMove += 1;
            if (actualMove == maxMove) {
                actualMove = 0;
                directionX = -directionX;
            }

            x += (directionX) * delta * GameInfo.PPM;
        }



        checkTimers(); // Si esta hit, restara hasta ser mnor que 0 el timerHit y hit = true;, en ese caso lo reseteara a 2, lo mismo con death oper lo eliminará
    }

    private void checkTimers() {
        if (entity.isDeath()) {
            timeToDeath -= 0.1;

            if (timeToDeath <= 0) {
                entity.setPositioned(false);
                hit = false;
                entity.setDeath(false);
                entity.setHealth(20);
                timeToDeath = 50;
                x = -1000;
                y = -1000;
                EventData eventData = new EventData(EventData.EVENT_TYPE.ADD_SCORE_EVENT, entity);
                GameManager.getInstance().sendEvent(eventData);
            }
        }
    }

    private void checkCollisionType(Collider enemyCollider, Collider otherCollider) {
        if (!hit)
            bulletCollision(enemyCollider, otherCollider);
    }

    private boolean bulletCollision(Collider enemyCollider, Collider otherCollider) {
        boolean collision = false;

        if (enemyCollider.getTag().equals("enemy") && otherCollider.getTag().equals("bullet")) {
            entity.setDeath(true);
            hit = true;
            collision = true;

            ((Bird)entity).playSound();
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
