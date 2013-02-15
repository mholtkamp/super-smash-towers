package com.me.td;

import java.awt.geom.Point2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BasicMap implements Map {
	private Texture bgtex;
	private int wayPointCount;
	
	public BasicMap()
	{
		bgtex = new Texture("data/BasicMap.png");
		wayPointCount = 4;
	}
	
	public Point2D getWayPoint(int x)
	{
		Point2D retPoint = new Point2D.Float();
		if(x == 1)
		{
			retPoint.setLocation(0, 50);
		}
		else if(x == 2)
		{
			retPoint.setLocation(393,50);
		}
		else if(x == 3)
		{
			retPoint.setLocation(393,300);
		}
		else if(x == 4)
		{
			retPoint.setLocation(800,300);
		}
		
		return retPoint;
	}
	
	public int getWayPointCount()
	{
		return wayPointCount;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bgtex, 0, 0, 800, 480);
	}

}
