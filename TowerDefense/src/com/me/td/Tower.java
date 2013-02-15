package com.me.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Tower {

	public float getX();
	public float getY();
	public float getRange();
	public void update();
	public void render(SpriteBatch batch);
}
