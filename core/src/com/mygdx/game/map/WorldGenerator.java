package com.mygdx.game.map;

import com.mygdx.game.GameManager;
import com.mygdx.game.camera.PlayerCamera;
import com.mygdx.game.entities.*;
import com.mygdx.game.helpers.GameInfo;

import java.util.ArrayList;
import java.util.List;

public class WorldGenerator {

    private static final int MATRIX_WIDTH = 5000;

    private int index = 0;
    private ArrayList<TileMatrix> maps;
    private ArrayList<GameEntity> createdEntities;
    private List<GameEntity> actualEntities;
    private int maxXgenerated = 0;

    public WorldGenerator() {
        maps = new ArrayList<TileMatrix>();
        createdEntities = new ArrayList<GameEntity>();
        actualEntities = new ArrayList<GameEntity>();
        actualEntities = generateSprites();
        createFirstTwoMaps();
    }

    private void createFirstTwoMaps() {
        for (int i = 0; i < 2; i++) {
            TileMatrix map = new TileMatrix(index, 6, 10);
            maps.add(map);


            for (Tile tile : map.getTiles()) {
                switch (tile.getId()) {
                    case 1:
                        int platformType = (int) Math.floor(Math.floor(Math.random() * (5 - 0 + 1)) + 0);

                        if (platformType != 5) {

                            Platform platform = new Platform(tile.getY() * GameInfo.TILE_X + (tile.getEntityX() * 100) + (index * MATRIX_WIDTH), tile.getX() * GameInfo.TILE_Y + 110, tile.getEntityWidth(), 70, "Platforms/Block.png");
                            platform.setOffSetX(tile.getEntityX());
                            actualEntities.add(platform);

                            // Monedas
                            createCoins(tile, platform, actualEntities);
                            createEnemies(tile, platform, actualEntities);
                            createScenario(tile, platform, actualEntities);
                        }
                        break;
                    case 2:
                        Block block = new Block(tile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH), tile.getX() * GameInfo.TILE_Y, 500, 180, "Platforms/Block.png");

                        actualEntities.add(block);
                        createScenario(tile, block, actualEntities);
                        break;
                    case 3:
                        Ground ground = new Ground(tile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH), tile.getX() * GameInfo.TILE_Y, 500, 180, "Platforms/floor.png");

                        actualEntities.add(ground);

                        createEnemies(tile, ground, actualEntities);
                        createScenario(tile, ground, actualEntities);
                        break;


                }
            }

            index++;
        }
    }

    // Recibe las entidades de enviormnet y las modifica para que solo contenga las necesarias
    public void repositionLeft(List<GameEntity> enviormentEntities) {
        TileMatrix map = maps.get(index - 3);
        for (Tile newTile : map.getTiles()) {
            if (newTile.getId() != 0) {

                for (GameEntity entity : enviormentEntities) {
                    // Revisar los ifs por el tema de la posicion de la camara
                    if (entity.getType() == GameManager.ENTITY_TYPE.BLOCK && newTile.getId() == 2 && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + ((index - 3) * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y);
                        createScenario(newTile, entity, enviormentEntities);
                        break;
                    } else if (entity.getType() == GameManager.ENTITY_TYPE.GROUND && newTile.getId() == 3  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + ((index - 3) * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y);
                        createScenario(newTile, entity, enviormentEntities);
                        break;
                    } else if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM && newTile.getId() == 1 && entity.getOffSetX() == newTile.getEntityX()  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + (newTile.getEntityX() * 100) + ((index - 3) * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y + 110);
                        break;
                    } else if (entity.getType() == GameManager.ENTITY_TYPE.BUSH  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.BUSH_DRIED  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS_FAT  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS_3  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.CORPSE  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.GRASS  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.GRASS_DRIED  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.STONE  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.TREE  && entity.getX() - GameManager.getInstance().getPlayerCamera().getBounds()[1] > 4500) {
                        entity.setPositioned((false));
                        break;
                    }
                }
            }
        }

        index--;
    }

    // Recibe las entidades de enviormnet y las modifica para que solo contenga las necesarias
    public void repositionRight(List<GameEntity> enviormentEntities) {
        // Aqui hay que generar un mapa y una vez hecho, hay que comoprobar antes de crear nuevas entidades, si ya hay similares en el matrix anterior al actual
        // EN caso afirtmativo se reposicionan, en caso negativo se crean
        TileMatrix map;

        if (index == maps.size()) {
            map = new TileMatrix(index, 6, 10);
            maps.add(map);
        } else {
            map = maps.get(index);
        }

        for (Tile newTile : map.getTiles()) {
            if (newTile.getId() != 0) {

                boolean founded = false;
                for (GameEntity entity : enviormentEntities) {

                    if (entity.getType() == GameManager.ENTITY_TYPE.BLOCK && newTile.getId() == 2 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y);
                        createScenario(newTile, entity, enviormentEntities);
                        founded = true;

                        break;
                    } else if (entity.getType() == GameManager.ENTITY_TYPE.GROUND && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y);
                        founded = true;
                        createScenario(newTile, entity, enviormentEntities);
                        break;

                    } else if (entity.getType() == GameManager.ENTITY_TYPE.BUSH && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.BUSH_DRIED && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }
                    else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS_FAT && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.CACTUS_3 && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.CORPSE && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.GRASS && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.GRASS_DRIED && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.STONE && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.TREE && newTile.getId() == 3 && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setPositioned((false));
                        break;
                    }else if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM && newTile.getId() == 1 && entity.getOffSetX() == newTile.getEntityX() && GameManager.getInstance().getPlayerCamera().getBounds()[0] - entity.getX() > 5000) {
                        entity.setX(newTile.getY() * GameInfo.TILE_X + (newTile.getEntityX() * 100) + (index * MATRIX_WIDTH));
                        entity.setY(newTile.getX() * GameInfo.TILE_Y + 110);
                        founded = true;

                        if (index == maps.size() - 1) {
                            // Monedas
                            createCoins(newTile, entity, enviormentEntities);
                            createEnemies(newTile, entity, enviormentEntities);
                            createScenario(newTile, entity, enviormentEntities);
                        }
                        break;
                    }

                }

                if (!founded) {
                    switch (newTile.getId()) {
                        case 1:
                            Platform platform = new Platform(newTile.getY() * GameInfo.TILE_X + (newTile.getEntityX() * 100) + (index * MATRIX_WIDTH), newTile.getX() * GameInfo.TILE_Y + 110, newTile.getEntityWidth(), 70, "Platforms/Block.png");
                            newTile.setEntity(platform);
                            platform.setOffSetX(newTile.getEntityX());
                            enviormentEntities.add(platform);

                            if (index == maps.size() - 1) {
                                // Monedas
                                createCoins(newTile, platform, enviormentEntities);
                                createEnemies(newTile, platform, enviormentEntities);
                                createScenario(newTile, platform, enviormentEntities);
                            }
                            break;
                        case 2:
                            Block block = new Block(newTile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH), newTile.getX() * GameInfo.TILE_Y, 500, 180, "Platforms/Block.png");
                            newTile.setEntity(block);
                            enviormentEntities.add(block);
                            createScenario(newTile, block, actualEntities);
                            break;
                        case 3:
                            Ground ground = new Ground(newTile.getY() * GameInfo.TILE_X + (index * MATRIX_WIDTH), newTile.getX() * GameInfo.TILE_Y, 500, 180, "Platforms/floor.png");
                            newTile.setEntity(ground);
                            enviormentEntities.add(ground);
                            createEnemies(newTile, ground, enviormentEntities);
                            createScenario(newTile, ground, enviormentEntities);
                            break;
                    }
                }
            }
        }

        index++;
    }

    public boolean hasToCreateNewMatrixRight() {
        boolean hasToCreate = false;
        PlayerCamera camera = GameManager.getInstance().getPlayerCamera();
        if ((index * MATRIX_WIDTH) - camera.getBounds()[1] < 300 && index != 0) {
            hasToCreate = true;
        }


        return hasToCreate;
    }

    public boolean hasToCreateNewMatrixLeft() {
        boolean hasToCreate = false;
        PlayerCamera camera = GameManager.getInstance().getPlayerCamera();
        if (camera.getBounds()[0] - ((index - 2) * MATRIX_WIDTH) < 300 && index - 2 != 0) {
            hasToCreate = true;
        }


        return hasToCreate;
    }

    public void createCoins(Tile tile, GameEntity entity, List<GameEntity> enviormentEntities) {
        // Randomizers de las monedas
        int spawnCoins = (int) Math.floor(Math.floor(Math.random() * (1 - 0 + 1)) + 0);
        if (spawnCoins == 1) { // Toca poenr monedas

            int coinType = (int) Math.floor(Math.floor(Math.random() * (3 - 1 + 1)) + 1);
            int numCoins;

            switch (coinType) {
                // Oro
                case 1:
                    if (tile.getX() > 3) {
                        int coinOrHearth = (int) Math.floor(Math.floor(Math.random() * (1 - 0 + 1)) + 0);
                        for (GameEntity ge : enviormentEntities) {
                            if (coinOrHearth == 0 && ge.getType() == GameManager.ENTITY_TYPE.GOLD_COIN && !ge.isPositioned()) {
                                ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25, (int) entity.getY() + 100);
                                ge.setPositioned(true);
                                break;
                            }
                            else if (coinOrHearth == 1 && ge.getType() == GameManager.ENTITY_TYPE.HEART && !ge.isPositioned()) {
                                ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25, (int) entity.getY() + 100);
                                ge.setPositioned(true);
                                break;
                            }
                        }
                        //enviormentEntities.add(new GoldCoin((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25, (int) entity.getY() + 100, 25, 25, "badlogic.jpg"));
                    }
                    break;
                // Plata
                case 2:
                    if (tile.getX() <= 3) {
                        numCoins = (int) Math.floor(Math.floor(Math.random() * (3 - 2 + 1)) + 2);
                        int offSetX = 0;
                        for (int j = 0; j < numCoins; j++) {
                            if (numCoins == 2) { // Mostrar 2
                                for (GameEntity ge : enviormentEntities) {
                                    if (ge.getType() == GameManager.ENTITY_TYPE.SILVER_COIN && !ge.isPositioned()) {
                                        ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25 - 50 + offSetX, (int) entity.getY() + 100);
                                        ge.setPositioned(true);
                                        break;
                                    }
                                }
                                //enviormentEntities.add(new SilverCoin((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25 - 50 + offSetX, (int) entity.getY() + 100, 25, 25, "badlogic.jpg"));
                            }
                            else if (numCoins == 3) { // Mostrar 3
                                for (GameEntity ge : enviormentEntities) {
                                    if (ge.getType() == GameManager.ENTITY_TYPE.SILVER_COIN && !ge.isPositioned()) {
                                        ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25 - 100 + offSetX, (int) entity.getY() + 100);
                                        ge.setPositioned(true);
                                        break;
                                    }
                                }
                                //enviormentEntities.add(new SilverCoin((int) (entity.getX() + (entity.getWidth() * 100) / 2) - 25 - 100 + offSetX, (int) entity.getY() + 100, 25, 25, "badlogic.jpg"));
                            }
                            offSetX += 100;
                        }
                    }
                    break;
                // Bronze
                case 3:
                    numCoins = (int) Math.floor(Math.floor(Math.random() * (5 - 3 + 1)) + 3);
                    int offSetX = 0;
                    int offsetY = 0;
                    for (int j = 0; j < numCoins; j++) {
                        if (numCoins != entity.getWidth()) {
                            for (GameEntity ge : enviormentEntities) {
                                if (ge.getType() == GameManager.ENTITY_TYPE.BRONZE_COIN && !ge.isPositioned()) {
                                    ge.setPosition((int) (entity.getX() + /*(entity.getWidth() * 100) +*/ offSetX), (int) entity.getY() + 100 + offsetY);
                                    ge.setPositioned(true);
                                    break;
                                }
                            }
                            //enviormentEntities.add(new BronzeCoin((int) (entity.getX() + (entity.getWidth() * 100) + offSetX), (int) entity.getY() + 100 + offsetY, 25, 25, "badlogic.jpg"));
                            offSetX += 50;
                            offsetY += 50;
                        }
                        else {
                            for (GameEntity ge : enviormentEntities) {
                                if (ge.getType() == GameManager.ENTITY_TYPE.BRONZE_COIN && !ge.isPositioned()) {
                                    ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int)(entity.getWidth() * 50) + offSetX + 25, (int) entity.getY() + 100);
                                    ge.setPositioned(true);
                                    break;
                                }
                            }
                            //enviormentEntities.add(new BronzeCoin((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int)(entity.getWidth() * 50) + offSetX + 25, (int) entity.getY() + 100, 25, 25, "badlogic.jpg"));
                            offSetX += 100;
                        }

                    }
                    break;
            }
        }
    }

    public void createEnemies(Tile tile, GameEntity entity, List<GameEntity> enviormentEntities) {
        if (entity.getX() > 500) {
            int enemyChance = (int) Math.floor(Math.floor(Math.random() * ((maps.size() + 3) - 0 + 1)) + 0);
            if (enemyChance >= 4) {

                int enemyType = (int) Math.floor(Math.floor(Math.random() * (2 - 0 + 1)) + 0);

                for (GameEntity ge : enviormentEntities) {
                    if (enemyType == 0 && tile.getId() == 1) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.ENEMY && ge.getTag().equals("ork1") && !ge.isPositioned()) {
                            ge.getMovement().setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + 100);
                            ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + 100);
                            ge.setPositioned(true);
                            break;

                        }
                    }
                    else if (enemyType == 1) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.ENEMY && ge.getTag().equals("troll") && !ge.isPositioned()) {
                            ge.getMovement().setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + 100);
                            ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + 100);
                            ge.setPositioned(true);
                            break;

                        }
                    }
                    else if (enemyType == 2 && tile.getId() == 1) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.ENEMY && ge.getTag().equals("bird") && !ge.isPositioned()) {
                            ge.getMovement().setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + entity.getHeight() + 120);
                            ge.setPosition((int) (entity.getX() + (entity.getWidth() * 100) / 2) - (int) (entity.getWidth() * 50) + 25, (int) entity.getY() + entity.getHeight() + 120);
                            ge.setPositioned(true);
                            break;

                        }
                    }
                }
            }
        }
    }

    public void createScenario(Tile tile, GameEntity entity, List<GameEntity> enviormentEntities) {
        int spawnScenario = (int) Math.floor(Math.floor(Math.random() * (1 - 0 + 1)) + 0);
        //if (spawnScenario == 1) { // Toca poenr monedas

            int ambientType = (int) Math.floor(Math.floor(Math.random() * (10 - 1 + 1)) + 1);
            int offsetX = 0;
            switch (ambientType) {

                // Bush
                case 1:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.BUSH && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 70);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 180);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Bush dried
                case 2:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.BUSH_DRIED && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 70);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 180);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;

                // Cactus
                case 3:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.CACTUS && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 70);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 180);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Cactus Fat
                case 4:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.CACTUS_FAT && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 70);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 180);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Cactus 3
                case 5:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.CACTUS_3 && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 70);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 180);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Corpse
                case 6:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.CORPSE && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX - 50), (int) entity.getY() + 65);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX - 50), (int) entity.getY() + 175);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Grass
                case 7:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.GRASS && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 65);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 175);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Grass Dried
                case 8:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.GRASS_DRIED && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 65);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 175);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Stone
                case 9:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.STONE && !ge.isPositioned()) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 65);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 175);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
                // Tree
                case 10:
                    for (GameEntity ge : enviormentEntities) {
                        if (ge.getType() == GameManager.ENTITY_TYPE.TREE && !ge.isPositioned() && entity.getType() == GameManager.ENTITY_TYPE.GROUND) {
                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                offsetX = (int) Math.floor(Math.floor(Math.random() * (((entity.getWidth() * 100) - 100) - 0 + 1)) + 0);
                            else
                                offsetX = (int) Math.floor(Math.floor(Math.random() * ((entity.getWidth() - 100) - 0 + 1)) + 0);

                            if (entity.getType() == GameManager.ENTITY_TYPE.PLATFORM)
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 65);
                            else
                                ge.setPosition((int) (entity.getX() + offsetX), (int) entity.getY() + 175);

                            ge.setPositioned(true);
                            break;
                        }
                    }
                    break;
            }
        //}
    }

    public List<GameEntity> getActualEntities() {
        return actualEntities;
    }


    public ArrayList<GameEntity> getCreatedEntities() {
        return createdEntities;
    }

    public List<GameEntity> generateSprites() {
        List<GameEntity> scenarioEntities = new ArrayList<GameEntity>();

        //SCENARIO
        for (int i = 0; i < 10; i++) {
            Bush bush = new Bush(-1000, -1000, 150, 100, "ambient/bush.png");
            bush.setPositioned(false);
            scenarioEntities.add(bush);
        }

        for (int i = 0; i < 10; i++) {
            BushDried bushDried = new BushDried(-1000, -1000, 150, 100, "ambient/bush_dried.png");
            bushDried.setPositioned(false);
            scenarioEntities.add(bushDried);
        }

        for (int i = 0; i < 10; i++) {
            Cactus cactus = new Cactus(-1000, -1000, 150, 220, "ambient/cactus.png");
            cactus.setPositioned(false);
            scenarioEntities.add(cactus);
        }

        for (int i = 0; i < 10; i++) {
            CactusFat cactusFat = new CactusFat(-1000, -1000, 75, 75, "ambient/cactus_fat.png");
            cactusFat.setPositioned(false);
            scenarioEntities.add(cactusFat);
        }

        for (int i = 0; i < 10; i++) {
            Cactus3 cactus3 = new Cactus3(-1000, -1000, 150, 220, "ambient/cactus_3.png");
            cactus3.setPositioned(false);
            scenarioEntities.add(cactus3);
        }

        for (int i = 0; i < 10; i++) {
            Corpse corpse = new Corpse(-1000, -1000, 150, 75, "ambient/corpse.png");
            corpse.setPositioned(false);
            scenarioEntities.add(corpse);
        }

        for (int i = 0; i < 10; i++) {
            Grass grass = new Grass(-1000, -1000, 150, 100, "ambient/grass.png");
            grass.setPositioned(false);
            scenarioEntities.add(grass);
        }

        for (int i = 0; i < 10; i++) {
            GrassDried grassDried = new GrassDried(-1000, -1000, 150, 100, "ambient/grass_dried.png");
            grassDried.setPositioned(false);
            scenarioEntities.add(grassDried);
        }

        for (int i = 0; i < 10; i++) {
            Stone stone = new Stone(-1000, -1000, 150, 100, "ambient/stone.png");
            stone.setPositioned(false);
            scenarioEntities.add(stone);
        }

        for (int i = 0; i < 10; i++) {
            Tree tree = new Tree(-1000, -1000, 400, 450, "ambient/tree.png");
            tree.setPositioned(false);
            scenarioEntities.add(tree);
        }

        // COINS
        for (int i = 0; i < 40; i++) {
            BronzeCoin bronzeCoin = new BronzeCoin(-1000, -1000, 25, 25, "badlogic.jpg");
            bronzeCoin.setPositioned(false);
            scenarioEntities.add(bronzeCoin);
        }
        for (int i = 0; i < 30; i++) {
            SilverCoin silverCoin = new SilverCoin(-1000, -1000, 25, 25, "badlogic.jpg");
            silverCoin.setPositioned(false);
            scenarioEntities.add(silverCoin);
        }
        for (int i = 0; i < 10; i++) {
            GoldCoin goldCoin = new GoldCoin(-1000, -1000, 25, 25, "badlogic.jpg");
            goldCoin.setPositioned(false);
            scenarioEntities.add(goldCoin);
        }

        //HEARTH
        for (int i = 0; i < 10; i++) {
            Heart heart = new Heart(-1000, -1000, 25, 25, "badlogic.jpg");
            heart.setPositioned(false);
            scenarioEntities.add(heart);
        }

        //ENEMIES
        for (int i = 0; i < 20; i++) {
            Ork ork = new Ork(-1000, -1000, 100, 100, "badlogic.jpg");
            ork.setPositioned(false);
            scenarioEntities.add(ork);
        }

        for (int i = 0; i < 20; i++) {
            Troll troll = new Troll(-1000, -1000, 100, 100, "badlogic.jpg");
            troll.setPositioned(false);
            scenarioEntities.add(troll);
        }

        for (int i = 0; i < 20; i++) {
            Bird bird = new Bird(-1000, -1000, 100, 100, "badlogic.jpg");
            bird.setPositioned(false);
            scenarioEntities.add(bird);
        }


        return scenarioEntities;
    }
}
