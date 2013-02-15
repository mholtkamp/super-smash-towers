package com.me.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Enemy {
	public float getX();
	public float getY();
	public int getHealth();
	public void subHealth(int negHealth);
	public void render(SpriteBatch batch);
	public void update();
	public boolean isDead();
	public Rectangle getCollider();
}
