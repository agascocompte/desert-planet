package com.mygdx.game.events;

import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.GameEntity;

public class CollisionData {

    public GameEntity entityA;
    public Collider colliderA;
    public GameEntity entityB;
    public Collider colliderB;

    public CollisionData(GameEntity entityA, Collider colliderA, GameEntity entityB, Collider colliderB) {
        this.entityA = entityA;
        this.colliderA = colliderA;
        this.entityB = entityB;
        this.colliderB = colliderB;
    }

}
