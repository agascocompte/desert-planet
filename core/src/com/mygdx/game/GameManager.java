package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.camera.PlayerCamera;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.Character;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.events.InputData;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameManager extends Game implements InputProcessor, ControllerListener {
    private static GameManager instance;
    private Screen activeScreen;
    private SpriteBatch batch;
    private Environment environment;
    private List<EventListener> eventListeners;
    private PlayerCamera camera = null;
    private OrthographicCamera mainMenuCamera = null;
    private int buttonCode = 1000;
    private boolean blockButton = false;
    private Character character;
    private BackgroundParallax background;

    private TextureAtlas atlas;

    private Music mainMenuMusic;
    private Music bgMusic;

    private int timePlayed, enemyKills, deaths, shots;
    private Texture hint;
    private boolean hintSeen = false;

    public void beginWorldGeneration() {
        environment.beginWorldGeneration();
    }

    public enum ENTITY_TYPE {
        BACKGROUND, PLATFORM, ICE_PLATFORM, ENEMY, CHARACTER, BRONZE_COIN, SILVER_COIN, GOLD_COIN, HEART, BLOCK, PROJECTILE, GROUND, BUTTON, BUSH, BUSH_DRIED, CACTUS, CACTUS_FAT, CACTUS_3, CORPSE, GRASS, GRASS_DRIED, STONE, TREE
    }

    private GameManager() {
        eventListeners = new ArrayList<EventListener>();
        mainMenuCamera = new OrthographicCamera();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    @Override
    public void create() {
        environment = new Environment();

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.local("sound/music/mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(0.3f);
        mainMenuMusic.play();

        bgMusic = Gdx.audio.newMusic(Gdx.files.local("sound/music/dessert_bg_music.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.3f);

        activeScreen = new MainMenuScreen(this);
        setScreen(activeScreen);
        Gdx.input.setInputProcessor(this);
        Controllers.addListener(this);
        character = null;

        FileHandle file = Gdx.files.local("save/statistics.json");
        readStatistics(file);

        hint = new Texture(Gdx.files.local("Backgrounds/keys.png"));

    }

    public GameEntity addNewGameEntity(ENTITY_TYPE type, int x, int y, int width, int height, String texturePath) {
        GameEntity newEntity = null;
        switch (type) {
            case CHARACTER:
                //character = null;
                character = new Character(x, y, width, height, texturePath, false);

                character.setHealth(100);
                character.setDeath(false);
                character.setX(0);
                character.setY(200);

                newEntity = character;
                camera = new PlayerCamera(character);
                camera.setToOrtho(false, GameInfo.WIDTH * 1.3f, GameInfo.HEIGHT * 1.3f);


                for (GameEntity entity : environment.getEntityAddBuffer()) {
                    if (entity.getType() == ENTITY_TYPE.BACKGROUND) {
                        BackgroundParallax bg = (BackgroundParallax) entity;
                        bg.setCamera(camera);
                    }
                }

                break;
            case GROUND:
                newEntity = new Ground(x, y, width, height, texturePath);
                break;
            case PLATFORM:
                newEntity = new Platform(x, y, width, height, texturePath);
                break;
            case BLOCK:
                newEntity = new Block(x, y, width, height, texturePath);
                break;
            case ENEMY:
                newEntity = new Ork(x, y, width, height, texturePath);
                break;
            case BRONZE_COIN:
                newEntity = new BronzeCoin(x, y, width, height, texturePath);
                break;
            case SILVER_COIN:
                newEntity = new SilverCoin(x, y, width, height, texturePath);
                break;
            case GOLD_COIN:
                newEntity = new GoldCoin(x, y, width, height, texturePath);
                break;
            case HEART:
                newEntity = new Heart(x, y, width, height, texturePath);
                break;
        }
        if (newEntity != null) {
            environment.addEntity(newEntity);
        }
        return newEntity;
    }

    public GameEntity addNewGameEntity(ENTITY_TYPE type, float x, float y, float direction, String name) {
        GameEntity newEntity = null;
        switch (type) {
            case PROJECTILE:
                if (name.equals("bullet")) {
                    newEntity = new Bullet((int)x, (int)y, (int)direction);
                }
                break;
        }
        if (newEntity != null) {
            environment.addEntity(newEntity);
        }
        return newEntity;
    }

    public GameEntity addNewGameEntity(ENTITY_TYPE type, int x, int y, int width, int height, String texturePath, int layer, float speed) {
        GameEntity newEntity = null;
        switch (type) {
            case BACKGROUND:
                background = new BackgroundParallax(x, y, width, height, texturePath, layer, speed);
                newEntity = background;
                break;
        }
        if (newEntity != null) {
            environment.addEntity(newEntity);
        }
        return newEntity;
    }

    public void deleteGameEntity(GameEntity entity) {
        environment.getTotalEntities().remove(entity);
    }

    public void update(float delta, SpriteBatch batch) {


        if (!((GameScreen) getScreen()).getHUDCAMERA().isGamePaused() && !((GameScreen) getScreen()).getHUDCAMERA().isOptionMenu()) {
            environment.update(delta);
            camera.update();
        }
        environment.draw(batch);
        batch.setProjectionMatrix(camera.combined);

    }

    /*Los fondos tiene que tener el formato tipo: "1.png", "2.png"... siendo el numero más alto la capa más profunda*/
    public void createBackgrounds(String parentPath, int numLayers) {
        float speed = 0;

        int height;
        for (int i = numLayers; i > 0; i--) {
            if (i == numLayers) height = 2500;
            else height = 1080;
            addNewGameEntity(GameManager.ENTITY_TYPE.BACKGROUND, 0, 100, 1920, height, parentPath + "/"+ i + ".png", i, speed);
            addNewGameEntity(GameManager.ENTITY_TYPE.BACKGROUND, -1920, 100, 1920, height, parentPath + "/"+ i + ".png", i, speed);
            addNewGameEntity(GameManager.ENTITY_TYPE.BACKGROUND, 1920, 100, 1920, height, parentPath + "/"+ i + ".png", i, speed);
            if(i > 7) {
                speed = 8f;
            }
            else if (i > 6) {
                speed = 7f;
            }
            else if (i > 5) {
                speed = 6f;
            }
            else if (i > 4) {
                speed = 5f;
            }
            else if (i > 3) {
                speed = 4f;
            }
            else if (i > 2)
                speed = 3f;
            else if (i > 1)
                speed = 2f;
            else
                speed = 1f;
        }
    }

    public void addEventListener(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void removeEventListener(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    public void sendEvent(EventData eventData) {
        //List<EventListener> copia = eventListeners;

        ListIterator<EventListener> iterator = eventListeners.listIterator();

        while(iterator.hasNext()) {
            iterator.next().onEvent(eventData);
        }


        //for (EventListener eventListener : eventListeners) {
        //    eventListener.onEvent(eventData);
        //}
    }
    @Override
    public boolean keyDown(int keycode) {
        InputData inputData = new InputData(keycode);
        EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
        sendEvent(eventData);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        InputData inputData = new InputData(keycode);
        EventData eventData = new EventData(EventData.EVENT_TYPE.KEYUP_EVENT, inputData);
        sendEvent(eventData);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    @Override
    public void dispose () {
        writeStatistics();
        System.out.println("Datos guardados");
        batch.dispose();
        mainMenuMusic.dispose();
        bgMusic.dispose();
    }

    public PlayerCamera getPlayerCamera() {
        return camera;
    }

    public OrthographicCamera getMainMenuCamera() {
        return mainMenuCamera;
    }

    public TextureAtlas getAtlas() {
        if (atlas == null) {
            atlas = new TextureAtlas(Gdx.files.internal("animations/characterAnimation.atlas"));
        }
        return atlas;
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }

    public void setActiveScreen(Screen activeScreen) {
        this.activeScreen = activeScreen;
    }

    public void removeEntities() {
        environment.getTotalEntities().clear();
    }

    public Character getCharacter() {
        return character;
    }



    // COntrolellerrr

    @Override
    public void connected(Controller controller) {
        Gdx.app.log("CONECTADO", controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {

        InputData inputData = new InputData(buttonCode);
        EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
        sendEvent(eventData);
        this.buttonCode = buttonCode;
        blockButton = true;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        InputData inputData = new InputData(buttonCode);
        EventData eventData = new EventData(EventData.EVENT_TYPE.KEYUP_EVENT, inputData);
        sendEvent(eventData);
        this.buttonCode = 1000;
        blockButton = false;
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {

        if (value == PovDirection.east) {
            InputData inputData = new InputData(-1);
            EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
            sendEvent(eventData);
            buttonCode = -1;
        }
        else if (value == PovDirection.west) {
            InputData inputData = new InputData(-2);
            EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
            sendEvent(eventData);
            buttonCode = -2;

        }
        else if (value == PovDirection.south) {
            InputData inputData = new InputData(-3);
            EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
            sendEvent(eventData);
            buttonCode = -3;

        }
        else if (value == PovDirection.north) {
            InputData inputData = new InputData(-4);
            EventData eventData = new EventData(EventData.EVENT_TYPE.KEYDOWN_EVENT, inputData);
            sendEvent(eventData);
            buttonCode = -4;

        }
        else if (value == PovDirection.center) {
            InputData inputData = new InputData(-5);
            EventData eventData = new EventData(EventData.EVENT_TYPE.KEYUP_EVENT, inputData);
            sendEvent(eventData);
            buttonCode = -5;

        }


        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    // Acceso a variables

    public void loadMusic (String path) {
        bgMusic = Gdx.audio.newMusic(Gdx.files.local(path));
    }

    public void playMainMenuMusic() {
        mainMenuMusic.play();
    }

    public void stopMainMenuMusic() {
        mainMenuMusic.stop();
    }

    public Music getMainMenuMusic() {
        return mainMenuMusic;
    }

    public void playMusic () {
        bgMusic.play();
    }

    public void stopMusic () {
        bgMusic.stop();
    }

    public Music getBgMusic() {
        return bgMusic;
    }

    public void readStatistics(FileHandle file) {
        JsonReader jsonReader = new JsonReader();
        JsonValue jv = jsonReader.parse(file.reader());

        timePlayed = jv.get(0).getInt("value");
        enemyKills = jv.get(1).getInt("value");
        shots = jv.get(2).getInt("value");
        deaths = jv.get(3).getInt("value");

        if (timePlayed != 0) {
            hintSeen = true;
        }
    }

    public void writeStatistics() {

            FileHandle file = Gdx.files.local("save/statistics.json");


            String result = "{";
            result += '"' + String.valueOf(1) + '"' + ":{\n" + '"' + "name" + '"' + ":" + '"' + "Time played" + '"' + ",\n" + '"' + "value" + '"' + ":" + timePlayed + "},\n";
            result += '"' + String.valueOf(2) + '"' + ":{\n" + '"' + "name" + '"' + ":" + '"' + "Enemies killed" + '"' + ",\n" + '"' + "value" + '"' + ":" + enemyKills + "},\n";
            result += '"' + String.valueOf(3) + '"' + ":{\n" + '"' + "name" + '"' + ":" + '"' + "Shots" + '"' + ",\n" + '"' + "value" + '"' + ":" + shots + "},\n";
            result += '"' + String.valueOf(4) + '"' + ":{\n" + '"' + "name" + '"' + ":" + '"' + "Deaths" + '"' + ",\n" + '"' + "value" + '"' + ":" + deaths + "}\n";
            result += "}";

            file.writeString(result, false);

    }

    public void addTimesDeath() {
        deaths++;
    }

    public void addTimePlayed(long time) {
        timePlayed += time;
    }

    public void addEnemiesKilled() {
        enemyKills++;
    }

    public void addShots() {
        shots++;
    }

    public int getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(int code) {
        buttonCode = code;
    }

    public boolean isHintSeen() {
        return hintSeen;
    }

    public Texture getHint() {
        return hint;
    }

    public void setSeenHint(boolean value) {
        hintSeen = value;
    }
}
