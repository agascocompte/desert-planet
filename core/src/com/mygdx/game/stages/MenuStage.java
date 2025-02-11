package com.mygdx.game.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameManager;
import com.mygdx.game.camera.HUDCamera;
import com.mygdx.game.entities.Button;
import com.mygdx.game.entities.SimpleBackground;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.events.InputData;
import com.mygdx.game.helpers.GameInfo;
import java.util.ArrayList;
import java.util.List;

public class MenuStage {
    private static final int NORMAL = 1;
    private static final int HOVER = 2;
    private static final int CLICK = 3;

    private final SpriteBatch batch;
    private Controller controller;
    private List<Button> buttons;

    private int position;

    private boolean beginGame = false;
    private boolean openOptions = false;
    private boolean openStatistics = false;

    public MenuStage() {
        batch = new SpriteBatch();

        buttons = new ArrayList<Button>();
        buttons.add(new Button(Gdx.graphics.getWidth() / 2 - 100, (Gdx.graphics.getHeight() / 2) - 10, 200, 70, "buttons/playButton.png", "buttons/playButtonHover.png", "buttons/playButtonHover.png"));
        buttons.add(new Button(Gdx.graphics.getWidth() / 2 - 100, (Gdx.graphics.getHeight() / 2) - 80, 200, 70, "buttons/statisticsButton.png", "buttons/statisticsButtonHover.png", "buttons/statisticsButtonHover.png"));
        buttons.add(new Button(Gdx.graphics.getWidth() / 2 - 100, (Gdx.graphics.getHeight() / 2) - 150, 200, 70, "buttons/optionsButton.png", "buttons/optionsButtonHover.png", "buttons/optionsButtonClick.png"));
        buttons.add(new Button(Gdx.graphics.getWidth() / 2 - 100, (Gdx.graphics.getHeight() / 2) - 220, 200, 70, "buttons/exitButton.png", "buttons/exitButtonHover.png", "buttons/exitButtonClick.png"));
        buttons.get(0).changeButtonStatus(2);
        for (Controller controller : Controllers.getControllers()) {
            if (controller.getName().equals("Controller (XBOX 360 For Windows)")) {
                this.controller = controller;
            }
        }
        position = 1;


    }

    public void draw() {


        batch.begin();




        for (Button button : buttons) {
            button.draw(batch);
        }
        batch.end();



    }

    public void dispose() {
        batch.dispose();
    }

    public void resetPosition() {
        position = 1;
        buttons.get(0).changeButtonStatus(HOVER);
        buttons.get(1).changeButtonStatus(NORMAL);
        buttons.get(2).changeButtonStatus(NORMAL);
    }

    public void moveDown() {
        position++;
        if(position > 4) {
            position = 1;
            buttons.get(3).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }else {
            buttons.get(position - 2).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }
    }

    public void moveUp() {
        position--;
        if(position < 1) {
            position = 4;
            buttons.get(0).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }
        else {
            buttons.get(position).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }
    }

    public void manageAction() {
        switch (position) {
            case 1:
                beginGame = true;
                resetPosition();
                break;
            case 2:
                openStatistics = true;
                break;
            case 3:
                openOptions = true;
                break;
            case 4:
                System.exit(0);
        }
    }

    public boolean hasToBeginGame() {
        return beginGame;
    }

    public boolean areOpenOptions() {
        return openOptions;
    }

    public void setOpenOptions(boolean openOptions) {
        this.openOptions = openOptions;
    }

    public boolean areOpenStatistics() {
        return openStatistics;
    }

    public void setOpenStatistics(boolean openStatistics) {
        this.openStatistics = openStatistics;
    }

    public int getPosition() {
        return position;
    }
}

