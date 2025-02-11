package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.events.CollisionData;
import com.mygdx.game.events.EventData;
import com.mygdx.game.map.WorldGenerator;
import com.mygdx.game.stages.OptionsStage;
import com.mygdx.game.stages.PauseStage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Environment {

    private List<GameEntity> totalEntities;
    private List<GameEntity> actualEntities;
    private List<GameEntity> entityAddBuffer; // List para añadir temporalemnte aqui las nuevas entidades y no provocar ConcurrentException
    private WorldGenerator world;


    public Environment() {
        totalEntities = new ArrayList<GameEntity>();
        actualEntities = new ArrayList<GameEntity>();
        entityAddBuffer = new ArrayList<GameEntity>();

    }

    public void addEntities(List<GameEntity> entities) {
        entityAddBuffer.addAll(entities);
    }

    public void addEntity(GameEntity entity) {
        if (entity.getType() == GameManager.ENTITY_TYPE.CHARACTER) {
            addEntities(world.getActualEntities());
        }
        entityAddBuffer.add(entity);
    }

    public List<GameEntity> getTotalEntities() {
        return totalEntities;
    }

    public void update(float delta) {
        for (GameEntity entity : totalEntities) {
            float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
            if (entity.getType() == GameManager.ENTITY_TYPE.BACKGROUND || (entity.getX() > cameraBounds[0] - 800 && entity.getX() < cameraBounds[1] + 800)) // Solo se actualiza si es un fondo o si estñá dentro de la cámara.
                actualEntities.add(entity);
        }

        if (world.hasToCreateNewMatrixRight()) {
            world.repositionRight(totalEntities);
        }
        else if (world.hasToCreateNewMatrixLeft()) {
            world.repositionLeft(totalEntities);
        }

        for (GameEntity entity : actualEntities) {
            float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
            if (entity.getType() == GameManager.ENTITY_TYPE.BACKGROUND || (entity.getX() > cameraBounds[0] - 500 && entity.getX() < cameraBounds[1] + 500)) // Solo se actualiza si es un fondo o si estñá dentro de la cámara.
                entity.update(delta);
        }

        Iterator<GameEntity> iterator = getTotalEntities().iterator();

        while (iterator.hasNext()) {
            if (iterator.next().hasToDelete()) {
                iterator.remove();
            }
        }

        totalEntities.addAll(entityAddBuffer);
        entityAddBuffer.clear();

        checkCollisions();
        actualEntities.clear();
    }

    public void draw(SpriteBatch batch) {
        for (GameEntity entity : totalEntities) {
            float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
            if (entity.getType() == GameManager.ENTITY_TYPE.BACKGROUND || (entity.getX() > cameraBounds[0] - 500 && entity.getX() < cameraBounds[1] + 500)) // Solo se actualiza si es un fondo o si estñá dentro de la cámara.
                actualEntities.add(entity);
        }

        GameEntity character = null;
        List<GameEntity> enemies = new ArrayList<GameEntity>();
        List<GameEntity> scenario = new ArrayList<GameEntity>();

        for (GameEntity entity : actualEntities) {
            float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
            if (entity.getType() == GameManager.ENTITY_TYPE.BACKGROUND || (entity.getX() > cameraBounds[0] - 500 && entity.getX() < cameraBounds[1] + 500)) {
                if (entity.getType() == GameManager.ENTITY_TYPE.CHARACTER) {
                    character = entity;
                } else if (entity.getType() == GameManager.ENTITY_TYPE.ENEMY) {
                    enemies.add(entity);
                } else {
                    entity.draw(batch);
                }

                if (entity.getType() == GameManager.ENTITY_TYPE.BUSH || entity.getType() == GameManager.ENTITY_TYPE.BUSH_DRIED || entity.getType() == GameManager.ENTITY_TYPE.CACTUS || entity.getType() == GameManager.ENTITY_TYPE.CACTUS_FAT || entity.getType() == GameManager.ENTITY_TYPE.CACTUS_3 || entity.getType() == GameManager.ENTITY_TYPE.CORPSE || entity.getType() == GameManager.ENTITY_TYPE.GRASS || entity.getType() == GameManager.ENTITY_TYPE.GRASS_DRIED || entity.getType() == GameManager.ENTITY_TYPE.STONE || entity.getType() == GameManager.ENTITY_TYPE.TREE) {
                    scenario.add(entity);
                }
            }
        }

        for (GameEntity entity : scenario) {
            entity.draw(batch);
        }

        for (GameEntity entity : enemies)
            entity.draw(batch);

        if (character != null) character.draw(batch);

        actualEntities.clear();

    }

    private void checkCollisions() {

        for (int i = 0; i < actualEntities.size() - 1; i++) {
            for (int j = i + 1; j < actualEntities.size(); j++) {
                float [] cameraBounds = GameManager.getInstance().getPlayerCamera().getBounds();
                if (actualEntities.get(i).getType() == GameManager.ENTITY_TYPE.BACKGROUND || ((actualEntities.get(i).getX() > cameraBounds[0] - 500 && actualEntities.get(i).getX() < cameraBounds[1] + 500)))
                {
                    if (actualEntities.get(i).getColliders() != null && actualEntities.get(j).getColliders() != null) { // Comprueba si hay colliders en esas entidades
                        for (Collider collider1 : actualEntities.get(i).getColliders()) {
                            for (Collider collider2 : actualEntities.get(j).getColliders()) {
                                if (collider1 != collider2 && collider1.checkCollision(collider2)) {
                                    CollisionData collisionData = new CollisionData(actualEntities.get(i), collider1, actualEntities.get(j), collider2);
                                    EventData eventData = new EventData(EventData.EVENT_TYPE.COLLISION_EVENT, collisionData);
                                    GameManager.getInstance().sendEvent(eventData);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public List<GameEntity> getEntityAddBuffer() {
        return entityAddBuffer;
    }

    public void beginWorldGeneration() {
        world = new WorldGenerator();
    }
}
