package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.game.GameManager;
import com.mygdx.game.camera.HUDCamera;
import com.mygdx.game.entities.Character;
import com.mygdx.game.entities.SimpleBackground;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.helpers.Register;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreStage {

    private final SpriteBatch batch;
    private final HUDCamera hudCamera;
    private OrthographicCamera camera;
    private SimpleBackground background;
    private int newPoints = -1;
    private String newName = "";
    public boolean actionDone = false;

    private BitmapFont title;
    private BitmapFont top1Name, top2Name, top3Name, top4Name, top5Name;
    private BitmapFont top1Points, top2Points, top3Points, top4Points, top5Points;


    List<Register> registerList;
    public boolean founded = false;
    int counter = 0;
    int index = 0;

    public ScoreStage(HUDCamera hudCamera) {
        this.hudCamera = hudCamera;
        batch = new SpriteBatch();

        registerList = new ArrayList<Register>();
        registerList.add(new Register("Empty", 0));
        registerList.add(new Register("Empty", 0));
        registerList.add(new Register("Empty", 0));
        registerList.add(new Register("Empty", 0));
        registerList.add(new Register("Empty", 0));
        camera = GameManager.getInstance().getMainMenuCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        title = new BitmapFont();
        title.getData().setScale(2,2);
        title.setColor(Color.BLACK);

        top1Name = new BitmapFont();
        top1Name.getData().setScale(2, 2);
        top2Name = new BitmapFont();
        top2Name.getData().setScale(2, 2);
        top3Name = new BitmapFont();
        top3Name.getData().setScale(2, 2);
        top4Name = new BitmapFont();
        top4Name.getData().setScale(2, 2);
        top5Name = new BitmapFont();
        top5Name.getData().setScale(2, 2);

        top1Points = new BitmapFont();
        top1Points.getData().setScale(2, 2);
        top2Points = new BitmapFont();
        top2Points.getData().setScale(2, 2);
        top3Points = new BitmapFont();
        top3Points.getData().setScale(2, 2);
        top4Points = new BitmapFont();
        top4Points.getData().setScale(2, 2);
        top5Points = new BitmapFont();
        top5Points.getData().setScale(2, 2);

        background = new SimpleBackground((GameInfo.WIDTH / 2) - 300, (GameInfo.HEIGHT / 2) - 200, 600, 430, "Backgrounds/pause/pauseBackground.png");

        FileHandle file = Gdx.files.local("save/scoreboard.json");
        registerList = getRegisters(file);

        int points = GameManager.getInstance().getCharacter().getPoints();
        int coins = GameManager.getInstance().getCharacter().getCoins();

        if (points == 0) points = 1;
        else if (coins == 0) coins = 1;
        newPoints =  points * coins;

        for (Register register : registerList) {
            if (register.getPoints() < newPoints) {
                index = counter;
                MyTextInputListener listener = new MyTextInputListener();
                Gdx.input.getTextInput(listener, "New Score!", "", "Write your nick...");
                break;
            }
            counter++;
        }
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        background.draw(batch);

        FileHandle file = Gdx.files.local("save/scoreboard.json");
        List<Register> registersToDraw = getRegisters(file);

        title.draw(batch, "SCOREBOARD", 395, 515);

        top1Name.draw(batch, registersToDraw.get(0).getName(), 315, 430);
        top1Points.draw(batch, String.valueOf(registersToDraw.get(0).getPoints()), 590, 430);

        top2Name.draw(batch, registersToDraw.get(1).getName(), 315, 380);
        top2Points.draw(batch, String.valueOf(registersToDraw.get(1).getPoints()), 590, 380);

        top3Name.draw(batch, registersToDraw.get(2).getName(), 315, 330);
        top3Points.draw(batch, String.valueOf(registersToDraw.get(2).getPoints()), 590, 330);

        top4Name.draw(batch, registersToDraw.get(3).getName(), 315, 280);
        top4Points.draw(batch, String.valueOf(registersToDraw.get(3).getPoints()), 590, 280);

        top5Name.draw(batch, registersToDraw.get(4).getName(), 315, 230);
        top5Points.draw(batch, String.valueOf(registersToDraw.get(4).getPoints()), 590, 230);

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    public class MyTextInputListener implements Input.TextInputListener {
        @Override
        public void input (String text) {
            newName = text;
            updateScores();
        }

        @Override
        public void canceled () {
            newName = "NoName";
            updateScores();
        }
    }

    public void updateScores() {
        registerList.remove(4);
        registerList.add(new Register(newName, newPoints));

        Collections.sort(registerList, new Comparator<Register>() {
            @Override
            public int compare(Register register1, Register register2)
            {
                if (register1.getPoints() > register2.getPoints()) {
                    return -1;
                }
                else if (register1.getPoints() < register2.getPoints()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        writeJson(registerList);
    }

    public List<Register> getRegisters(FileHandle file) {
        JsonReader jsonReader = new JsonReader();
        JsonValue jv = jsonReader.parse(file.reader());

        int registers = jv.size;

        for (int i = 0; i < registers; i++) {
            String name = jv.get(i).getString("player");
            int points = jv.get(i).getInt("points");
            registerList.get(i).setName(name);
            registerList.get(i).setPoints(points);
        }

        return registerList;
    }

    public void writeJson(List<Register> registers) {

        FileHandle file = Gdx.files.local("save/scoreboard.json");

        String result = "{";

        for (int i = 0; i < registers.size(); i++) {
            if (i != registers.size() - 1)
                result += '"' + String.valueOf(i + 1) + '"' + ":{\n" + '"' + "player" + '"' + ":" + '"' + registers.get(i).getName() + '"' + ",\n" + '"' + "points" + '"' + ":" + registers.get(i).getPoints() + "},\n";
            else
                result += '"' + String.valueOf(i + 1) + '"' + ":{\n" + '"' + "player" + '"' + ":" + '"' + registers.get(i).getName() + '"' + ",\n" + '"' + "points" + '"' + ":" + registers.get(i).getPoints() + "}\n";
        }
        result += "}";
        file.writeString(result, false);

    }
}
