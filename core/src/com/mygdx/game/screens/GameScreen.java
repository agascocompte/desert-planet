package com.mygdx.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FrameRate;
import com.mygdx.game.GameManager;
import com.mygdx.game.camera.HUDCamera;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.map.WorldGenerator;

public class GameScreen implements Screen {

    private FrameRate frameRate;
    private HUDCamera hudCamera;

    private final SpriteBatch batch;
    private final GameManager gameManager;
    private long startTime;



    public GameScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        batch = new SpriteBatch();
        frameRate = new FrameRate();
        hudCamera = new HUDCamera();
        System.out.println(startTime / 1000);
        gameManager.createBackgrounds("Backgrounds/Desert", 8);
        gameManager.beginWorldGeneration();
        gameManager.addNewGameEntity(GameManager.ENTITY_TYPE.CHARACTER, 0, 200,150,150, "badlogic.jpg"); // Player
        startTime = System.currentTimeMillis() / 1000;
        GameManager.getInstance().playMusic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (GameManager.getInstance().isHintSeen()) {
            gameManager.update(delta, batch);
            frameRate.update();//Borrar
            frameRate.render();//Borrar
            hudCamera.update(delta);
            hudCamera.draw();
        }
        else {
            batch.draw(GameManager.getInstance().getHint(), 0 ,0, GameInfo.WIDTH, GameInfo.HEIGHT);
        }

        if (!GameManager.getInstance().isHintSeen() && (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || GameManager.getInstance().getButtonCode() == 7)) {
            GameManager.getInstance().setSeenHint(true);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        hudCamera.pauseGame();
    }

    @Override
    public void resume() {
        hudCamera.resumeGame();

    }

    @Override
    public void hide() {
        gameManager.removeEntities();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public HUDCamera getHUDCAMERA() {
        return hudCamera;
    }

    public long getStartTime() {
        return startTime;
    }
}
