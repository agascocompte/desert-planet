package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.entities.SimpleBackground;
import com.mygdx.game.helpers.GameInfo;
import com.mygdx.game.helpers.Register;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsStage {
    private final SpriteBatch batch;    ;
    private SimpleBackground background;

    private BitmapFont title;
    private BitmapFont timePlayedName, enemiesKilledName, shotsName, deathsName;
    private BitmapFont timePlayedValue, enemiesKilledValue, shotsValue, deathsValue;

    List<Register> registerList;

    public StatisticsStage() {
        batch = new SpriteBatch();

        title = new BitmapFont();
        title.getData().setScale(2,2);
        title.setColor(Color.BLACK);

        timePlayedName = new BitmapFont();
        timePlayedName.getData().setScale(2, 2);
        enemiesKilledName = new BitmapFont();
        enemiesKilledName.getData().setScale(2, 2);
        shotsName = new BitmapFont();
        shotsName.getData().setScale(2, 2);
        deathsName = new BitmapFont();
        deathsName.getData().setScale(2, 2);

        timePlayedValue = new BitmapFont();
        timePlayedValue.getData().setScale(2, 2);
        enemiesKilledValue = new BitmapFont();
        enemiesKilledValue.getData().setScale(2, 2);
        shotsValue = new BitmapFont();
        shotsValue.getData().setScale(2, 2);
        deathsValue = new BitmapFont();
        deathsValue.getData().setScale(2, 2);

        background = new SimpleBackground((GameInfo.WIDTH / 2) - 300, (GameInfo.HEIGHT / 2) - 200, 600, 430, "Backgrounds/pause/pauseBackground.png");

        FileHandle file = Gdx.files.local("save/statistics.json");
        registerList = getStatistics(file);
    }

    public void draw() {

        batch.begin();
        background.draw(batch);

        FileHandle file = Gdx.files.local("save/statistics.json");
        List<Register> registersToDraw = getStatistics(file);

        title.draw(batch, "STATISTICS", 420, 515);

        timePlayedName.draw(batch, registersToDraw.get(0).getName(), 315, 410);
        int time = registersToDraw.get(0).getPoints();
        if (time < 60)
            timePlayedValue.draw(batch, String.valueOf(registersToDraw.get(0).getPoints()) + "s", 620, 410);
        else if (time < 3600)
            timePlayedValue.draw(batch, String.valueOf(registersToDraw.get(0).getPoints() / 60) + "m", 620, 410);
        else
            timePlayedValue.draw(batch, String.valueOf(registersToDraw.get(0).getPoints() / 3600) + "h", 620, 410);

        enemiesKilledName.draw(batch, registersToDraw.get(1).getName(), 315, 360);
        enemiesKilledValue.draw(batch, String.valueOf(registersToDraw.get(1).getPoints()), 620, 360);

        shotsName.draw(batch, registersToDraw.get(2).getName(), 315, 310);
        shotsValue.draw(batch, String.valueOf(registersToDraw.get(2).getPoints()), 620, 310);

        deathsName.draw(batch, registersToDraw.get(3).getName(), 315, 260);
        deathsValue.draw(batch, String.valueOf(registersToDraw.get(3).getPoints()), 620, 260);

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    public List<Register> getStatistics(FileHandle file) {
        List<Register> list = new ArrayList<Register>();

        JsonReader jsonReader = new JsonReader();
        JsonValue jv = jsonReader.parse(file.reader());

        int registers = jv.size;

        for (int i = 0; i < registers; i++) {
            String name = jv.get(i).getString("name");
            int value = jv.get(i).getInt("value");
            list.add(new Register(name, value));
        }

        return list;
    }
}
