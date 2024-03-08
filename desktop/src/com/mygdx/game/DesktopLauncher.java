package com.mygdx.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setForegroundFPS(60);
		config.setIdleFPS(60);
		config.setTitle(Application.TITLE);
		config.setWindowedMode(Application.V_WIDTH, Application.V_HEIGHT);
		System.out.println("");
		System.out.println("Lifecycle Started");
		new Lwjgl3Application(new Application(), config);

	}
}
