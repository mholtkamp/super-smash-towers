package com.me.td;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Map {

	public void render(SpriteBatch batch);
	public Point2D getWayPoint(int x);
	public int getWayPointCount();

}
