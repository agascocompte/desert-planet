package com.mygdx.game.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameManager;
import com.mygdx.game.entities.Character;
import com.mygdx.game.entities.CoinViewer;
import com.mygdx.game.entities.HealthBar;
import com.mygdx.game.entities.PointsViewer;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.stages.OptionsStage;
import com.mygdx.game.stages.PauseStage;
import com.mygdx.game.stages.ScoreStage;

public class HUDCamera extends OrthographicCamera {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private HealthBar healthBar;
    private CoinViewer coinViewer;
    private PointsViewer pointsViewer;
    private PauseStage pauseStage;
    private OptionsStage optionsStage;
    private ScoreStage scoreStage;

    public boolean gamePaused = false;
    public boolean menuOption = false;

    public HUDCamera() {

        setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        healthBar = new HealthBar(50, Gdx.graphics.getHeight() - 51, 202, 22);
        coinViewer = new CoinViewer((Gdx.graphics.getWidth() / 2) - 50, Gdx.graphics.getHeight() - 63);
        pointsViewer = new PointsViewer(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 63);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        pauseStage = new PauseStage(this);
        optionsStage = new OptionsStage(this);
    }

    public void update(float delta) {
        healthBar.update(delta);
        coinViewer.update(delta);
        pointsViewer.update(delta);
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        healthBar.drawRenderer(shapeRenderer);
        shapeRenderer.end();

        batch.begin();
        healthBar.drawBatch(batch);
        coinViewer.draw(batch);
        pointsViewer.draw(batch);
        batch.end();

        //test
        if (isGamePaused()) {
            pauseStage.draw();
        }
        else {
            pauseStage.resetPosition();
        }

        if (isOptionMenu()) {
            optionsStage.draw();
            pauseStage.resetPosition();
        }

        if (scoreStage == null && GameManager.getInstance().getCharacter().isDeath()) {
            scoreStage = new ScoreStage(this);
        }

        if (scoreStage != null)
            scoreStage.draw();
    }

    public void pauseGame() {
        if (GameManager.getInstance().isHintSeen())
            gamePaused = true;
    }

    public void resumeGame() {
        if (!isOptionMenu()) {
            gamePaused = false;
        }
    }

    public void openMenuOption() {
        menuOption = true;
    }

    public void closeMenuOption() {
        menuOption = false;
    }

    public boolean isOptionMenu() {
        return menuOption;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public OptionsStage getOptionsStage() {
        return optionsStage;
    }
}
