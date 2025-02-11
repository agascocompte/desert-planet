package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
import com.mygdx.game.entities.OptionElement;
import com.mygdx.game.entities.SimpleBackground;
import com.mygdx.game.events.EventData;
import com.mygdx.game.events.EventListener;
import com.mygdx.game.events.InputData;
import com.mygdx.game.helpers.GameInfo;

import java.util.ArrayList;
import java.util.List;

public class OptionsStage implements EventListener {
    private static final int NORMAL = 1;
    private static final int HOVER = 2;
    private static final int CLICK = 3;

    private final SpriteBatch batch;
    private final HUDCamera hudCamera;
    private OrthographicCamera camera;
    private Controller controller;
    private SimpleBackground background;
    private List<Button> buttons;

    private List<OptionElement> elements;

    private int position;

    private BitmapFont volumeNumber;
    private BitmapFont fullscreenTxt;
    private BitmapFont title;

    public OptionsStage(HUDCamera hudCamera) {
        this.hudCamera = hudCamera;
        batch = new SpriteBatch();

        camera = GameManager.getInstance().getMainMenuCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        background = new SimpleBackground((GameInfo.WIDTH / 2) - 300, (GameInfo.HEIGHT / 2) - 200, 600, 430, "Backgrounds/pause/pauseBackground.png");
        buttons = new ArrayList<Button>();
        buttons.add(new Button((int) background.getX() + 100, 350, 70, 70, "buttons/Volume.png", "buttons/Volume_hover.png", "buttons/Volume_click.png"));
        buttons.add(new Button((int) background.getX() + 100, 270, 70, 70, "buttons/Fullscreen.png", "buttons/Fullscreen_hover.png", "buttons/Fullscreen_click.png"));
        buttons.add(new Button(((int) background.getX() + (int) background.getWidth()) / 2, 180, 200, 70, "buttons/backButton.png", "buttons/backButtonHover.png", "buttons/backButtonClick.png"));
        buttons.get(0).changeButtonStatus(2);

        elements = new ArrayList<OptionElement>();
        elements.add(new OptionElement(((int) background.getX() + (int) background.getWidth()) / 2, 360, 270, 50, "buttons/progressBar.png"));
        elements.add(new OptionElement(((int) background.getX() + (int) background.getWidth()) / 2 + 65, 371, (int)(GameManager.getInstance().getBgMusic().getVolume() * 183), 30, "buttons/bar.png"));

        volumeNumber = new BitmapFont();
        fullscreenTxt = new BitmapFont();
        title = new BitmapFont();
        title.getData().setScale(2, 2);
        title.setColor(Color.BLACK);

        for (Controller controller : Controllers.getControllers()) {
            if (controller.getName().equals("Controller (XBOX 360 For Windows)")) {
                this.controller = controller;
            }
        }
        position = 1;
        GameManager.getInstance().addEventListener(this);
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        background.draw(batch);
        title.draw(batch, "OPTIONS", 440, 515);
        for (Button button : buttons) {
            button.draw(batch);
        }

        for (OptionElement element : elements) {
            element.draw(batch);
        }

        volumeNumber.getData().setScale(2, 2);
        volumeNumber.draw(batch,String.valueOf( (int) (GameManager.getInstance().getBgMusic().getVolume() * 100)), (int)elements.get(0).getX() + 15, (int)elements.get(0).getY() + 38);


        String fullScreenText = "FULLSCREEN OFF";
        if (Gdx.graphics.isFullscreen()) {
            fullScreenText = "FULLSCREEN ON";
        }
        volumeNumber.getData().setScale(2, 2);
        volumeNumber.draw(batch,fullScreenText, (int)buttons.get(1).getWidth() + 330, (int)buttons.get(1).getY() + 50);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    @Override
    public void onEvent(EventData eventData) {
        switch (eventData.eventType) {
            case KEYDOWN_EVENT :
                if (hudCamera.isOptionMenu()) {
                    InputData kdData = (InputData) eventData.eventData;
                    if ((kdData.keyCode == Input.Keys.DOWN || kdData.keyCode == -3)) {
                        moveDown();
                    } else if ((kdData.keyCode == Input.Keys.UP || kdData.keyCode == -4)) {
                        moveUp();
                    } else if ((kdData.keyCode == Input.Keys.ENTER || kdData.keyCode == 0)) {
                        manageAction();
                    }
                    else if (((kdData.keyCode == Input.Keys.RIGHT || kdData.keyCode == -1) && position == 1) && GameManager.getInstance().getBgMusic().getVolume() <= 1) {
                        GameManager.getInstance().getBgMusic().setVolume(GameManager.getInstance().getBgMusic().getVolume() + 0.1f);
                        GameManager.getInstance().getMainMenuMusic().setVolume(GameManager.getInstance().getMainMenuMusic().getVolume() + 0.1f);
                        elements.get(1).setSize((int)(GameManager.getInstance().getBgMusic().getVolume() * 183), elements.get(1).getHeight());
                    }
                    else if (((kdData.keyCode == Input.Keys.LEFT || kdData.keyCode == -2) && position == 1) && GameManager.getInstance().getBgMusic().getVolume() > 0.1f) {
                        GameManager.getInstance().getBgMusic().setVolume(GameManager.getInstance().getBgMusic().getVolume() - 0.1f);
                        GameManager.getInstance().getMainMenuMusic().setVolume(GameManager.getInstance().getMainMenuMusic().getVolume() - 0.1f);
                        elements.get(1).setSize((int)(GameManager.getInstance().getBgMusic().getVolume() * 183), elements.get(1).getHeight());
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
            case 2:
                boolean fullScreen = Gdx.graphics.isFullscreen();
                Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
                if (fullScreen == true)
                    Gdx.graphics.setWindowedMode(GameInfo.WIDTH, GameInfo.HEIGHT);
                else
                    Gdx.graphics.setFullscreenMode(currentMode);
                break;
            case 3:
                hudCamera.closeMenuOption();
                resetPosition();
                break;
        }
    }
}
