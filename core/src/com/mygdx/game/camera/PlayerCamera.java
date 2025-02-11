package com.mygdx.game.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.Character;
import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.helpers.GameInfo;

public class PlayerCamera extends OrthographicCamera {

    Character character;
    private float lastPosX;
    private float actualPosX;

    public PlayerCamera (Character character) {
        this.character = character;
        position.x = character.getX();
        position.y = character.getY();
        lastPosX = position.x;
        actualPosX = position.x;
    }

    //@Override
    public void update() {

        float lerp = 0.1f;
        lastPosX = actualPosX;
        Vector3 position = this.position;

        //Update X
        if (character.getX() > 570) {
            position.x += ((character.getX() + character.getWidth() / 2) - position.x) * 0.7;
            actualPosX = ((character.getX() + character.getWidth() / 2));
        }
        else
            if (position.x > 650) {
                position.x += ((character.getX() + character.getWidth() / 2) - position.x) * 0.7;
                actualPosX = ((character.getX() + character.getWidth() / 2));
            }
            else
                position.x = 650;

        // Update Y
        if (character.getY() > 345)
            position.y += ((character.getY() + 175) - position.y) * lerp;
        else
            if (position.y > 500) {
                position.y += ((character.getY() + 175) - position.y) * lerp;
            }
            else {
                position.y = 500;
            }


        super.update();
    }

    public float [] getBounds() {
        float [] bounds = new float [4];  // Izd, Drcha, Abajo, Arriba

        bounds[0] = position.x - (viewportWidth / 2);
        bounds[1] = position.x + (viewportWidth/ 2);
        bounds[2] = position.y - (viewportHeight / 2);
        bounds[3] = position.y + (viewportHeight / 2);

        return bounds;
    }

    public int getDirection () {
        if (lastPosX < actualPosX) return -1;
        else if (lastPosX > actualPosX) return 1;
        else return 0;
    }
}
