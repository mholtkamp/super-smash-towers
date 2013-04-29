package com.me.td;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "super-smash-towers";
		cfg.useGL20 = true;
		cfg.width = 740;
		cfg.height = 400;
		
		new LwjglApplication(new TDGame(), cfg);
	}
}
