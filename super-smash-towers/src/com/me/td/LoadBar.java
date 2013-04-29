package com.me.td;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadBar {
	private float progress;
	private float width,height;
	private float x,y;
	private AssetManager manager;
	private Texture loadTex;
	public LoadBar(AssetManager manager)
	{
		width = 600;
		height = 60;
		x = 120;
		y = 200;
		this.manager = manager;
		loadTex = manager.get("data/white.png");
	}
	
	public void update()
	{
		progress = manager.getProgress();
	}
	public void render(SpriteBatch batch)
	{
		batch.draw(loadTex,x,y,(width - x)*progress,height);
	}

}
