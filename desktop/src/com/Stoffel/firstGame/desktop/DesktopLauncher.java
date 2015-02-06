package com.Stoffel.firstGame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Stoffel.firstGame.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.x = -1;
		config.y = -1;
		config.height = 480;
		config.width = 800;
		config.title = "Drop Game";
		
		new LwjglApplication(new MyGame(), config);
	
	}
}
