package com.mygdx.game.movement;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.mygdx.game.GameManager;
import com.mygdx.game.collision.Collider;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.Character;
import com.mygdx.game.events.CollisionData;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.events.InputData;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;

public class CharacterMovement extends Movement implements EventListener{



    public static float GRAVITY = -0.8f;

    private float velX = 5;

    private float oldY = 0;

    private int lastDirection = 1;

    private float maxVelY = -10;

    private boolean blockLeftDirection = false;
    private boolean blockRightDirection = false;

    private boolean inmune = false;
    private boolean hit = false;
    private float inmuneTime = 5f;

    private float jumpForce = 0;
    private float hitForce = 0;

    private Character character;

    private boolean throwPlath = false;
    private float throwPlathTime = 0.7f;

    private boolean onFloor = false;
    private boolean onIce = false;
    private boolean slidingR = false;
    private boolean slidingL = false;

    private boolean doubleJump = false;

    public CharacterMovement(GameEntity entity, GameManager gameManager, int x, int y) {
        super(entity, gameManager);
        character = (Character) entity;
        this.x = x;
        this.y = y;
        state = state.FALLING;
        gameManager.addEventListener(this);
    }

    @Override
    public void update(float delta) {

        hit = false;
        switch (state) {
            case FALLING:
                fall();
                onFloor = false;
                break;
            case GROUND:
                y = oldY;
                velY = 0;
                break;
            case JUMPING:
                jumpForce = 6.5f;
                velY = jumpForce;
                break;
            case HIT:

                break;
        }


        x += (directionXR + directionXL) * delta * GameInfo.PPM * hitForce;
        if (state != state.GROUND) // Faltaba esta línea
            y += velY * delta * GameInfo.PPM;

        state = state.FALLING;

        hitForce = 1;
        if (inmune) checkInmuneTime();
        if (throwPlath) checkThrowPlatTimer();
        if (slidingR) {
            directionXR -= 0.1;
            if (directionXR <= 0) {
                directionXR = 0;
                slidingR = false;
            }
        }
        else if (slidingL) {
            directionXL += 0.1;
            if (directionXL >= 0) {
                directionXL = 0;
                slidingL = false;
            }
        }

        //Checkear que no se salga por la izq de la pantalla
        if (entity.getX() < -40) {
            directionXL = 0;
        }

        //Checkear muerte por caida
        if (entity.getY() < -100) {
            if (!entity.isDeath()) {
                character.playDieSound();
                GameManager.getInstance().addTimesDeath();
                addTimePlayed();

                GameManager.getInstance().writeStatistics();
            }
            entity.setDeath(true);
            directionXL = 0;
            directionXR = 0;
        }

    }

    private void checkThrowPlatTimer() {
        throwPlathTime -= 0.1;
        if (throwPlathTime <= 0) {
            throwPlath = false;
            throwPlathTime = 0.7f;
        }
    }

    private void checkInmuneTime() {
        inmuneTime -= 0.1;
        if (inmuneTime <= 0) {
            inmune = false;
            inmuneTime = 5f;
        }
    }

    private void fall() {
        velY += GRAVITY + jumpForce;

        if (velY < maxVelY)
            velY = maxVelY;

        jumpForce = 0;
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case KEYDOWN_EVENT:
                InputData kdData = (InputData) eventData.eventData;
                if ((kdData.keyCode == Input.Keys.RIGHT || kdData.keyCode == -1) && !blockRightDirection && !entity.isDeath()) {
                    if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") && !((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused()) {
                        directionXR = velX;
                        directionXL = 0;
                        blockLeftDirection = false;
                        lastDirection = 1;
                        character.stopSlideSound();
                    }
                } else if ((kdData.keyCode == Input.Keys.LEFT || kdData.keyCode == -2) && !blockLeftDirection && !entity.isDeath() && entity.getX() > -40) {
                    if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") && !((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused()) {
                        directionXL = -velX;
                        blockRightDirection = false;
                        directionXR = 0;
                        lastDirection = -1;
                        character.stopSlideSound();
                    }
                } else if ((kdData.keyCode == Input.Keys.UP || kdData.keyCode == 0) && (state == state.GROUND || !doubleJump) && !entity.isDeath()) {
                    if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") &&!((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused()) {
                        character.playJumpSound();
                        if (state != state.GROUND)
                            doubleJump = true;
                        state = state.JUMPING;
                    }
                } else if ((kdData.keyCode == Input.Keys.DOWN || kdData.keyCode == -3)  && !entity.isDeath()) {
                    if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") &&!((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused()) {
                        throwPlath = true;
                    }
                } else if ((kdData.keyCode == Input.Keys.SPACE || kdData.keyCode == 1) && entity.isDeath()) {
                    if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") &&!((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused()) {
                        Screen screen = new MainMenuScreen(gameManager);
                        GameManager.getInstance().setScreen(screen);
                        entity.setDeath(false);
                        inmune = true;
                        gameManager.stopMusic();
                        gameManager.playMainMenuMusic();
                    }
                } else if (((kdData.keyCode == Input.Keys.ESCAPE || kdData.keyCode == 7) && !entity.isDeath()) && GameManager.getInstance().getScreen() instanceof GameScreen) {
                    if (((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isOptionMenu()){
                        ((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().closeMenuOption();
                        ((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().getOptionsStage().resetPosition();
                    }

                    else {
                        if (gameManager.getScreen().getClass().getName().equals("com.mygdx.game.screens.GameScreen") && !((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().isGamePaused())
                            //((GameScreen)GameManager.getInstance().getScreen()).getHUDCAMERA().pauseGame();
                            GameManager.getInstance().getActiveScreen().pause();

                        else {
                            GameManager.getInstance().getActiveScreen().resume();
                        }
                    }
                }

                break;
            case KEYUP_EVENT:
                InputData kuData = (InputData) eventData.eventData;
                if (kuData.keyCode == Input.Keys.RIGHT || (kuData.keyCode == -5 && directionXR > 0)) {
                    if (!onIce)
                        directionXR = 0;
                    else {
                        slidingR = true;
                        //character.stopSlideSound();
                        character.playSlideSound();
                    }
                    blockRightDirection = false; // QUitar los bloqueos de aqui para evitar rebotes laterales
                    blockLeftDirection = false;
                    //lastDirection = 1;
                } else if (kuData.keyCode == Input.Keys.LEFT || (kuData.keyCode == -5 && directionXL < 0)) {
                    if (!onIce)
                        directionXL = 0;
                    else {
                        slidingL = true;
                        //character.stopSlideSound();
                        character.playSlideSound();
                    }

                    blockRightDirection = false;
                    blockLeftDirection = false;
                    //lastDirection = -1;
                }
                break;
            case COLLISION_EVENT:
                CollisionData cData = (CollisionData) eventData.eventData;
                Collider playerCollider;
                Collider otherCollider;

                if (cData.entityA.getType() == GameManager.ENTITY_TYPE.CHARACTER) {
                    playerCollider = cData.colliderA;
                    otherCollider = cData.colliderB;
                    checkCollisionType(playerCollider, otherCollider);
                } else if (cData.entityB.getType() == GameManager.ENTITY_TYPE.CHARACTER){
                    playerCollider = cData.colliderB;
                    otherCollider = cData.colliderA;
                    checkCollisionType(playerCollider, otherCollider);
                }
                break;
            case ADD_SCORE_EVENT:
                GameEntity enemyEntity = (GameEntity) eventData.eventData;

                if(enemyEntity.getTag().equals("ork1")) {
                    Ork ork = (Ork) enemyEntity;
                    character.addPoints(ork.getPoints());
                }
                else if(enemyEntity.getTag().equals("troll")) {
                    Troll troll = (Troll) enemyEntity;
                    character.addPoints(troll.getPoints());
                }
                else if(enemyEntity.getTag().equals("bird")) {
                    Bird bird = (Bird) enemyEntity;
                    character.addPoints(bird.getPoints());
                }
        }
    }

    private void checkCollisionType(Collider playerCollider, Collider otherCollider) {
        feetCollision(playerCollider, otherCollider);  //Colision con el suelo si esta cayendo
        if (!lateralCollision(playerCollider, otherCollider))  //Colision lateral
            if (!headCollision(playerCollider, otherCollider)) // Colision con la cabeza en un bloque
                if (!enemyCollision(playerCollider, otherCollider)) // Colision con el enemigo
                    if (!coinCollision(playerCollider, otherCollider)) // COlision con las monedas
                        heartCollision(playerCollider, otherCollider); // Colision con los corazones
    }

    private boolean headCollision(Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if (playerCollider.getTag().equals("head") && otherCollider.getTag().equals("block")  && velY > 0) {
            velY = 0;
        }
        return collision;
    }

    private boolean feetCollision(Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if (playerCollider.getTag().equals("feet") && (otherCollider.getTag().equals("platform") || otherCollider.getTag().equals("ground")) && velY <= 0) {
            doubleJump = false;
            if (!throwPlath) { // Si NO se puede atravesar la plataforma
                state = state.GROUND;
                oldY = otherCollider.getY() + otherCollider.getHeight() - 15;
                collision = true;
                if (otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.ICE_PLATFORM)
                    onIce = true;
                else
                    onIce = false;
                if (otherCollider.getTag().equals("ground")) {
                    onFloor = true;
                }
            } else { // Si se puede ataravesar, hay que mirar si es un bloque o no, ya que de lo cotrario podriamos atravesarlo.
                if (otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.BLOCK || otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.GROUND) {
                    state = state.GROUND;
                    oldY = otherCollider.getY() + otherCollider.getHeight() - 15;
                    collision = true;
                }
            }
        }


        return collision;
    }

    private boolean lateralCollision(Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getTag().equals("block")) {
            if (playerCollider.getTag().equals("lateralL")) { // lateral Izquierdo
                if (x < otherCollider.getX() + otherCollider.getWidth()) {
                    x = otherCollider.getX() + otherCollider.getWidth() - 45;
                    blockLeftDirection = true;
                    //directionXL = 0;
                }
            }
            else if (playerCollider.getTag().equals("lateralR")){   // Lateral derecho
                if (x + entity.getWidth() > otherCollider.getX()) {
                    x = otherCollider.getX() - entity.getWidth() + 45;
                    blockRightDirection = true;
                    //directionXR = 0;
                }
            }

            collision = true;
        }

        return collision;
    }

    private boolean enemyCollision(Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getTag().equals("enemy") && !inmune) {
            if (!otherCollider.getGameEntity().isDeath() && !entity.isDeath()) {
                collision = true;
                state = state.HIT;
                hitForce = -10;
                velY = 10f;
                hit = true;
                inmune = true;
                if (otherCollider.getGameEntity().getTag().equals("ork1"))
                    entity.setHealth(entity.getHealth() - 30);
                else if (otherCollider.getGameEntity().getTag().equals("troll"))
                    entity.setHealth(entity.getHealth() - 45);
                else if (otherCollider.getGameEntity().getTag().equals("bird"))
                    entity.setHealth(entity.getHealth() - 15);

                character.playHurtSound();

                if (entity.getHealth() <= 0) {
                    entity.setDeath(true);
                    character.playDieSound();
                    GameManager.getInstance().addTimesDeath();
                    addTimePlayed();
                    GameManager.getInstance().writeStatistics();
                }
            }
        }

        return collision;
    }

    private boolean coinCollision(Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.BRONZE_COIN && otherCollider.getGameEntity().isPositioned()) {
            BronzeCoin bronzeCoin = (BronzeCoin) otherCollider.getGameEntity();
            gameManager.getCharacter().addCoins(bronzeCoin.getScore());
            //otherCollider.getGameEntity().delete();
            otherCollider.getGameEntity().setPosition(-1000, -1000);
            otherCollider.getGameEntity().setPositioned(false);
            collision = true;

            bronzeCoin.playSound();
        }
        else if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.SILVER_COIN && otherCollider.getGameEntity().isPositioned()) {
            SilverCoin silverCoin = (SilverCoin) otherCollider.getGameEntity();
            gameManager.getCharacter().addCoins(silverCoin.getScore());
            //otherCollider.getGameEntity().delete();
            otherCollider.getGameEntity().setPosition(-1000, -1000);
            otherCollider.getGameEntity().setPositioned(false);
            collision = true;

            silverCoin.playSound();


        } else if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.GOLD_COIN && otherCollider.getGameEntity().isPositioned()) {
            GoldCoin goldCoin = (GoldCoin) otherCollider.getGameEntity();
            gameManager.getCharacter().addCoins(goldCoin.getScore());
            //otherCollider.getGameEntity().delete();
            otherCollider.getGameEntity().setPosition(-1000, -1000);
            otherCollider.getGameEntity().setPositioned(false);
            collision = true;

            goldCoin.playSound();
        }

        return collision;
    }

    public boolean heartCollision (Collider playerCollider, Collider otherCollider) {
        boolean collision = false;

        if ((playerCollider.getTag().equals("lateralL") || playerCollider.getTag().equals("lateralR")) && otherCollider.getGameEntity().getType() == GameManager.ENTITY_TYPE.HEART && otherCollider.getGameEntity().isPositioned()) {
            Heart heart = (Heart) otherCollider.getGameEntity();
            gameManager.getCharacter().addHealth(heart.getLife());
            otherCollider.getGameEntity().setPosition(-1000, -1000);
            otherCollider.getGameEntity().setPositioned(false);
            collision = true;

            character.playLifeSound();
        }

        return collision;
    }

    public float getDirection () {
        return lastDirection;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isOnFloor() {
        return onFloor;
    }

    public boolean isSliding() {
        if (slidingL || slidingR) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addTimePlayed() {
        long endTime   = System.currentTimeMillis() / 1000;
        long totalTime = endTime - ((GameScreen)GameManager.getInstance().getActiveScreen()).getStartTime();
        System.out.println("Total time: " + totalTime);
        GameManager.getInstance().addTimePlayed(totalTime);
    }

    public boolean isBlockLeftDirection() {
        return blockLeftDirection;
    }

    public boolean isBlockRightDirection() {
        return blockRightDirection;
    }

    public boolean isDoubleJump() {
        return doubleJump;
    }
}
