package com.mygdx.game.movement;

import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.entities.Troll;
import com.mygdx.game.events.CollisionData;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.helpers.GameInfo;

public class BulletMovement extends Movement implements EventListener {

    private int bulletspeed = 15;
    private float direction;
    private boolean impact;
    private float impactPosition;
    private float deathCounter;
    private final int LIFETIME = 2;
    private int damage = 20;
    private boolean collided = false;

    public BulletMovement(GameEntity entity, GameManager gameManager, int x, int y, int direction) {
        super(entity, gameManager);
        this.x = x;
        this.y = y;
        this.direction = direction;
        gameManager.addEventListener(this);
        deathCounter = 0;
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case COLLISION_EVENT:
                CollisionData cData = (CollisionData) eventData.eventData;
                Collider bulletCollider;
                Collider otherCollider;

                if (cData.entityA.getType() == GameManager.ENTITY_TYPE.PROJECTILE) {
                    bulletCollider = cData.colliderA;
                    otherCollider = cData.colliderB;
                    if (cData.entityA == entity) // Posible Borrado
                        checkCollisionType(bulletCollider, otherCollider);
                } else if (cData.entityB.getType() == GameManager.ENTITY_TYPE.PROJECTILE) {
                    bulletCollider = cData.colliderB;
                    otherCollider = cData.colliderA;
                    if (cData.entityB == entity)
                        checkCollisionType(bulletCollider, otherCollider);
                }
                break;
        }
    }

    private void checkCollisionType(Collider bulletCollider, Collider otherCollider) {
        if (!collided && (scenarioCollision(bulletCollider, otherCollider) || enemyCollision(bulletCollider, otherCollider))) {//Colision con el el suelo o con las paredes
            collided = true;
        }
         // Colision con el enemigo
    }

    private boolean scenarioCollision(Collider bulletCollider, Collider otherCollider) {
        boolean collision = false;

        if ((otherCollider.getTag().equals("platform")  || otherCollider.getTag().equals("block")) && bulletCollider.getTag().equals("bullet")) {
            if (!impact) impactPosition = x;
            impact = true;
            collision = true;

            ((Bullet)entity).playSound();
        }
        return collision;
    }

    private boolean enemyCollision(Collider bulletCollider, Collider otherCollider) {
        boolean collision = false;

        if (otherCollider.getTag().equals("enemy") && bulletCollider.getTag().equals("bullet")) {
            if (!impact) {
                impactPosition = x;
                impact = true;
                int actualHealth = otherCollider.getGameEntity().getHealth();
                otherCollider.getGameEntity().setHealth(actualHealth - damage);
                damage = 0;
                collision = true;

                ((Bullet) entity).playSound();

                if (otherCollider.getGameEntity().getHealth() <= 0 && !otherCollider.getGameEntity().isDeath())
                    otherCollider.getGameEntity().playDieSound();
            }
        }
        return collision;
    }

    @Override
    public void update(float delta) {
        if (!impact)
            x = getX() + bulletspeed * direction * delta * GameInfo.PPM;
        else {
            if (direction > 0)
                x = impactPosition + 50;

            deathCounter += 0.1;
            if (deathCounter > LIFETIME) {
                entity.delete();
            }

        }

    }

    @Override
    public float getDirection() {
        return 0;
    }

    public boolean isImpact() {
        return impact;
    }
}
