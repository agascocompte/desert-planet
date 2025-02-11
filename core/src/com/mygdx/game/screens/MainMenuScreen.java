package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameManager;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.Character;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.stages.MenuStage;
import com.mygdx.game.stages.OptionsMenuStage;
import com.mygdx.game.stages.StatisticsStage;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {
    private final SpriteBatch batch;
    private final GameManager gameManager;
    private OrthographicCamera camera;
    private Controller controller;
    Screen game;
    List<BackgroundParallax> backgrounds;
    Sprite title = new Sprite(new Texture("Backgrounds/titleGame.png"));
    List<GameEntity> entities;
    MenuStage menuStage;
    OptionsMenuStage optionMenuStage;
    StatisticsStage statisticsStage;
    private boolean blockButton = false;
    private float counter = 0;

    public MainMenuScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        batch = new SpriteBatch();

        menuStage = new MenuStage();
        optionMenuStage = new OptionsMenuStage();
        statisticsStage = new StatisticsStage();

        camera = gameManager.getMainMenuCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        for (Controller controller : Controllers.getControllers()) {
            if (controller.getName().equals("Controller (XBOX 360 For Windows)")) {
                this.controller = controller;
            }
        }

        backgrounds = new ArrayList<BackgroundParallax>();
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/8.png", 8, 0));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/7.png", 7, 0.05f));
        backgrounds.add(new BackgroundParallax(-GameInfo.WIDTH, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/7.png", 7, 0.05f));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/6.png", 6, 0.2f));
        backgrounds.add(new BackgroundParallax(- GameInfo.WIDTH, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/6.png", 6, 0.2f));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/5.png", 5, 0));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/4.png", 4, 0));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/3.png", 3, 0));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/2.png", 2, 0));
        backgrounds.add(new BackgroundParallax(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT, "Backgrounds/Desert/1.png", 1, 0));
        title.setPosition(200, 350);
        title.setSize(600, 200);
        entities = new ArrayList<GameEntity>();
        entities.add(new Ground(-400, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(-200, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(0, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(200, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(400, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(600, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(800, 0, 200, 50, "Platforms/floor.png"));
        entities.add(new Ground(1000, 0, 200, 50, "Platforms/floor.png"));
        if (GameManager.getInstance().getCharacter() == null)
            entities.add(new Character(200, 200, 50, 50, "badlogic.jpg", true));
        else {
            entities.add(GameManager.getInstance().getCharacter());
            ((Character) entities.get(entities.size() - 1)).setMovement(null);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for (BackgroundParallax backgroundParallax : backgrounds) {
            backgroundParallax.staticUpdate(delta);
            backgroundParallax.draw(batch);
        }

        for (GameEntity entity : entities) {

            if (entity instanceof  Character) {
                entity.setX(entity.getX() + 5);
                entity.setY(40);
                entity.update(delta);
                if (entity.getX() > GameInfo.WIDTH + 200) {
                    entity.setX(-200);
                }
            }
            entity.draw(batch);
        }
        title.draw(batch);


        batch.end();

        // Resets
        if (blockButton == true) {
            if (counter > 1) {
                blockButton = false;
                counter = 0;
            }
            else {
                counter += 0.1;
            }
        }


        // DRAW
        menuStage.draw();

        if (menuStage.areOpenOptions()) {
            optionMenuStage.draw();
        }
        if (menuStage.areOpenStatistics()) {
            statisticsStage.draw();
        }

        // INPUTS MENU
        if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || GameManager.getInstance().getButtonCode() == 0) && !blockButton && menuStage.getPosition() == 1 && !menuStage.areOpenOptions() && !menuStage.areOpenStatistics()) {
            GameManager.getInstance().stopMainMenuMusic();
            game = new GameScreen((GameManager.getInstance()));
            GameManager.getInstance().setScreen(game);
            GameManager.getInstance().setActiveScreen(game);
            blockButton = true;
            this.dispose();
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || GameManager.getInstance().getButtonCode() == -4) && !blockButton && !menuStage.areOpenOptions() && !menuStage.areOpenStatistics()){
            menuStage.moveUp();
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || GameManager.getInstance().getButtonCode() == -3) && !blockButton&& !menuStage.areOpenOptions() && !menuStage.areOpenStatistics()){
            menuStage.moveDown();
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || GameManager.getInstance().getButtonCode() == 0) && !blockButton && !menuStage.areOpenOptions() && !menuStage.areOpenStatistics()) {
            menuStage.manageAction();
            blockButton = true;

        }
        // INPUT STATISTICS
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || GameManager.getInstance().getButtonCode() == 0) && !blockButton && menuStage.areOpenStatistics()) {
            menuStage.setOpenStatistics(false);
            blockButton = true;
        }


        //INPUTS OPTIONS
        if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || GameManager.getInstance().getButtonCode() == 0) && !blockButton && menuStage.areOpenOptions()) {
            optionMenuStage.manageAction();
            if (optionMenuStage.isCloseOptions()) {
                menuStage.setOpenOptions(false);
                optionMenuStage.setCloseOptions(false);
            }
            if (optionMenuStage.getPosition() == 2) {
                counter = -1;
                GameManager.getInstance().setButtonCode(1000);
            }
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || GameManager.getInstance().getButtonCode() == -4) && !blockButton && menuStage.areOpenOptions()) {
            optionMenuStage.moveUp();
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || GameManager.getInstance().getButtonCode() == -3) && !blockButton && menuStage.areOpenOptions()) {
            optionMenuStage.moveDown();
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || GameManager.getInstance().getButtonCode() == -1) && !blockButton && menuStage.areOpenOptions() && optionMenuStage.getPosition() == 1 && GameManager.getInstance().getBgMusic().getVolume() <= 1) {
            GameManager.getInstance().getBgMusic().setVolume(GameManager.getInstance().getBgMusic().getVolume() + 0.1f);
            GameManager.getInstance().getMainMenuMusic().setVolume(GameManager.getInstance().getMainMenuMusic().getVolume() + 0.1f);
            optionMenuStage.getElements().get(1).setSize((int)(GameManager.getInstance().getBgMusic().getVolume() * 183), optionMenuStage.getElements().get(1).getHeight());
            blockButton = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || GameManager.getInstance().getButtonCode() == -2) && !blockButton && menuStage.areOpenOptions() && optionMenuStage.getPosition() == 1 && GameManager.getInstance().getBgMusic().getVolume() > 0.1f) {
            GameManager.getInstance().getBgMusic().setVolume(GameManager.getInstance().getBgMusic().getVolume() - 0.1f);
            GameManager.getInstance().getMainMenuMusic().setVolume(GameManager.getInstance().getMainMenuMusic().getVolume() - 0.1f);
            optionMenuStage.getElements().get(1).setSize((int)(GameManager.getInstance().getBgMusic().getVolume() * 183),  optionMenuStage.getElements().get(1).getHeight());
            blockButton = true;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
