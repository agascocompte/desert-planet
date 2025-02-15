package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameManager;
import com.mygdx.game.helpers.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Desert Planet";
		config.width = GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		new LwjglApplication(GameManager.getInstance(), config);
	}
}
