package com.mygdx.game.stages;

import com.badlogic.gdx.Game;
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

public class PauseStage implements EventListener {
    private static final int NORMAL = 1;
    private static final int HOVER = 2;
    private static final int CLICK = 3;

    private final SpriteBatch batch;
    private final HUDCamera hudCamera;
    private OrthographicCamera camera;
    private Controller controller;
    private SimpleBackground background;
    private List<Button> buttons;

    private BitmapFont title;

    private int position;

    public PauseStage(HUDCamera hudCamera) {
        this.hudCamera = hudCamera;
        batch = new SpriteBatch();

        title = new BitmapFont();
        title.setColor(Color.BLACK);
        title.getData().setScale(2, 2);

        camera = GameManager.getInstance().getMainMenuCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        background = new SimpleBackground((GameInfo.WIDTH / 2) - 150, (GameInfo.HEIGHT / 2) - 200, 300, 430, "Backgrounds/pause/pauseBackground.png");
        buttons = new ArrayList<Button>();
        buttons.add(new Button((int) background.getX() + 50, 350, 200, 70, "buttons/resumeButton.png", "buttons/resumeButtonHover.png", "buttons/resumeButtonClick.png"));
        buttons.add(new Button((int) background.getX() + 50, 270, 200, 70, "buttons/optionsButton.png", "buttons/optionsButtonHover.png", "buttons/optionsButtonClick.png"));
        buttons.add(new Button((int) background.getX() + 50, 190, 200, 70, "buttons/exitButton.png", "buttons/exitButtonHover.png", "buttons/exitButtonClick.png"));
        buttons.get(0).changeButtonStatus(2);
        for (Controller controller : Controllers.getControllers()) {
            if (controller.getName().equals("Controller (XBOX 360 For Windows)")) {
                this.controller = controller;
            }
        }
        position = 1;
        GameManager.getInstance().addEventListener(this);
    }

    public void draw() {


        batch.begin();


        background.draw(batch);
        title.draw(batch, "PAUSE", 450, 515);
        for (Button button : buttons) {
            button.draw(batch);
        }
        batch.end();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void dispose() {
        batch.dispose();
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case KEYDOWN_EVENT :
                if (!hudCamera.isOptionMenu() && hudCamera.isGamePaused()) {
                    InputData kdData = (InputData) eventData.eventData;
                    if ((kdData.keyCode == Input.Keys.DOWN ||  kdData.keyCode == -3)) {
                        moveDown();
                    } else if ((kdData.keyCode == Input.Keys.UP ||  kdData.keyCode == -4)) {
                        moveUp();
                    } else if ((kdData.keyCode == Input.Keys.ENTER || kdData.keyCode == 0)) {
                        manageAction();
                    }
                }
                break;
        }
    }

    public void resetPosition() {
        position = 1;
        buttons.get(0).changeButtonStatus(HOVER);
        buttons.get(1).changeButtonStatus(NORMAL);
        buttons.get(2).changeButtonStatus(NORMAL);
    }

    public void moveDown() {
        position++;
        if(position > 3) {
            position = 1;
            buttons.get(2).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }else {
            buttons.get(position - 2).changeButtonStatus(NORMAL);
            buttons.get(position - 1).changeButtonStatus(HOVER);
        }
    }

    public void moveUp() {
        position--;
        if(position < 1) {
            position = 3;
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
                hudCamera.resumeGame();
                resetPosition();
                break;
            case 2:
                hudCamera.openMenuOption();
                break;
            case 3:
                System.exit(0);
        }
    }
}
